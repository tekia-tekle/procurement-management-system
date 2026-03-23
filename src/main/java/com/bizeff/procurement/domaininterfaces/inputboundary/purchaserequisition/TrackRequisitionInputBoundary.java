package com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.TrackRequisitionInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.TrackRequisitionOutputDS;

public interface TrackRequisitionInputBoundary {
    TrackRequisitionOutputDS trackRequisition(TrackRequisitionInputDS inputData);
}
