package com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.TrackRequisitionOutputDS;

public interface TrackRequisitionOutputBoundary {
    void presentApprovedRequisitions(TrackRequisitionOutputDS approvedRequisitions);
}
