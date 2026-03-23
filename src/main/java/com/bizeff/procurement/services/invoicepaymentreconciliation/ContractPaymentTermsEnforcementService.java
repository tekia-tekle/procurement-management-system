package com.bizeff.procurement.services.invoicepaymentreconciliation;

import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.PaymentTerms;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;
import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;
import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.bizeff.procurement.models.enums.PaymentTerms.PREPAID;
import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.*;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.*;

public class ContractPaymentTermsEnforcementService {
//
//    private final List<PaymentReconciliation> reconciledRecords = new ArrayList<>();
//    private final List<Contract> contractsList = new ArrayList<>();
//    private final Logger logger = Logger.getLogger(ContractPaymentTermsEnforcementService.class.getName());
//
//    /**
//     * Enforces the payment terms and returns the result as a string for tracking or display purposes.
//     */
//    public String enforcePaymentTerms(String paymentId, String contractId) {
//        validateString(paymentId, "Payment Id");
//        validateString(contractId, "Contract Id");
//
//        PaymentReconciliation paymentReconciliation = getReconciledRecordById(paymentId);
//        Invoice invoice = paymentReconciliation.getInvoice();
//        Supplier supplier = invoice.getSupplier();
//
//        Contract contract = contractsList.stream()
//                .filter(c -> c.getContractId().equals(contractId)
//                        && c.getSupplier().getSupplierId().equals(supplier.getSupplierId()))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("No contract found with the given ID and supplier."));
//
//        if (!contract.getPurchaseOrders().contains(paymentReconciliation.getPurchaseOrder())) {
//            throw new IllegalArgumentException("Mismatch between payment reconciliation and contract (purchase order).");
//        }
//
//        LocalDate baseDate = invoice.getInvoiceDate();
//        LocalDate dueDate = calculateDueDate(contract.getPaymentTerms(), baseDate);
//        LocalDate paymentDate = paymentReconciliation.getPaymentDate();
//        BigDecimal paidAmount = paymentReconciliation.getActualPaidAmount();
//        BigDecimal contractAmount = contract.getTotalCost();
//
//        StringBuilder result = new StringBuilder();
//
//        if (paymentDate.isAfter(dueDate)) {
//            if (contract.isRenewable()) {
//                result.append("Payment is overdue. Contract is marked for review or renegotiation. ");
//                logger.warning("Payment is overdue for Payment ID: " + paymentId + ". Contract may require renewal review.");
//            } else {
//                result.append("Payment is overdue and contract is not renewable. ");
//                logger.warning("Payment is overdue for non-renewable contract. Payment ID: " + paymentId);
//            }
//        }
//
//        if (paidAmount.compareTo(contractAmount) > 0) {
//            result.append("Overpayment detected. Review for refund or adjustment. ");
//            logger.warning("Overpayment for Payment ID: " + paymentId + ". Paid: " + paidAmount + ", Expected: " + contractAmount);
//        } else if (paidAmount.compareTo(contractAmount) < 0) {
//            result.append("Underpayment detected. Review required. ");
//            logger.warning("Underpayment for Payment ID: " + paymentId + ". Paid: " + paidAmount + ", Expected: " + contractAmount);
//        } else {
//            result.append("Payment is compliant with contract terms. ");
//            logger.info("Payment is compliant for Payment ID: " + paymentId);
//        }
//
//        return result.toString().trim();
//    }
//
//    /** Adds a reconciled payment record. */
//    public PaymentReconciliation addReconciledRecord(PaymentReconciliation paymentRecord) {
//        validatePayment(paymentRecord);
//        reconciledRecords.add(paymentRecord);
//        return paymentRecord;
//    }
//
//    /** Adds a contract. */
//    public Contract addContracts(Contract contract) {
//        validateContracts(contract);
//        contractsList.add(contract);
//        return contract;
//    }
//
//    /** Returns all reconciled payment records. */
//    public List<PaymentReconciliation> getAllReconciledRecords() {
//        return new ArrayList<>(reconciledRecords);
//    }
//
//    /** Retrieves a reconciled payment record by its ID. */
//    public PaymentReconciliation getReconciledRecordById(String paymentId) {
//        validateString(paymentId, "Payment ID");
//        return reconciledRecords.stream()
//                .filter(record -> record.getPaymentId().equals(paymentId))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("No payment record found with ID: " + paymentId));
//    }
//
//    /** Deletes a reconciled payment record by its ID. */
//    public void deleteReconciledPayment(String paymentId) {
//        boolean removed = reconciledRecords.removeIf(payment -> payment.getPaymentId().equals(paymentId));
//        if (!removed) {
//            throw new IllegalArgumentException("Payment ID not found: " + paymentId);
//        }
//    }
//
//    /** Calculates due date based on payment terms. */
//    private LocalDate calculateDueDate(PaymentTerms paymentTerms, LocalDate startDate) {
//        return switch (paymentTerms) {
//            case PREPAID -> startDate;
//            case NET_30 -> startDate.plusDays(30);
//            case NET_60 -> startDate.plusDays(60);
//            case NET_90 -> startDate.plusDays(90);
//            default -> throw new IllegalArgumentException("Unknown payment terms.");
//        };
//    }
//
//    /** Validates a payment reconciliation record. */
//    private void validatePayment(PaymentReconciliation paymentReconciliation) {
//        if (paymentReconciliation == null)
//            throw new IllegalArgumentException("Reconciled payment cannot be null.");
//        if (paymentReconciliation.getInvoice() == null)
//            throw new IllegalArgumentException("Invoice cannot be null.");
//        if (paymentReconciliation.getPaymentId() == null || paymentReconciliation.getPaymentId().isEmpty())
//            throw new IllegalArgumentException("Payment ID cannot be null or empty.");
//        if (paymentReconciliation.getInvoice().getTotalCosts().compareTo(BigDecimal.ZERO) <= 0)
//            throw new IllegalArgumentException("Total invoice amount must be greater than zero.");
//        if (paymentReconciliation.getActualPaidAmount().compareTo(BigDecimal.ZERO) <= 0)
//            throw new IllegalArgumentException("Actual paid amount must be greater than zero.");
//        if (paymentReconciliation.getPaymentDate() == null || paymentReconciliation.getReconciliationDate() == null)
//            throw new IllegalArgumentException("Payment and reconciliation dates cannot be null.");
//        if (paymentReconciliation.getReconciliationDate().isBefore(paymentReconciliation.getPaymentDate()))
//            throw new IllegalArgumentException("Reconciliation date can't be before payment date.");
//    }
//
//    /** Validates a contract before adding. */
//    public void validateContracts(Contract contract) {
//        if (contract == null)
//            throw new NullPointerException("Contract is null.");
//        if (contract.getContractId() == null || contract.getContractId().trim().isEmpty())
//            throw new IllegalArgumentException("Contract ID can't be null or empty.");
//        if (contract.getSupplier() == null)
//            throw new NullPointerException("Supplier is required.");
//        if (contract.getContractTitle() == null || contract.getContractTitle().trim().isEmpty())
//            throw new IllegalArgumentException("Contract title must be provided.");
//        if (contract.getStartDate() == null || contract.getStartDate().isBefore(LocalDate.now()))
//            throw new IllegalArgumentException("Start date can't be null or in the past.");
//        if (contract.getEndDate().isBefore(contract.getStartDate()))
//            throw new IllegalArgumentException("End date can't be before start date.");
//        if (contract.getTotalCost().compareTo(BigDecimal.ZERO) < 0)
//            throw new IllegalArgumentException("Total cost must be non-negative.");
//        if (contract.getDeliveryTerms() == null)
//            throw new IllegalArgumentException("Delivery terms must be specified.");
//        if (contract.getPaymentTerms() == null)
//            throw new IllegalArgumentException("Payment terms must be specified.");
//        if (contract.getStatus() == null)
//            throw new IllegalArgumentException("Contract status must be specified.");
//        if (contract.getPurchaseOrders() == null)
//            throw new IllegalArgumentException("Associated purchase orders must be provided.");
//    }

