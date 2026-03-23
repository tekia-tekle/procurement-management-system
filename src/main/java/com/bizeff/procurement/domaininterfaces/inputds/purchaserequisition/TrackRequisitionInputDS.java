package com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition;

import java.time.LocalDate;

public class TrackRequisitionInputDS {
    private RequisitionContactDetailsInputDS manager;
    private String requisitionId;
    private LocalDate trackedDate;
    public TrackRequisitionInputDS(){}
    public TrackRequisitionInputDS(RequisitionContactDetailsInputDS manager, String requisitionId){
        this.manager = manager;
        this.requisitionId = requisitionId;
        this.trackedDate = LocalDate.now(); // Default to current date

    }
    public RequisitionContactDetailsInputDS getManager() {
        return manager;
    }

    public void setManager(RequisitionContactDetailsInputDS manager) {
        this.manager = manager;
    }

    public String getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public LocalDate getTrackedDate() {
        return trackedDate;
    }

    public void setTrackedDate(LocalDate trackedDate) {
        this.trackedDate = trackedDate;
    }
}
