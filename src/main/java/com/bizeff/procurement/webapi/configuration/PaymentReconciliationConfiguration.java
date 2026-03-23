package com.bizeff.procurement.webapi.configuration;

import com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation.ReconcileInvoiceInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation.ViewReconciledPaymentRecordInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.ReconcileInvoiceOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.ViewReconciledPaymentRecordOutputBoundary;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.*;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation.*;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataInventoryRepository;
import com.bizeff.procurement.persistences.repositoryimplementation.invoicepaymentreconciliation.*;
import com.bizeff.procurement.services.invoicepaymentreconciliation.InvoiceReconciliationServices;
import com.bizeff.procurement.services.invoicepaymentreconciliation.PaymentReconciliationMaintainingService;
import com.bizeff.procurement.usecases.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalUseCase;
import com.bizeff.procurement.usecases.invoicepaymentreconciliation.ReconcileInvoiceUseCase;
import com.bizeff.procurement.usecases.invoicepaymentreconciliation.ViewReconciledPaymentRecordUseCase;
import com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord.NotifyingSupplierForPaymentApprovalViewModel;
import com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord.ReconcileInvoiceViewModel;
import com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord.ViewReconciledPaymentRecordViewModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentReconciliationConfiguration {

    @Bean
    public PaymentReconciliationRepository paymentReconciliationRepository(SpringDataPaymentReconciliationRepository springDataPaymentReconciliationRepository){
        return new JpaPaymentReconciliationRepository(springDataPaymentReconciliationRepository);
    }
    @Bean
    public InvoiceReconciliationServices invoiceReconciliationServices(){
        return new InvoiceReconciliationServices();
    }

    @Bean
    public ReconcileInvoiceViewModel reconcileInvoiceViewModel(){
        return new ReconcileInvoiceViewModel();
    }
    @Bean
    public ReconcileInvoiceInputBoundary reconcileInvoiceInputBoundary(InvoiceReconciliationServices invoiceReconciliationServices,
                                                                       PaymentReconciliationRepository paymentReconciliationRepository,
                                                                       DeliveryReceiptRepository deliveryReceiptRepository,
                                                                       InvoiceRepository invoiceRepository,
                                                                       PurchaseOrderRepository purchaseOrderRepository,
                                                                       ReconcileInvoiceOutputBoundary reconcileInvoiceOutputBoundary){
        return new ReconcileInvoiceUseCase(
                invoiceReconciliationServices,
                paymentReconciliationRepository,
                deliveryReceiptRepository,
                invoiceRepository,
                purchaseOrderRepository,
                reconcileInvoiceOutputBoundary);
    }

    @Bean
    public PaymentReconciliationMaintainingService paymentReconciliationMaintainingService(){
        return new PaymentReconciliationMaintainingService();
    }
    @Bean
    public NotifyingSupplierForPaymentApprovalViewModel notifyingSupplierForPaymentApprovalViewModel(){
        return new NotifyingSupplierForPaymentApprovalViewModel();
    }
    @Bean
    public NotifySupplierForPaymentApprovalInputBoundary notifySupplierForPaymentApprovalInputBoundary(PaymentReconciliationMaintainingService paymentReconciliationMaintainingService,
                                                                                                       PaymentReconciliationRepository paymentReconciliationRepository,
                                                                                                       NotifySupplierForPaymentApprovalOutputBoundary notifySupplierForPaymentApprovalOutputBoundary){

        return new NotifySupplierForPaymentApprovalUseCase(
                paymentReconciliationMaintainingService,
                paymentReconciliationRepository,
                notifySupplierForPaymentApprovalOutputBoundary);
    }

    @Bean
    public ViewReconciledPaymentRecordViewModel viewReconciledPaymentRecordViewModel(){
        return new ViewReconciledPaymentRecordViewModel();
    }
    @Bean
    public ViewReconciledPaymentRecordInputBoundary viewReconciledPaymentRecordInputBoundary(PaymentReconciliationMaintainingService paymentReconciliationMaintainingService,
                                                                                             PaymentReconciliationRepository paymentReconciliationRepository,
                                                                                             ViewReconciledPaymentRecordOutputBoundary viewReconciledPaymentRecordOutputBoundary){
        return new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);
    }

    @Bean
    public InvoiceRepository invoiceRepository(SpringDataInvoiceRepository springDataInvoiceRepository){
        return new JpaInvoiceRepository(springDataInvoiceRepository);
    }

    @Bean
    public InvoicedItemRepository invoicedItemRepository(SpringDataInvoicedItemRepository springDataInvoicedItemRepository,
                                                         SpringDataInventoryRepository springDataInventoryRepository){
        return new JpaInvoicedItemRepository(springDataInvoicedItemRepository,springDataInventoryRepository);
    }
    @Bean
    public DeliveryReceiptRepository deliveryReceiptRepository(SpringDataDeliveryReceiptsRepository springDataDeliveryReceiptsRepository){
        return new JpaDeliveryReceiptsRepository(springDataDeliveryReceiptsRepository);
    }

    @Bean
    public DeliveredItemRepository deliveredItemRepository(SpringDataDeliveredItemRepository springDataDeliveredItemRepository,
                                                           SpringDataInventoryRepository springDataInventoryRepository){

        return new JpaDeliveredItemRepository(springDataDeliveredItemRepository,springDataInventoryRepository);
    }
}
