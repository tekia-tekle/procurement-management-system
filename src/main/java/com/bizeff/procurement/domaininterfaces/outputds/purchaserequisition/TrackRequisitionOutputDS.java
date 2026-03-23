package com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition;

import com.bizeff.procurement.models.enums.RequisitionStatus;

import java.time.LocalDate;

public class TrackRequisitionOutputDS {
    private String managerName;//tracked by.
    private String requisitionId;
    private String requisitionNumber;
    private RequisitionStatus status;
    private LocalDate trackedDate;
    private String comments;
    public TrackRequisitionOutputDS(String requisitionId,
                                    String requisitionNumber,
                                    RequisitionStatus status,
                                    String managerName,
                                    LocalDate trackedDate,
                                    String comments)
    {
        this.requisitionId = requisitionId;
        this.requisitionNumber = requisitionNumber;
        this.status = status;
        this.managerName = managerName;
        this.trackedDate = trackedDate;
        this.comments = comments;
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

    public RequisitionStatus getStatus() {
        return status;
    }

    public void setStatus(RequisitionStatus status) {
        this.status = status;
    }

    public String getManagerName() {
        return managerName;
    }
    public String getComments() {
        return comments;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getTrackedDate() {
        return trackedDate;
    }

    public void setTrackedDate(LocalDate trackedDate) {
        this.trackedDate = trackedDate;
    }

    @Override
    public String toString() {
        return "TrackRequisitionOutputDS{" +
                "managerName='" + managerName + '\'' +
                ", requisitionId='" + requisitionId + '\'' +
                ", requisitionNumber='" + requisitionNumber + '\'' +
                ", status=" + status +
                ", trackedDate=" + trackedDate +
                ", comments='" + comments + '\'' +
                '}';
    }
}
