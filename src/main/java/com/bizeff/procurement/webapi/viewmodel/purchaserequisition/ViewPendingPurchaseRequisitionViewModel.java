package com.bizeff.procurement.webapi.viewmodel.purchaserequisition;

public class ViewPendingPurchaseRequisitionViewModel {
    private String requisitionId;
    private String requisitionNumber;
    private String requisitionStatus;
    private String requisitionDate;
    private String priorityLevel;
    private String expectedDeliveryDate;
    public ViewPendingPurchaseRequisitionViewModel(){};

    public ViewPendingPurchaseRequisitionViewModel(
            String requisitionId,
            String requisitionNumber,
            String requisitionStatus,
            String requisitionDate,
            String priorityLevel,
            String expectedDeliveryDate)
    {
        this.requisitionId = requisitionId;
        this.requisitionNumber = requisitionNumber;
        this.requisitionStatus = requisitionStatus;
        this.requisitionDate = requisitionDate;
        this.priorityLevel = priorityLevel;
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public String getRequisitionNumber() {
        return requisitionNumber;
    }

    public void setRequisitionNumber(String requisitionNumber) {
        this.requisitionNumber = requisitionNumber;
    }

    public String getRequisitionStatus() {
        return requisitionStatus;
    }

    public void setRequisitionStatus(String requisitionStatus) {
        this.requisitionStatus = requisitionStatus;
    }

    public String getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    @Override
    public String toString() {
        return "ViewPendingPurchaseRequisitionViewModel{" +
                "requisitionId='" + requisitionId + '\'' +
                ", requisitionNumber='" + requisitionNumber + '\'' +
                ", requisitionStatus='" + requisitionStatus + '\'' +
                ", requisitionDate='" + requisitionDate + '\'' +
                ", priorityLevel='" + priorityLevel + '\'' +
                ", expectedDeliveryDate='" + expectedDeliveryDate + '\'' +
                '}';
    }
}
