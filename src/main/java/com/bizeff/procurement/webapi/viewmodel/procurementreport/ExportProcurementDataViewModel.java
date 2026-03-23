package com.bizeff.procurement.webapi.viewmodel.procurementreport;

import java.util.Map;

public class ExportProcurementDataViewModel {
    private String reportId;
    private String reportTitle;
    private String startDate;
    private String endDate;
    private String format; // e.g., "PDF", "Excel", "CSV"
    private String exportedBy;
    private String exportedAt;
    private Map<String, Object> reportData;

    public ExportProcurementDataViewModel() {}

    public ExportProcurementDataViewModel(String reportId,
                                          String reportTitle,
                                          String startDate,
                                          String endDate,
                                          String format,
                                          String exportedBy,
                                          String exportedAt,
                                          Map<String, Object> reportData)
    {
        this.reportId = reportId;
        this.reportTitle = reportTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.format = format;
        this.exportedBy = exportedBy;
        this.exportedAt = exportedAt;
        this.reportData = reportData;
    }
    public String getReportId() {
        return reportId;
    }
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    public String getReportTitle() {
        return reportTitle;
    }
    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public String getExportedBy() {
        return exportedBy;
    }
    public void setExportedBy(String exportedBy) {
        this.exportedBy = exportedBy;
    }
    public String getExportedAt() {
        return exportedAt;
    }
    public void setExportedAt(String exportedAt) {
        this.exportedAt = exportedAt;
    }
    public Map<String, Object> getReportData() {
        return reportData;
    }
    public void setReportData(Map<String, Object> reportData) {
        this.reportData = reportData;
    }
    @Override
    public String toString() {
        return "ExportProcurementDataViewModel{" +
                "reportId='" + reportId + '\'' +
                ", reportTitle='" + reportTitle + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", format='" + format + '\'' +
                ", exportedBy='" + exportedBy + '\'' +
                ", exportedAt='" + exportedAt + '\'' +
                ", reportData=" + reportData +
                '}';
    }
}