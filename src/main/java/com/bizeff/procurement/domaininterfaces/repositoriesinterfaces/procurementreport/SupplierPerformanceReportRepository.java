package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.SupplierPerformanceReport;

import java.util.List;
import java.util.Optional;

public interface SupplierPerformanceReportRepository {
    SupplierPerformanceReport save(SupplierPerformanceReport supplierPerformanceReport);
    Optional<SupplierPerformanceReport> findById(Long id);
    void deleteById(Long id);
    List<SupplierPerformanceReport> findAll();
    void deleteAll();
}
