package com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.CreateRequisitionOutputDS;

public interface CreateRequisitionOutputBoundary {
    void presentRequestedRequisitions(CreateRequisitionOutputDS requestedRequisition);
}