    /////////////////////////////////////////////////////////////
    private List<Contract> contractList;
    private List<Invoice> invoiceList;
    private List<Supplier> supplierList;
    private List<PurchaseOrder> purchaseOrderList;
    private List<DeliveryReceipt> deliveryReceiptList;
    private Map<String,PaymentReconciliation> paymentReconciliationMap;
    private Logger logger = Logger.getLogger(ContractPaymentTermsEnforcementService.class.getName());

    public ContractPaymentTermsEnforcementService() {
        this.contractList = new ArrayList<>();
        this.invoiceList = new ArrayList<>();
        this.supplierList = new ArrayList<>();
        this.purchaseOrderList = new ArrayList<>();
        this.deliveryReceiptList = new ArrayList<>();
        this.paymentReconciliationMap = new HashMap<>();
    }
    public Contract addContract(Contract contract) {
        validateContract(contract);
        boolean existedContract = contractList.stream().anyMatch(c -> c.getContractId().equals(contract.getContractId()));
        if(existedContract) {
            throw new IllegalArgumentException("we can't add new contract with contract Id: "+ contract.getContractId()+"since it already exists.");
        }
        contractList.add(contract);
        return contract;
    }
    public Invoice addInvoice(Invoice invoice) {
        validateInvoice(invoice);
        boolean existedInvoice = invoiceList.stream().anyMatch(i -> i.getInvoiceId().equals(invoice.getInvoiceId()));
        if(existedInvoice) {
            throw new IllegalArgumentException("we can't create new Invoice with Invoice Id: "+ invoice.getInvoiceId()+ "since it already exists.");
        }
        invoiceList.add(invoice);
        return invoice;
    }
    public Supplier addSupplier(Supplier supplier) {
        validateSupplier(supplier);
        boolean existedSupplier = supplierList.stream().anyMatch(s -> s.getSupplierId().equals(supplier.getSupplierId()));
        if(existedSupplier) {
            throw new IllegalArgumentException("we can't add new supplier with supplier Id:"+ supplier.getSupplierId()+"since it already exists.");
        }
        supplierList.add(supplier);
        return supplier;
    }
    public PurchaseOrder addPurchaseOrder(PurchaseOrder purchaseOrder) {
        validatePurchaseOrder(purchaseOrder);
        boolean existedPurchaseOrder = purchaseOrderList.stream().anyMatch(p -> p.getOrderId().equals(purchaseOrder.getOrderId()));
        if(existedPurchaseOrder) {
            throw new IllegalArgumentException("we can't create new purchase order with orderId: "+purchaseOrder.getOrderId()+"since it already exists.");
        }
        purchaseOrderList.add(purchaseOrder);
        return purchaseOrder;
    }
    public DeliveryReceipt addDeliveryReceipt(DeliveryReceipt deliveryReceipt) {
        validateDeliveryReceipt(deliveryReceipt);
        boolean existedDeliveryReceipt = deliveryReceiptList.stream().anyMatch(d -> d.getReceiptId().equals(deliveryReceipt.getReceiptId()));
        if(existedDeliveryReceipt) {
            throw new IllegalArgumentException("we can't create new delivery Receipt with receiptId :"+ deliveryReceipt.getReceiptId()+"since it already exists.");
        }
        deliveryReceiptList.add(deliveryReceipt);
        return deliveryReceipt;
    }
    public PaymentReconciliation addPaymentReconciliation(PaymentReconciliation paymentReconciliation) {
        boolean existedPaymentReconciliation = paymentReconciliationMap.containsKey(paymentReconciliation.getPaymentId());
        if(existedPaymentReconciliation) {
            throw new IllegalArgumentException("we can't create new PaymentReconciliation with the payment Id"+paymentReconciliation.getPaymentId()+ "since it already exists.");
        }
        paymentReconciliationMap.put(paymentReconciliation.getPaymentId(),paymentReconciliation);

        return paymentReconciliation;
    }

