package com.bizeff.procurement.usecases.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation.ReconcileInvoiceInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.invoicepaymentreconciliation.AccountantContactDetails;
import com.bizeff.procurement.domaininterfaces.inputds.invoicepaymentreconciliation.ReconcileInvoiceInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.ReconcileInvoiceOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ReconclieInvoiceOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.DeliveryReceiptRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.InvoiceRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.PaymentReconciliationRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.models.enums.ReconciliationStatus;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;
import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;
import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.services.invoicepaymentreconciliation.InvoiceReconciliationServices;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateNotNull;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateString;

public class ReconcileInvoiceUseCase implements ReconcileInvoiceInputBoundary {
    private PaymentReconciliationRepository paymentReconciliationRepository;
    private DeliveryReceiptRepository deliveryReceiptRepository;
    private InvoiceRepository invoiceRepository;
    private PurchaseOrderRepository purchaseOrderRepository;
    private ReconcileInvoiceOutputBoundary reconcileInvoiceOutputBoundary;
    private InvoiceReconciliationServices invoiceReconciliationService;

    public ReconcileInvoiceUseCase(
            InvoiceReconciliationServices invoiceReconciliationService,
            PaymentReconciliationRepository paymentReconciliationRepository,
            DeliveryReceiptRepository deliveryReceiptRepository,
            InvoiceRepository invoiceRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            ReconcileInvoiceOutputBoundary reconcileInvoiceOutputBoundary
    ) {
        this.paymentReconciliationRepository = paymentReconciliationRepository;
        this.deliveryReceiptRepository = deliveryReceiptRepository;
        this.invoiceRepository = invoiceRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.reconcileInvoiceOutputBoundary = reconcileInvoiceOutputBoundary;
        this.invoiceReconciliationService = invoiceReconciliationService;
    }

    @Override
    public ReconclieInvoiceOutputDS reconcileInvoice(ReconcileInvoiceInputDS input) {
        validateInput(input);

        Invoice invoice = invoiceRepository.findByInvoiceId(input.getInvoiceId())
                .orElseThrow(()-> new IllegalArgumentException("there is no invoice with the invoice id "+ input.getInvoiceId()));

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByOrderId(input.getOrderId())
                .orElseThrow(()->new IllegalArgumentException("there is no purchase order with the id "+ invoice.getPurchaseOrder()));


        DeliveryReceipt deliveryReceipts = deliveryReceiptRepository.findByReceiptId(input.getReceiptId()).orElseThrow(()-> new IllegalArgumentException("there is no delivered receipts with id: "+ input.getReceiptId()));

        if (input.getReconciliationDate().isBefore(deliveryReceipts.getDeliveryDate())){
            throw new IllegalArgumentException("we can't reconcile before received.");
        }
        invoiceReconciliationService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        invoiceReconciliationService.getInvoiceMap().put(invoice.getInvoiceId(),invoice);
        invoiceReconciliationService.getDeliveryReceiptMap().put(deliveryReceipts.getReceiptId(),deliveryReceipts);
        PaymentReconciliation paymentReconciliation = invoiceReconciliationService.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(), deliveryReceipts.getReceiptId(),input.getReconciliationDate());

        PaymentReconciliation savedPaymentReconciliation = paymentReconciliationRepository.save(paymentReconciliation);

        String message;

        if (savedPaymentReconciliation.getReconciliationStatus().equals(ReconciliationStatus.MATCHED)) {
            message = "Successfully Reconciled with out Discrepancy.";

        }else {
            message = "Reconciled With Discrepancy"+ savedPaymentReconciliation.getDiscrepancyAmount();
        }
        ReconclieInvoiceOutputDS output = new ReconclieInvoiceOutputDS(savedPaymentReconciliation.getPaymentId(),savedPaymentReconciliation.getReconciliationStatus(),savedPaymentReconciliation.getReconciliationDate(),message);

        reconcileInvoiceOutputBoundary.presentReconciledInvoice(output);

        return output;
    }
    public void validateInput(ReconcileInvoiceInputDS inputDS){
        validateNotNull(inputDS,"Input DS");
        validateOfficerContactDetail(inputDS.getOfficerDetails());
        validateString(inputDS.getInvoiceId(),"Invoice Id");
        validateString(inputDS.getOrderId(),"Order id");
        validateString(inputDS.getReceiptId(), "Receipt Id");
        validateNotNull(inputDS.getReconciliationDate(),"Reconciliation Date");
    }
    public void validateOfficerContactDetail(AccountantContactDetails accountantContactDetails){
        validateNotNull(accountantContactDetails, "accountantContactDetails");
        validateString(accountantContactDetails.getName(),"Accountant name");
        validateString(accountantContactDetails.getEmailAddress(), "User email");
        validateString(accountantContactDetails.getPhoneNumber(), "User phone number");
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!accountantContactDetails.getEmailAddress().matches(emailRegex)){
            throw new IllegalArgumentException("please Enter valid email address.based on the emailRegex = \"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\";\n");
        }
        String phoneRegex = "^\\+\\d{1,3}\\d{4,14}$";
        if (!accountantContactDetails.getPhoneNumber().matches(phoneRegex)){
            throw new IllegalArgumentException("Please Enter valid phone number with country code.");
        }
        validateString(accountantContactDetails.getRole(), "Role");
    }
}
