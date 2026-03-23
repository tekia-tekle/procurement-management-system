package com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.ViewSupplierPerformanceReportOutputDS;

import java.util.List;

public interface ViewSupplierPerformanceReportOutputBoundary {
    void presentSuppliersBasedOnPerformance(List<ViewSupplierPerformanceReportOutputDS> viewSupplierPerformanceReportOutputDS);
}
