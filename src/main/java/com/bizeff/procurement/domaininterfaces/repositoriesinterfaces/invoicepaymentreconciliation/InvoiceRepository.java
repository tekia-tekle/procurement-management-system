package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation;

import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {
    Optional<Invoice> findByInvoiceId(String invoiceId);

    Invoice save(Invoice invoice);

    void deleteByInvoiceId(String invoiceId);

    List<Invoice> findAll();
    void deleteAll();
    List<Invoice> findBySupplierId(String supplierId);

    Optional<Invoice> findByPurchaseOrderId(String purchaseOrderId);

    List<Invoice> findInvoiceByUserId(String userId);

    List<Invoice> findByInvoiceDateBetween(LocalDate startDate, LocalDate endDate);
}
