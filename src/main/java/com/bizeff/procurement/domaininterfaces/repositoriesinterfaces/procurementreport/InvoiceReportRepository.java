package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.InvoiceReport;

import java.util.List;
import java.util.Optional;

public interface InvoiceReportRepository {
    InvoiceReport save(InvoiceReport invoiceReport);
    Optional<InvoiceReport> findById(Long id);
    void deleteById(Long id);
    List<InvoiceReport> findAll();
    void deleteAll();
}
