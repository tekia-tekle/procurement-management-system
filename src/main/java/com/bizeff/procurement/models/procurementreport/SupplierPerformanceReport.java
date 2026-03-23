package com.bizeff.procurement.models.procurementreport;

import com.bizeff.procurement.models.enums.SupplierPerformanceRate;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;

import java.util.List;
import java.util.Map;

public class SupplierPerformanceReport {
    private Long id;
    private Map<String, SupplierPerformance> latestPerformancePerSupplier;

    private Map<String, Double> supplierWithAveragePerformanceScore;
    private Map<SupplierPerformanceRate, Integer> suppliersByCurrentPerformanceRate;
    private List<SupplierPerformance> supplierPerformancesPerSupplier;
    public SupplierPerformanceReport() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, SupplierPerformance> getLatestPerformancePerSupplier() {
        return latestPerformancePerSupplier;
    }

    public void setLatestPerformancePerSupplier(Map<String, SupplierPerformance> latestPerformancePerSupplier) {
        this.latestPerformancePerSupplier = latestPerformancePerSupplier;
    }


    public Map<String, Double> getSupplierWithAveragePerformanceScore() {
        return supplierWithAveragePerformanceScore;
    }

    public void setSupplierWithAveragePerformanceScore(Map<String, Double> supplierWithAveragePerformanceScore) {
        this.supplierWithAveragePerformanceScore = supplierWithAveragePerformanceScore;
    }

    public Map<SupplierPerformanceRate, Integer> getSuppliersByCurrentPerformanceRate() {
        return suppliersByCurrentPerformanceRate;
    }

    public void setSuppliersByCurrentPerformanceRate(Map<SupplierPerformanceRate, Integer> suppliersByCurrentPerformanceRate) {
        this.suppliersByCurrentPerformanceRate = suppliersByCurrentPerformanceRate;
    }
    public List<SupplierPerformance> getSupplierPerformances() {
        return supplierPerformancesPerSupplier;
    }
    public void setSupplierPerformances(List<SupplierPerformance> supplierPerformancesPerSupplier) {
        this.supplierPerformancesPerSupplier = supplierPerformancesPerSupplier;
    }
}
