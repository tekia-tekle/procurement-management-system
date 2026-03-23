package com.bizeff.procurement.domaininterfaces.inputds.procurementReport;

import com.bizeff.procurement.models.enums.FileFormat;
import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;

import java.time.LocalDate;
import java.util.List;

public class ExportProcurementDataInputDS {
    private ReporterContactDetail reporterContactDetail;

    private String templateId;
    private LocalDate startDate;
    private LocalDate endDate;
    private FileFormat exportFormat; // CSV, EXCEL, PDF, JSON
    private List<ProcurementActivity> includedActivities;
    private List<String> selectedFields;
    private String reportTitle;
    private String reportDescription;
    public ExportProcurementDataInputDS() {

    }

    public ExportProcurementDataInputDS(ReporterContactDetail reporterContactDetail,
                                        String templateId,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        FileFormat exportFormat,
                                        List<ProcurementActivity> includedActivities,
                                        List<String> selectedFields,
                                        String reportTitle,
                                        String reportDescription) {
        this.reporterContactDetail = reporterContactDetail;
        this.templateId = templateId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.exportFormat = exportFormat;
        this.includedActivities = includedActivities;
        this.selectedFields = selectedFields;
        this.reportTitle = reportTitle;
        this.reportDescription = reportDescription;
    }

    //getter and setter.

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
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public FileFormat getExportFormat() {
        return exportFormat;
    }

    public void setExportFormat(FileFormat exportFormat) {
        this.exportFormat = exportFormat;
    }

    public List<ProcurementActivity> getIncludedActivities() {
        return includedActivities;
    }

    public void setIncludedActivities(List<ProcurementActivity> includedActivities) {
        this.includedActivities = includedActivities;
    }

    public List<String> getSelectedFields() {
        return selectedFields;
    }

    public void setSelectedFields(List<String> selectedFields) {
        this.selectedFields = selectedFields;
    }
    public String getReportTitle() {
        return reportTitle;
    }
    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
    public String getReportDescription() {
        return reportDescription;
    }
    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    @Override
    public String toString() {
        return "ExportProcurementDataInputDS{" +
                "reporterContactDetail=" + reporterContactDetail +
                ", templateId='" + templateId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", exportFormat=" + exportFormat +
                ", includedActivities=" + includedActivities +
                ", selectedFields=" + selectedFields +
                ", reportTitle='" + reportTitle + '\'' +
                ", reportDescription='" + reportDescription + '\'' +
                '}';
    }
}
