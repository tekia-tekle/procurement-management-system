package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation;
import com.bizeff.procurement.models.enums.ReconciliationStatus;
import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentReconciliationRepository {
    PaymentReconciliation save(PaymentReconciliation paymentReconciliation);
    Optional<PaymentReconciliation> findByPaymentId(String paymentId);

    List<PaymentReconciliation> findByInvoiceId(String invoiceId);
    List<PaymentReconciliation> findByOrderId(String orderId);
    List<PaymentReconciliation> findByReceiptId(String receiptId);
    List<PaymentReconciliation> findByStatus(ReconciliationStatus status);
    List<PaymentReconciliation> findByReconciliationDateBefore(LocalDate date);

    List<PaymentReconciliation> findAll();
    void deleteByPaymentId(String paymentId);

    void deleteAll();
}