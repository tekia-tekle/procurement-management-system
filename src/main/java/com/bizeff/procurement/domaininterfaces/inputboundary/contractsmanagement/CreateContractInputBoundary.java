package com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement;
import com.bizeff.procurement.domaininterfaces.inputds.contractsmanagement.CreateContractInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.CreateContractOutputDS;

public interface CreateContractInputBoundary {
    CreateContractOutputDS createContracts(CreateContractInputDS inputData);
}