    public void enforceContractPaymentTerm(String contractId) {
        validateString(contractId,"contractId");
        Contract contract = getContractById(contractId);
        Supplier supplier = contract.getSupplier();
        boolean matchedSupplier = supplierList.stream().anyMatch(s -> s.getSupplierId().equals(supplier.getSupplierId()));
        if(!matchedSupplier) {
            throw new IllegalArgumentException("the supplier with supplierId: "+supplier.getSupplierId()+" does not exist.");
        }

        List<PurchaseOrder> purchaseOrders = contract.getPurchaseOrders();
        for(PurchaseOrder purchaseOrder : purchaseOrders) {
            if(!purchaseOrder.getSupplier().getSupplierId().equals(supplier.getSupplierId())) {
                throw new IllegalArgumentException("the purchase order with orderId: "+purchaseOrder.getOrderId()+" does not match the supplier.");
            }

            boolean existedPurchaseOrder = purchaseOrderList.stream().anyMatch(p -> p.getOrderId().equals(purchaseOrder.getOrderId()));
            if(!existedPurchaseOrder) {
                throw new IllegalArgumentException("the purchase order with orderId: "+purchaseOrder.getOrderId()+" does not exist.");
            }
            Invoice invoice = invoiceList.stream().filter(i -> i.getPurchaseOrder().getOrderId().equals(purchaseOrder.getOrderId())).findFirst().orElseThrow(()->new IllegalArgumentException("No invoice associated with purchase order Id:"+purchaseOrder.getOrderId()));
            DeliveryReceipt deliveryReceipt = deliveryReceiptList.stream().filter(d -> d.getPurchaseOrder().getOrderId().equals(purchaseOrder.getOrderId())).findFirst().orElseThrow(()->new IllegalArgumentException("No delivery receipt associated with purchase order Id:"+purchaseOrder.getOrderId()));

            LocalDate baseDate = invoice.getInvoiceDate();
            LocalDate dueDate = calculateDueDate(contract.getPaymentTerms(), baseDate);
            LocalDate paymentDate = invoice.getPaymentDate();
            BigDecimal paidAmount = deliveryReceipt.getTotalCost();
            BigDecimal contractAmount = contract.getTotalCost();
            BigDecimal expectedAmount = calculateExpectedAmount(deliveryReceipt, invoice);
            if (paymentDate.isAfter(contract.getEndDate())){
                throw new IllegalArgumentException("The payment date is after the contract end date.");
            }
            if (contract.getPaymentTerms() == PREPAID && paymentDate.isAfter(invoice.getInvoiceDate())) {
                throw new IllegalArgumentException("Payment for PREPAID contract must be on or before invoice date.");
            }
            if (paymentDate.isAfter(dueDate)) {
                if (paymentDate.isAfter(contract.getEndDate())) {
                    throw new IllegalArgumentException("Payment date is after contract end date.");
                } else {
                    long overdueDays = paymentDate.toEpochDay() - dueDate.toEpochDay();
                    logger.warning("Payment is overdue by " + overdueDays + " days. Must pay before contract end date: " + contract.getEndDate());
                }
            }
            if (expectedAmount.compareTo(contractAmount)> 0){
                throw new IllegalArgumentException("The expected amount is greater than the contract amount.");
            }

            PaymentReconciliation paymentReconciliation = new PaymentReconciliation(invoice,purchaseOrder,deliveryReceipt,expectedAmount,paidAmount,invoice.getCurrency(),paymentDate,dueDate);
            paymentReconciliationMap.put(paymentReconciliation.getPaymentId(),paymentReconciliation);
        }
    }
    public Invoice getInvoiceById(String invoiceId) {
        validateString(invoiceId,"invoiceId");
        return invoiceList.stream().filter(i -> i.getInvoiceId().equals(invoiceId)).findFirst().orElseThrow(()->new IllegalArgumentException("No invoice found with ID: " + invoiceId));
    }
    public Contract getContractById(String contractId) {
        validateString(contractId,"contractId");
        return contractList.stream().filter(c -> c.getContractId().equals(contractId)).findFirst().orElseThrow(()->new IllegalArgumentException("No contract found with ID: " + contractId));
    }
    public Supplier getSupplierById(String supplierId) {
        validateString(supplierId, "supplierId");
        return supplierList.stream().filter(s -> s.getSupplierId().equals(supplierId)).findFirst().orElseThrow(() -> new IllegalArgumentException("No supplier found with ID: " + supplierId));
    }
    public PurchaseOrder getPurchaseOrderById(String orderId) {
        validateString(orderId, "orderId");
        return purchaseOrderList.stream().filter(p -> p.getOrderId().equals(orderId)).findFirst().orElseThrow(() -> new IllegalArgumentException("No purchase order found with ID: " + orderId));
    }
    public DeliveryReceipt getDeliveryReceiptById(String receiptId) {
        validateString(receiptId, "receiptId");
        return deliveryReceiptList.stream().filter(d -> d.getReceiptId().equals(receiptId)).findFirst().orElseThrow(() -> new IllegalArgumentException("No delivery receipt found with ID: " + receiptId));
    }

