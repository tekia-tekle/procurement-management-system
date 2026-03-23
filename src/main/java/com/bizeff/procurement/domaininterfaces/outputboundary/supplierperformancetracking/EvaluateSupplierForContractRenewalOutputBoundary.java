package com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking;

import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.EvaluateSupplierForContractRenewalOutputDS;

import java.util.List;

public interface EvaluateSupplierForContractRenewalOutputBoundary {
    void presentRenewalContract(List<EvaluateSupplierForContractRenewalOutputDS> outputDS);
}
