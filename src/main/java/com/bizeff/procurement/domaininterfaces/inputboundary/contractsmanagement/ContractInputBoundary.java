package com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement;

import com.bizeff.procurement.domaininterfaces.inputds.contractsmanagement.CreateContractInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.CreatePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.ContractOutputDS;

import java.util.List;

public interface ContractInputBoundary {
    ContractOutputDS createContracts(CreateContractInputDS inputData);
    List<ContractOutputDS> notifyContracts(int daysBeforeExpirationThreshold);
    ContractOutputDS viewContractDetailWhenCreatingPurchaseOrder(String contractId, CreatePurchaseOrderInputDS inputDS);

}
