package com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder;

import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.CreatePurchaseOrderOutputDS;

public interface CreatePurchaseOrderOutputBoundary {
    void presentCreatedPurchaseOrder(CreatePurchaseOrderOutputDS newPurchaseOrder);
}
