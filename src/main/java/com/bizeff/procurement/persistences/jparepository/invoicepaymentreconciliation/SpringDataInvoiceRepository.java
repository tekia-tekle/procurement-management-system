package com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation;


import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataInvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    Optional<InvoiceEntity> findByInvoiceId(String invoiceId);
    void deleteByInvoiceId(String invoiceId);
    Optional<InvoiceEntity> findByPurchaseOrder_OrderId(String orderId);
    List<InvoiceEntity>findBySupplier_SupplierId(String supplierId);
    List<InvoiceEntity> findByUser_UserId(String userId);
    List<InvoiceEntity>findByInvoiceDateBetween(LocalDate startDate,LocalDate endDate);

}
