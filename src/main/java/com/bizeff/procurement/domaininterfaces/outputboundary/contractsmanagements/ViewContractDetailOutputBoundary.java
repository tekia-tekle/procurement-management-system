package com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements;

import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.ViewContractDetailOutputDS;

public interface ViewContractDetailOutputBoundary {
    void presentContractDetails(ViewContractDetailOutputDS detailsViewerOutputDs);
}
