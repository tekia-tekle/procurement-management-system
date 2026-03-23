package com.bizeff.procurement.models.procurementreport;

import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;

import java.util.List;
import java.util.Map;

public class TemplateBasedReport {
    private Long id;
    private ReportTemplate reportTemplate;
    private List<ProcurementActivity> procurementActivities;
    private Map<String, Object> data;
    public TemplateBasedReport() {}
    public TemplateBasedReport(ReportTemplate reportTemplate,
                               List<ProcurementActivity>procurementActivities,
                               Map<String, Object> data) {
        this.reportTemplate = reportTemplate;
        this.procurementActivities = procurementActivities;
        this.data = data;
    }
    //getter and setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ReportTemplate getReportTemplate() {
        return reportTemplate;
    }
    public void setReportTemplate(ReportTemplate reportTemplate) {
        this.reportTemplate = reportTemplate;
    }
    public List<ProcurementActivity> getProcurementActivities() {
        return procurementActivities;
    }
    public void setProcurementActivities(List<ProcurementActivity> procurementActivities) {
        this.procurementActivities = procurementActivities;
    }
    public Map<String, Object> getData() {
        return data;
    }
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    //toString
    @Override
    public String toString() {
        return "TemplateBasedReport{" +
                "reportTemplate=" + reportTemplate +
                ", procurementActivities=" + procurementActivities +
                ", data=" + data +
                '}';
    }
}
