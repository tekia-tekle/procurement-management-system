package com.bizeff.procurement.webapi.viewmodel.procurementreport;

import java.util.Map;

public class CreateCustomizedProcurementDashboardViewModel {
    private String dashboardId;
    private String dashboardTitle;
    private Map<String, Object> dashboardData;
    private String createdBy;
    private String createdAt;

    public CreateCustomizedProcurementDashboardViewModel() {}
    public CreateCustomizedProcurementDashboardViewModel(String dashboardId,
                                                         String dashboardTitle,
                                                         Map<String, Object> dashboardData,
                                                         String createdBy,
                                                         String createdAt)
    {
        this.dashboardId = dashboardId;
        this.dashboardTitle = dashboardTitle;
        this.dashboardData = dashboardData;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
    public String getDashboardId() {
        return dashboardId;
    }
    public void setDashboardId(String dashboardId) {
        this.dashboardId = dashboardId;
    }
    public String getDashboardTitle() {
        return dashboardTitle;
    }
    public void setDashboardTitle(String dashboardTitle) {
        this.dashboardTitle = dashboardTitle;
    }
    public Map<String, Object> getDashboardData() {
        return dashboardData;
    }
    public void setDashboardData(Map<String, Object> dashboardData) {
        this.dashboardData = dashboardData;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public String toString() {
        return "CreateCustomizedProcurementDashboardViewModel [" +
                "dashboardId=" + dashboardId +
                ", dashboardTitle=" + dashboardTitle +
                ", dashboardData=" + dashboardData +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                "]";
    }

}
