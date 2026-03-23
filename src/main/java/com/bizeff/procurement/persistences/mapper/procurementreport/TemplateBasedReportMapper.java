package com.bizeff.procurement.persistences.mapper.procurementreport;

import com.bizeff.procurement.models.procurementreport.TemplateBasedReport;
import com.bizeff.procurement.persistences.entity.procurementreport.ReportDataEntity;
import com.bizeff.procurement.persistences.entity.procurementreport.ReportTemplateEntity;
import com.bizeff.procurement.persistences.entity.procurementreport.TemplateBasedReportEntity;

import java.util.Map;
import java.util.stream.Collectors;

public class TemplateBasedReportMapper {
    public static TemplateBasedReportEntity toEntity(TemplateBasedReport templateBasedReport, ReportTemplateEntity reportTemplateEntity) {
        if (templateBasedReport == null) {
            return null;
        }
        TemplateBasedReportEntity entity = new TemplateBasedReportEntity();
        if (templateBasedReport.getId() != null) {
            entity.setId(templateBasedReport.getId());
        }
        entity.setReportTemplate(reportTemplateEntity);
        entity.setProcurementActivities(templateBasedReport.getProcurementActivities());
        //convert Map<String, Object> to Map<String, String>
        entity.populateDataFromMap(templateBasedReport.getData());
        return entity;
    }
    public static TemplateBasedReport toModel(TemplateBasedReportEntity entity) {
        if (entity == null) {
            return null;
        }
        TemplateBasedReport templateBasedReport = new TemplateBasedReport();
        templateBasedReport.setId(entity.getId());
        templateBasedReport.setReportTemplate(ReportTemplateMapper.toModel(entity.getReportTemplate()));
        templateBasedReport.setProcurementActivities(entity.getProcurementActivities());
        //convert Map<String, String> to Map<String, Object>
        Map<String, Object> data = entity.getReportDataList().stream()
                .collect(Collectors.toMap(ReportDataEntity::getDataKey, ReportDataEntity::getDataValue));
        templateBasedReport.setData(data);
        return templateBasedReport;
    }
}
