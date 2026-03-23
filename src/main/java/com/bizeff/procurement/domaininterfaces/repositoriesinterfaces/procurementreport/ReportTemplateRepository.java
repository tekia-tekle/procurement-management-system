package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.ReportTemplate;

import java.util.List;
import java.util.Optional;

public interface ReportTemplateRepository {
    ReportTemplate save(ReportTemplate reportTemplate);
    ReportTemplate update(ReportTemplate reportTemplate);
    Optional<ReportTemplate> findBytemplateId(String templateId);
    void deleteBytemplateId(String templateId);
    void deleteById(Long id);
    List<ReportTemplate> findAll();
    void deleteAll();

}
