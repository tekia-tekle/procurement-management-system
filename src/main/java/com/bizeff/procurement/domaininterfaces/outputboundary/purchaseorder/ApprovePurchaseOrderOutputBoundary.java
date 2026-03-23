package com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder;

import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.ApprovePurchaseOrderOutputDS;

public interface ApprovePurchaseOrderOutputBoundary {
    void presentPurchaseOrderWithStatus(ApprovePurchaseOrderOutputDS trackedPurchaseOrder);
}
