package com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking;

import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQualitativePerformanceMetrics;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQuantitativePerformanceMetrics;

public class ViewSupplierPerformanceMetricsViewModel {
    private String supplierId;
    private SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics;
    private SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics;
    public ViewSupplierPerformanceMetricsViewModel() {}
    public ViewSupplierPerformanceMetricsViewModel(String supplierId, SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics, SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics) {
        this.supplierId = supplierId;
        this.supplierQuantitativePerformanceMetrics = supplierQuantitativePerformanceMetrics;
        this.supplierQualitativePerformanceMetrics = supplierQualitativePerformanceMetrics;
    }
    public String getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
    public SupplierQuantitativePerformanceMetrics getSupplierQuantitativePerformanceMetrics() {
        return supplierQuantitativePerformanceMetrics;
    }
    public void setSupplierQuantitativePerformanceMetrics(SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics) {
        this.supplierQuantitativePerformanceMetrics = supplierQuantitativePerformanceMetrics;
    }
    public SupplierQualitativePerformanceMetrics getSupplierQualitativePerformanceMetrics() {
        return supplierQualitativePerformanceMetrics;
    }
    public void setSupplierQualitativePerformanceMetrics(SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics) {
        this.supplierQualitativePerformanceMetrics = supplierQualitativePerformanceMetrics;
    }

    @Override
    public String toString() {
        return "ViewSupplierPerformanceMetricsViewModel{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierQuantitativePerformanceMetrics=" + supplierQuantitativePerformanceMetrics +
                ", supplierQualitativePerformanceMetrics=" + supplierQualitativePerformanceMetrics +
                '}';
    }
}
