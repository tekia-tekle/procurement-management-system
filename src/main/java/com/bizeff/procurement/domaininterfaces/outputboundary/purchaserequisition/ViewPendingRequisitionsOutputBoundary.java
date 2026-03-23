package com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.ViewPendingRequisitionsOutputData;

import java.util.List;

public interface ViewPendingRequisitionsOutputBoundary {
    void presentPendingRequisition(List<ViewPendingRequisitionsOutputData> pendingRequisitions);
}
