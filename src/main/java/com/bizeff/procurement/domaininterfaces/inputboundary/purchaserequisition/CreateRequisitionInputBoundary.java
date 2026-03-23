package com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.CreateRequisitionInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.CreateRequisitionOutputDS;

public interface CreateRequisitionInputBoundary {
    CreateRequisitionOutputDS createRequisition(CreateRequisitionInputDS inputData);
}
