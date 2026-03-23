package com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.NotifyExpiringContractOutputDS;

import java.util.List;

public interface NotifyExpiringContractInputBoundary {
    List<NotifyExpiringContractOutputDS> notifyContracts(int daysBeforeExpirationThreshold);
}
