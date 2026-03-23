package com.bizeff.procurement.webapi.viewmodel.procurementreport;

import java.util.Map;

public class GenerateMonthlyApprovedPurchaseOrderReportViewModel {
    private String reportId;
    private String reportTitle;
    private String reportDescription;
    private String startDate;
    private String endDate;
    private Long totalPurchaseOrders;
    private String totalSpending;
    private Map<String, String> supplierSpending; //supplierId, spending
    private Map<String, Integer> supplierPurchaseOrders; //supplierId, purchaseOrders
    private Map<String, Integer> departmentPurchaseOrders; //departmentId, purchaseOrders
    private Map<String, String> departmentSpending; //departmentId, spending

    public GenerateMonthlyApprovedPurchaseOrderReportViewModel() {}

    public GenerateMonthlyApprovedPurchaseOrderReportViewModel(String reportId,
                                                               String reportTitle,
                                                               String reportDescription,
                                                               String startDate,
                                                               String endDate,
                                                               Long totalPurchaseOrders,
                                                               String totalSpending,
                                                               Map<String, String> supplierSpending,
                                                               Map<String, Integer> supplierPurchaseOrders,
                                                               Map<String, Integer> departmentPurchaseOrders,
                                                               Map<String, String> departmentSpending)
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
    public Long getTotalPurchaseOrders() {
        return totalPurchaseOrders;
    }
    public void setTotalPurchaseOrders(Long totalPurchaseOrders) {
        this.totalPurchaseOrders = totalPurchaseOrders;
    }
    public String getTotalSpending() {
        return totalSpending;
    }
    public void setTotalSpending(String totalSpending) {
        this.totalSpending = totalSpending;
    }
    public Map<String, String> getSupplierSpending() {
        return supplierSpending;
    }
    public void setSupplierSpending(Map<String, String> supplierSpending) {
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
    public Map<String, String> getDepartmentSpending() {
        return departmentSpending;
    }
    public void setDepartmentSpending(Map<String, String> departmentSpending) {
        this.departmentSpending = departmentSpending;
    }

    //toString
    @Override
    public String toString() {
        return "GenerateMonthlyApprovedPurchaseOrderReportViewModel{" +
                "reportId='" + reportId + '\'' +
                ", reportTitle='" + reportTitle + '\'' +
                ", reportDescription='" + reportDescription + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", totalPurchaseOrders=" + totalPurchaseOrders +
                ", totalSpending='" + totalSpending + '\'' +
                ", supplierSpending=" + supplierSpending +
                ", supplierPurchaseOrders=" + supplierPurchaseOrders +
                ", departmentPurchaseOrders=" + departmentPurchaseOrders +
                ", departmentSpending=" + departmentSpending +
                '}';
    }
}
