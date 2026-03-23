package com.bizeff.procurement.domaininterfaces.outputds.procurementreport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class GenerateMonthlyApprovedPurchaseOrderReportOutPutDS {
    private String reportId;
    private String reportTitle;
    private String reportDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long totalPurchaseOrders;
    private BigDecimal totalSpending;
    private Map<String, BigDecimal> supplierSpending; //supplierId, spending
    private Map<String, Integer> supplierPurchaseOrders; //supplierId, purchaseOrders
    private Map<String, Integer> departmentPurchaseOrders; //departmentId, purchaseOrders
    private Map<String, BigDecimal> departmentSpending; //departmentId, spending
    //default constructor
    public GenerateMonthlyApprovedPurchaseOrderReportOutPutDS() {}
    //parameterized constructor
    public GenerateMonthlyApprovedPurchaseOrderReportOutPutDS(String reportId,
                                                              String reportTitle,
                                                              String reportDescription,
                                                              LocalDate startDate,
                                                              LocalDate endDate,
                                                              Long totalPurchaseOrders,
                                                              BigDecimal totalSpending,
                                                              Map<String, BigDecimal> supplierSpending,
                                                              Map<String, Integer> supplierPurchaseOrders,
                                                              Map<String, Integer> departmentPurchaseOrders,
                                                              Map<String, BigDecimal> departmentSpending)
    {
        this.reportId = reportId;
        this.reportTitle = reportTitle;
        this.reportDescription = reportDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPurchaseOrders = totalPurchaseOrders;
        this.totalSpending = totalSpending;
        this.supplierSpending = supplierSpending;
        this.supplierPurchaseOrders = supplierPurchaseOrders;
        this.departmentPurchaseOrders = departmentPurchaseOrders;
        this.departmentSpending = departmentSpending;
    }

    //Getter and setter
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
    public Long getTotalPurchaseOrders() {
        return totalPurchaseOrders;
    }
    public void setTotalPurchaseOrders(Long totalPurchaseOrders) {
        this.totalPurchaseOrders = totalPurchaseOrders;
    }
    public BigDecimal getTotalSpending() {
        return totalSpending;
    }
    public void setTotalSpending(BigDecimal totalSpending) {
        this.totalSpending = totalSpending;
    }
    public Map<String, BigDecimal> getSupplierSpending() {
        return supplierSpending;
    }
    public void setSupplierSpending(Map<String, BigDecimal> supplierSpending) {
        this.supplierSpending = supplierSpending;
    }
    public Map<String, Integer> getSupplierPurchaseOrders() {
        return supplierPurchaseOrders;
    }
    public void setSupplierPurchaseOrders(Map<String, Integer> supplierPurchaseOrders) {
        this.supplierPurchaseOrders = supplierPurchaseOrders;
    }
    public Map<String, Integer> getDepartmentPurchaseOrders() {
        return departmentPurchaseOrders;
    }
    public void setDepartmentPurchaseOrders(Map<String, Integer> departmentPurchaseOrders) {
        this.departmentPurchaseOrders = departmentPurchaseOrders;
    }
    public Map<String, BigDecimal> getDepartmentSpending() {
        return departmentSpending;
    }
    public void setDepartmentSpending(Map<String, BigDecimal> departmentSpending) {
        this.departmentSpending = departmentSpending;
    }

    @Override
    public String toString() {
        return "GenerateMonthlyApprovedPurchaseOrderReportOutPutDS{" +
                "reportId='" + reportId + '\'' +
                ", reportTitle='" + reportTitle + '\'' +
                ", report Description='" + reportDescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalPurchaseOrders=" + totalPurchaseOrders +
                ", totalSpending=" + totalSpending +
                ", supplierSpending=" + supplierSpending +
                ", supplierPurchaseOrders=" + supplierPurchaseOrders +
                ", departmentPurchaseOrders=" + departmentPurchaseOrders +
                ", departmentSpending=" + departmentSpending +
                '}';
    }
}
