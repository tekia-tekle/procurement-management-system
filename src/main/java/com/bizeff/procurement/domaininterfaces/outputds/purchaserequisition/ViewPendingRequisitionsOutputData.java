package com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition;

import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.enums.RequisitionStatus;

import java.time.LocalDate;

public class ViewPendingRequisitionsOutputData {
    private String requisitionId;
    private String requisitionNumber;
    private RequisitionStatus requisitionStatus;
    private LocalDate requisitionDate;
    private PriorityLevel priorityLevel;
    private LocalDate expectedDeliveryDate;
    public ViewPendingRequisitionsOutputData(){}

    public ViewPendingRequisitionsOutputData(String requisitionId,
                                             String requisitionNumber,
                                             RequisitionStatus requisitionStatus,
                                             LocalDate requisitionDate,
                                             PriorityLevel priorityLevel,
                                             LocalDate expectedDeliveryDate)
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

    public RequisitionStatus getRequisitionStatus() {
        return requisitionStatus;
    }

    public void setRequisitionStatus(RequisitionStatus requisitionStatus) {
        this.requisitionStatus = requisitionStatus;
    }

    public LocalDate getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(LocalDate requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public PriorityLevel getPriorityLevel(){
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public LocalDate getExpectedDeliveryDate(){
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    @Override
    public String toString() {
        return "ViewPendingRequisitionsOutputData{" +
                "requisitionId='" + requisitionId + '\'' +
                ", requisitionNumber='" + requisitionNumber + '\'' +
                ", requisitionStatus=" + requisitionStatus +
                ", requisitionDate=" + requisitionDate +
                ", priorityLevel=" + priorityLevel +
                ", expectedDeliveryDate=" + expectedDeliveryDate +
                '}';
    }
}