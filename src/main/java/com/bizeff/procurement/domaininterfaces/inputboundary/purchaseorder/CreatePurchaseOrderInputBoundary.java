package com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder;


import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.CreatePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.CreatePurchaseOrderOutputDS;

public interface CreatePurchaseOrderInputBoundary {
    CreatePurchaseOrderOutputDS createOrder(CreatePurchaseOrderInputDS input);
}
