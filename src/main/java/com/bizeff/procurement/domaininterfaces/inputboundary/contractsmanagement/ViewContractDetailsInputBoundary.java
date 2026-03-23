package com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement;

import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.CreatePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.ViewContractDetailOutputDS;

public interface ViewContractDetailsInputBoundary {
    ViewContractDetailOutputDS viewContractDetailWhenCreatingPurchaseOrder(String contractId, CreatePurchaseOrderInputDS inputDS);
}
