package com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements;

import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.NotifyExpiringContractOutputDS;

import java.util.List;

public interface NotifyExpiringContractOutputBoundary {
    void notifyContracts(List<NotifyExpiringContractOutputDS> expiringContracts);
}
