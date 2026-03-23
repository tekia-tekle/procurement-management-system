package com.bizeff.procurement.webapi.viewmodel.purchaserequisition;

public class TrackPurchaseRequisitionViewModel {
    private String requisitionId;
    private String requisitionStatus;
    private String trackedBy;
    private String trackedDate;
    private String feedBack; // if approved <- approved, if rejected / set to pending give the reason.

    public TrackPurchaseRequisitionViewModel(){}
    public TrackPurchaseRequisitionViewModel(String requisitionId,
                                             String requisitionStatus,
                                             String trackedBy,
                                             String trackedDate,
                                             String feedBack)
    {
        this.requisitionId = requisitionId;
        this.requisitionStatus = requisitionStatus;
        this.trackedBy = trackedBy;
        this.trackedDate = trackedDate;
        this.feedBack = feedBack;
    }

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public String getRequisitionStatus() {
        return requisitionStatus;
    }

    public void setRequisitionStatus(String requisitionStatus) {
        this.requisitionStatus = requisitionStatus;
    }

    public String getTrackedBy() {
        return trackedBy;
    }

    public void setTrackedBy(String trackedBy) {
        this.trackedBy = trackedBy;
    }

    public String getTrackedDate() {
        return trackedDate;
    }

    public void setTrackedDate(String trackedDate) {
        this.trackedDate = trackedDate;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    @Override
    public String toString() {
        return "TrackPurchaseRequisitionViewModel{" +
                "requisitionId='" + requisitionId + '\'' +
                ", requisitionStatus='" + requisitionStatus + '\'' +
                ", trackedBy='" + trackedBy + '\'' +
                ", trackedDate='" + trackedDate + '\'' +
                ", feedBack='" + feedBack + '\'' +
                '}';
    }
}
