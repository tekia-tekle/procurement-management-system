package com.bizeff.procurement.persistences.jparepository.procurementreport;

import com.bizeff.procurement.persistences.entity.procurementreport.ReportTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataReportTemplateRepository extends JpaRepository<ReportTemplateEntity,Long> {
    Optional<ReportTemplateEntity>findByTemplateId(String templateId);
    void deleteByTemplateId(String templateId);
}
