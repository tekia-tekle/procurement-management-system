package com.bizeff.procurement.domaininterfaces.outputds.procurementreport;

import com.bizeff.procurement.models.enums.FileFormat;

import java.time.LocalDate;
import java.util.Map;

public class ExportedProcurementDataOutputDS {
    private String reportId;
    private String reportTitle;
    private FileFormat fileFormat;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate exportedAt;
    private String exportedBy;
    private Map<String, Object> reportData;
    public ExportedProcurementDataOutputDS() {

    }

    public ExportedProcurementDataOutputDS(String reportId,
                                           String reportTitle,
                                           LocalDate startDate,
                                           LocalDate endDate,
                                           LocalDate exportedAt,
                                           String exportedBy,
                                           Map<String, Object> reportData,
                                           FileFormat fileFormat)
    {
        this.reportId = reportId;
        this.reportTitle = reportTitle;
        this.fileFormat = fileFormat;
        this.startDate = startDate;
        this.endDate = endDate;
        this.exportedAt = exportedAt;
        this.exportedBy = exportedBy;
        this.reportData = reportData;
    }

    // Getters and Setters
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
    public FileFormat getFileFormat() {
        return fileFormat;
    }
    public void setFileFormat(FileFormat fileFormat) {
        this.fileFormat = fileFormat;
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
    public LocalDate getExportedAt() {
        return exportedAt;
    }
    public void setExportedAt(LocalDate exportedAt) {
        this.exportedAt = exportedAt;
    }
    public String getExportedBy() {
        return exportedBy;
    }
    public void setExportedBy(String exportedBy) {
        this.exportedBy = exportedBy;
    }
    public Map<String, Object> getReportData() {
        return reportData;
    }
    public void setReportData(Map<String, Object> reportData) {
        this.reportData = reportData;
    }

    //toString
    @Override
    public String toString() {
        return "ExportedProcurementDataOutputDS [reportId=" + reportId + ", reportTitle=" + reportTitle + ", fileFormat=" + fileFormat + ", startDate=" + startDate + ", endDate=" + endDate + ", exportedAt=" + exportedAt + ", exportedBy=" + exportedBy + ", reportData=" + reportData + "]";
    }
}
