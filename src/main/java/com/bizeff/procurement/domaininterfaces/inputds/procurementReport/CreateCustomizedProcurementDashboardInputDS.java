package com.bizeff.procurement.domaininterfaces.inputds.procurementReport;

import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;

import java.util.List;

public class CreateCustomizedProcurementDashboardInputDS {
    private ReporterContactDetail reporterContactDetail;
    private String templateId;
    private String templateName;
    private List<ProcurementActivity> procurementActivities;
    private List<String> selectedFields;

    //Default constructor
    public CreateCustomizedProcurementDashboardInputDS() {

    }

    //Parameterized constructor
    public CreateCustomizedProcurementDashboardInputDS(ReporterContactDetail reporterContactDetail,
                                                       String templateId,
                                                       String templateName,
                                                       List<ProcurementActivity> procurementActivities,
                                                       List<String> selectedFields)
    {
        this.reporterContactDetail = reporterContactDetail;
        this.templateId = templateId;
        this.templateName = templateName;
        this.procurementActivities = procurementActivities;
        this.selectedFields = selectedFields;

    }

    // getter and setter
    public ReporterContactDetail getReporterContactDetail() {
        return reporterContactDetail;
    }
    public void setReporterContactDetail(ReporterContactDetail reporterContactDetail) {
        this.reporterContactDetail = reporterContactDetail;
    }
    public String getTemplateId() {
        return templateId;
    }
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    public String getTemplateName() {
        return templateName;
    }
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    public List<ProcurementActivity> getProcurementActivities() {
        return procurementActivities;
    }
    public void setProcurementActivities(List<ProcurementActivity> procurementActivities) {
        this.procurementActivities = procurementActivities;
    }

    public List<String> getSelectedFields() {
        return selectedFields;
    }
    public void setSelectedFields(List<String> selectedFields) {
        this.selectedFields = selectedFields;
    }

    //toString method


    @Override
    public String toString() {
        return "CreateCustomizedProcurementDashboardInputDS{" +
                "reporterContactDetail=" + reporterContactDetail +
                ", templateId='" + templateId + '\'' +
                ", templateName='" + templateName + '\'' +
                ", procurementActivities=" + procurementActivities +
                ", selectedFields=" + selectedFields +
                '}';
    }
}
