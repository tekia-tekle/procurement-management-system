package com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.ApprovePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.ApprovePurchaseOrderOutputDS;

public interface ApprovePurchaseOrderInputBoundary {
    ApprovePurchaseOrderOutputDS trackPurchaseOrderStatus(ApprovePurchaseOrderInputDS input);
}
