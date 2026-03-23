package com.bizeff.procurement.persistences.mapper.procurementreport;

import com.bizeff.procurement.models.procurementreport.ReportTemplate;
import com.bizeff.procurement.persistences.entity.procurementreport.ReportTemplateEntity;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.UserMapper;

public class ReportTemplateMapper {
    public static ReportTemplateEntity toEntity(ReportTemplate reportTemplate) {
        if (reportTemplate == null) return null;
        ReportTemplateEntity reportTemplateEntity = new ReportTemplateEntity();
        if (reportTemplate.getId() != null){
            reportTemplateEntity.setId(reportTemplate.getId());
        }
        reportTemplateEntity.setTemplateId(reportTemplate.getTemplateId());
        reportTemplateEntity.setTemplateName(reportTemplate.getTemplateName());
        reportTemplateEntity.setTemplateDescription(reportTemplate.getTemplateDescription());
        reportTemplateEntity.setSelectedFields(reportTemplate.getSelectedFields());
        reportTemplateEntity.setCreatedAt(reportTemplate.getCreatedAt());
        reportTemplateEntity.setCreatedBy(UserMapper.toEntity(reportTemplate.getCreatedBy()));

        return reportTemplateEntity;
    }

    public static ReportTemplate toModel(ReportTemplateEntity reportTemplateEntity) {
        if (reportTemplateEntity == null) return null;
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setId(reportTemplateEntity.getId());
        reportTemplate.setTemplateId(reportTemplateEntity.getTemplateId());
        reportTemplate.setTemplateName(reportTemplateEntity.getTemplateName());
        reportTemplate.setTemplateDescription(reportTemplateEntity.getTemplateDescription());
        reportTemplate.setSelectedFields(reportTemplateEntity.getSelectedFields());
        reportTemplate.setCreatedAt(reportTemplateEntity.getCreatedAt());
        reportTemplate.setCreatedBy(UserMapper.toDomain(reportTemplateEntity.getCreatedBy()));

        return reportTemplate;
    }
}
