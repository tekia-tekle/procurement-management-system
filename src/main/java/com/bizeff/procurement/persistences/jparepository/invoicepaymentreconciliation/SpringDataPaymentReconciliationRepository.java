package com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation;

import com.bizeff.procurement.models.enums.ReconciliationStatus;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.PaymentReconciliationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataPaymentReconciliationRepository extends JpaRepository<PaymentReconciliationEntity, Long> {
    Optional<PaymentReconciliationEntity>findByPaymentId(String paymentId);
    List<PaymentReconciliationEntity>findByInvoice_InvoiceId(String invoiceId);
    List<PaymentReconciliationEntity>findByPurchaseOrder_OrderId(String orderId);
    List<PaymentReconciliationEntity>findByDeliveryReceipt_ReceiptId(String receiptId);
    List<PaymentReconciliationEntity>findByReconciliationStatus(ReconciliationStatus status);
    List<PaymentReconciliationEntity>findByReconciliationDateBefore(LocalDate date);
    void deleteByPaymentId(String paymentId);
}
