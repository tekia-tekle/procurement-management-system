package com.bizeff.procurement.domaininterfaces.inputds.procurementReport;

import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;

import java.time.LocalDate;

public class GenerateMonthlyApprovedPurchaseOrderReportInputDS {
    private ReporterContactDetail reporterContactDetail;
    private String reportTitle;
    private String reportDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProcurementActivity procurementActivity;
    private LocalDate reportedDate;

    public GenerateMonthlyApprovedPurchaseOrderReportInputDS() {

    }

    public GenerateMonthlyApprovedPurchaseOrderReportInputDS(ReporterContactDetail reporterContactDetail,
                                                             String reportTitle,
                                                             String reportDescription,
                                                             LocalDate startDate,
                                                             LocalDate endDate,
                                                             ProcurementActivity procurementActivity,
                                                             LocalDate reportedDate) {
        this.reporterContactDetail = reporterContactDetail;
        this.reportTitle = reportTitle;
        this.reportDescription = reportDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.procurementActivity = procurementActivity;
        this.reportedDate = reportedDate;
    }
    public ReporterContactDetail getReporterContactDetail() {
        return reporterContactDetail;
    }
    public void setReporterContactDetail(ReporterContactDetail reporterContactDetail) {
        this.reporterContactDetail = reporterContactDetail;
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
    public ProcurementActivity getProcurementActivity() {
        return procurementActivity;
    }
    public void setProcurementActivity(ProcurementActivity procurementActivity) {
        this.procurementActivity = procurementActivity;
    }
    public LocalDate getReportedDate() {
        return reportedDate;
    }
    public void setReportedDate(LocalDate reportedDate) {
        this.reportedDate = reportedDate;
    }

    @Override
    public String toString() {
        return "GenerateMonthlyApprovedPurchaseOrderReportInputDS{" +
                "reporterContactDetail=" + reporterContactDetail +
                ", reportTitle='" + reportTitle + '\'' +
                ", reportDescription='" + reportDescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", procurementActivity=" + procurementActivity +
                ", reportedDate=" + reportedDate +
                '}';
    }
}
