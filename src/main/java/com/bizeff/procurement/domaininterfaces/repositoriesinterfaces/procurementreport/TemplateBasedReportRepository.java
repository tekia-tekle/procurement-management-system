package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.TemplateBasedReport;

import java.util.List;
import java.util.Optional;

public interface TemplateBasedReportRepository {
    TemplateBasedReport save(TemplateBasedReport templateBasedReport);
    Optional<TemplateBasedReport> findById(Long id);
    void deleteById(Long id);
    List<TemplateBasedReport> findAll();
    List<TemplateBasedReport> findByReportTemplateId(String templateId);
    void deleteAll();

}
