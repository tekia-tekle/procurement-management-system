package com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.ViewPendingRequisitionsOutputData;

import java.util.List;

public interface ViewPendingRequisitionsInputBoundary {
    List<ViewPendingRequisitionsOutputData> viewAllPendingRequisition(String departmentId);
}
