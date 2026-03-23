package com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking;

import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.EvaluateSupplierForContractRenewalOutputDS;

import java.util.List;

public interface EvaluateSupplierForContractRenewalInputBoundary {
    List<EvaluateSupplierForContractRenewalOutputDS> evaluateSupplierforContractRenewal(String supplierCategory);
}
