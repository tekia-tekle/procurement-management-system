package com.bizeff.procurement.persistences.jparepository.procurementreport;

import com.bizeff.procurement.persistences.entity.procurementreport.TemplateBasedReportEntity;

import java.util.List;

public interface SpringDataTemplateBasedReportRepository extends org.springframework.data.jpa.repository.JpaRepository<com.bizeff.procurement.persistences.entity.procurementreport.TemplateBasedReportEntity, Long> {
    List<TemplateBasedReportEntity> findByReportTemplate_TemplateId(String templateId);
}
