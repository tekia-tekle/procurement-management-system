package com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements;

import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.ContractOutputDS;

import java.util.List;

public interface ContractOutputBoundary {
    void presentContracts(ContractOutputDS managerOutputDs);
    void notifyContracts(List<ContractOutputDS> expiringContracts);
    void presentContractDetails(ContractOutputDS detailsViewerOutputDs);

}