    public PaymentReconciliation getPaymentReconciliationById(String paymentId) {
        validateString(paymentId,"paymentId");
        PaymentReconciliation paymentReconciliation = paymentReconciliationMap.get(paymentId);
        if(paymentReconciliation == null) {
            throw new IllegalArgumentException("there is no payment Reconciliation with paymentId:"+paymentId);
        }
        return paymentReconciliation;
    }
    public PaymentReconciliation getPaymentReconciliationByInvoiceId(String invoiceId) {
        validateString(invoiceId,"invoiceId");
        return paymentReconciliationMap.values().stream().filter(p -> p.getInvoice().getInvoiceId().equals(invoiceId)).findFirst().orElseThrow(() -> new IllegalArgumentException("No payment reconciliation found with invoice ID: " + invoiceId));
    }
    public List<Contract> getContracts() {
        return contractList;
    }
    public List<Invoice> getInvoices() {
        return invoiceList;
    }
    public List<Supplier> getSuppliers() {
        return supplierList;
    }
    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrderList;
    }
    public List<DeliveryReceipt> getDeliveryReceipts() {
        return deliveryReceiptList;
    }
    public Map<String, PaymentReconciliation> getPaymentReconciliationMap() {
        return paymentReconciliationMap;
    }

    //helper methods
    public LocalDate calculateDueDate(PaymentTerms paymentTerms, LocalDate startDate){
        return switch (paymentTerms) {
            case PREPAID -> startDate;
            case NET_30 -> startDate.plusDays(30);
            case NET_60 -> startDate.plusDays(60);
            case NET_90 -> startDate.plusDays(90);
            default -> throw new IllegalArgumentException("Unknown payment terms: " + paymentTerms);
        };
    }
    public BigDecimal calculateExpectedAmount(DeliveryReceipt deliveryReceipt, Invoice invoice) {
        BigDecimal invoiceSubtotal = invoice.getSubtotal();
        // Match only items delivered and invoiced
        BigDecimal deliverySubtotal = deliveryReceipt.getReceivedItems().stream()
                .filter(delItem -> invoice.getInvoiceItems().stream().anyMatch(invItem -> invItem.getInventory().getItemId().equals(delItem.getInventory().getItemId())))
                .map(item -> item.getTotalPrice() != null ? item.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (invoiceSubtotal.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        BigDecimal ratio = deliverySubtotal.divide(invoiceSubtotal, 4, BigDecimal.ROUND_HALF_UP);
        return deliverySubtotal
                .add(invoice.getShippingCost().multiply(ratio))
                .add(invoice.getTaxes().multiply(ratio))
                .subtract(invoice.getDiscount().multiply(ratio));
    }
    public void validateContract(Contract contract) {
        validateNotNull(contract,"Contract");
        validateString(contract.getContractId(), "Contract Id");
        validateSupplier(contract.getSupplier());
        validateString(contract.getContractTitle(),"Contract Title");
        validateDateNotInPast(contract.getStartDate(), "Delivery Date");
        validatePositiveValue(contract.getTotalCost(), "Total Cost");
        validateDateOrder(contract.getStartDate(), contract.getEndDate());
        if (contract.getEndDate().isBefore(contract.getStartDate())) {
            throw new IllegalArgumentException("Contract end date cannot be before the start date.");
        }
        validateNotEmptyList(contract.getPurchaseOrders(), "Purchase Orders");
    }
    public void validateSupplier(Supplier supplier) {
        validateNotNull(supplier, "Supplier ");
        validateString(supplier.getSupplierName(), "Supplier name");
        validateString(supplier.getSupplierCategory(), "Supplier Category");
        validateString(supplier.getTaxIdentificationNumber(), "Tax Identification Number");
        validateString(supplier.getRegistrationNumber(), "Registration Number");
        validateSupplierContactDetail(supplier.getSupplierContactDetail());
        validateItems(supplier.getExistedItems());
        validateSupplierPaymentMethod(supplier.getSupplierPaymentMethods());
        validateDate(supplier.getRegistrationDate(),"Registration Date");
    }
    public void validatePurchaseOrder(PurchaseOrder purchaseOrder) {
        validateNotNull(purchaseOrder,"Purchase Order");
        validateString(purchaseOrder.getOrderId(), "Order ID");
        validateNotNull(purchaseOrder.getDepartment(), "Department");
        validateNotNull(purchaseOrder.getRequisitionList(), "Purchase Requisition");
        validateNotEmptyList(purchaseOrder.getRequisitionList(),"Purchase Requisition");
        validateDateNotInPast(purchaseOrder.getOrderDate(), "Order Date");
        validateNotNull(purchaseOrder.getSupplier(), "Supplier");
        validateString(purchaseOrder.getShippingMethod(), "Shipping Method");
        validateDateNotInPast(purchaseOrder.getDeliveryDate(), "Delivery Date");
        validatePositiveValue(purchaseOrder.getTotalCost(), "Total Cost");
        BigDecimal totalRequisitionCost = purchaseOrder.getRequisitionList().stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (purchaseOrder.getTotalCost().compareTo(totalRequisitionCost) < 0){
            throw new IllegalArgumentException("cost for Order can't be less than cost for requisition.");
        }
    }
    public void validateInvoice(Invoice invoice){
        validateString(invoice.getInvoiceId(),"Invoice Id");
        validateDate(invoice.getInvoiceDate(), " Invoice Date");
        validateNotNull(invoice.getSupplier(),"Supplier");
        validatePurchaseOrder(invoice.getPurchaseOrder());
        validateDate(invoice.getDueDate(),"Due Date");
        validateDate(invoice.getPaymentDate(), "Payment Date");
        validatePositiveValue(invoice.getTotalCosts(), "Invoice Total Costs");
        validateNotNull(invoice.getCreatedBy(),"User");

    }
    public void validateDeliveryReceipt(DeliveryReceipt deliveryReceipt){
        validateString(deliveryReceipt.getReceiptId(),"Receipts Id");
        validateSupplier(deliveryReceipt.getSupplier());
        validatePurchaseOrder(deliveryReceipt.getPurchaseOrder());
        validateNotEmptyList(deliveryReceipt.getReceivedItems(),"Received Items");
        validatePositiveValue(deliveryReceipt.getTotalCost(),"deliveryReceipts TotalCosts");
        validateNotNull(deliveryReceipt.getReceivedBy(), "User");
        validateDate(deliveryReceipt.getDeliveryDate(), "Delivered Date.");
    }
    public static void validateDateOrder(LocalDate startDate, LocalDate endDate) {
        validateDate(startDate, "Start date");
        if (endDate == null) {
            throw new IllegalArgumentException("end date must not be null.");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
    }
}
