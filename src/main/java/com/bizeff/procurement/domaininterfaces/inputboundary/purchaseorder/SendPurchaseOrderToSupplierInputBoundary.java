package com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder;

import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.SendPurchaseOrderToSupplierInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.SendPurchaseOrderToSupplierOutPutDS;

import java.util.List;

public interface SendPurchaseOrderToSupplierInputBoundary {
    List<SendPurchaseOrderToSupplierOutPutDS> sendPurchaseOrderToSupplier(SendPurchaseOrderToSupplierInputDS input);
}
