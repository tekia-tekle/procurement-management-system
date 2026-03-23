package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.SupplierReport;

import java.util.List;
import java.util.Optional;

public interface SupplierReportRepository {
    SupplierReport save(SupplierReport supplierReport);
    Optional<SupplierReport> findById(Long id);
    void deleteById(Long id);
    List<SupplierReport> findAll();
    void deleteAll();
}
