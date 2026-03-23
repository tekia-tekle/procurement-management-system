package com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements;

import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.CreateContractOutputDS;

public interface CreateContractOutputBoundary {
    void presentContracts(CreateContractOutputDS managerOutputDs);
}
