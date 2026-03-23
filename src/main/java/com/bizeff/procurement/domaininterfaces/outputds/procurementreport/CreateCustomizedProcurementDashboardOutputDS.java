package com.bizeff.procurement.domaininterfaces.outputds.procurementreport;

import com.bizeff.procurement.models.purchaserequisition.User;
import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CreateCustomizedProcurementDashboardOutputDS {
    private String dashboardId;
    private String dashboardTitle;
    private List<ProcurementActivity>procurementActivities;
    private Map<String, Object> reportData;
    private User createdBy;
    private LocalDate createdAt;

    //Default constructor
    public CreateCustomizedProcurementDashboardOutputDS() {

    }

    //Parameterized constructor
    public CreateCustomizedProcurementDashboardOutputDS(String dashboardId,
                                                        String dashboardTitle,
                                                        List<ProcurementActivity>procurementActivities,
                                                        Map<String, Object> reportData,
                                                        User createdBy,
                                                        LocalDate createdAt)
    {
        this.dashboardId = dashboardId;
        this.dashboardTitle = dashboardTitle;
        this.procurementActivities = procurementActivities;
        this.reportData = reportData;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
    //Getter and setter
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

    public List<ProcurementActivity> getProcurementActivities() {
        return procurementActivities;
    }
    public void setProcurementActivities(List<ProcurementActivity> procurementActivities) {
        this.procurementActivities = procurementActivities;
    }

    public Map<String, Object> getReportData() {
        return reportData;
    }
    public void setReportData(Map<String, Object> reportData) {
        this.reportData = reportData;
    }
    public User getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    //toString
    @Override
    public String toString() {
        return "CreateCustomizedProcurementDashboardOutputDS [" +
                "dashboardId=" + dashboardId +
                ", dashboardTitle=" + dashboardTitle +
                ", reportData=" + reportData +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                "]";
    }
}
