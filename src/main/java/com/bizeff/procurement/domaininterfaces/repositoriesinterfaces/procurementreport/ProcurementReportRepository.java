package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.ProcurementReport;

import java.util.List;
import java.util.Optional;

public interface ProcurementReportRepository {
    ProcurementReport save(ProcurementReport procurementReport);
    Optional<ProcurementReport> findByReportId(String reportId);
    void deleteByReportId(String reportId);
    Optional<ProcurementReport> findById(Long id);
    void deleteById(Long id);
    List<ProcurementReport> findAll();
    void deleteAll();

}
