package com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement;

import java.time.LocalDate;

public class ViewSupplierPerformanceReportInputDS {
    private String supplierCategory;
    private LocalDate startDate;
    private LocalDate endDate;

    public ViewSupplierPerformanceReportInputDS(
            String supplierCategory,
            LocalDate startDate,
            LocalDate endDate)
    {
        this.supplierCategory = supplierCategory;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSupplierCategory() {
        return supplierCategory;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
