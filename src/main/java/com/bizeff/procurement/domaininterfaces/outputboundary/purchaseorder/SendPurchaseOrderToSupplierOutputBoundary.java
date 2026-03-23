package com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder;

import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.SendPurchaseOrderToSupplierOutPutDS;

import java.util.List;

public interface SendPurchaseOrderToSupplierOutputBoundary {
    void presentElectronicallyTransferredPurchaseOrders(List<SendPurchaseOrderToSupplierOutPutDS> electronicallyTransferredOrders);
}
