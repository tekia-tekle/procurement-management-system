package com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking;

import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.GenerateSupplierPerformanceReportOutputDS;

import java.util.List;

public interface GenerateSupplierPerformanceReportInputBoundary {
    List<GenerateSupplierPerformanceReportOutputDS> generatePerformanceReport(String supplierId);
}
