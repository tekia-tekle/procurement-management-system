package com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking;

import com.bizeff.procurement.models.enums.SupplierPerformanceRate;

import java.time.LocalDate;

public class GenerateSupplierPerformanceReportOutputDS {
    private String supplierId;
    private double quantitativePerformanceScore;
    private double qualitativePerformanceScore;
    private double totalPerformanceScore;

    private SupplierPerformanceRate supplierPerformanceRate;
    private LocalDate evaluatedDate;

    public GenerateSupplierPerformanceReportOutputDS(String supplierId,
                                                     double quantitativePerformanceScore,
                                                     double qualitativePerformanceScore,
                                                     double totalPerformanceScore,
                                                     SupplierPerformanceRate supplierPerformanceRate,
                                                     LocalDate evaluatedDate)
    {
        this.supplierId = supplierId;
        this.quantitativePerformanceScore = quantitativePerformanceScore;
        this.qualitativePerformanceScore = qualitativePerformanceScore;
        this.totalPerformanceScore = totalPerformanceScore;
        this.supplierPerformanceRate = supplierPerformanceRate;
        this.evaluatedDate = evaluatedDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public double getQuantitativePerformanceScore() {
        return quantitativePerformanceScore;
    }

    public double getQualitativePerformanceScore() {
        return qualitativePerformanceScore;
    }

    public double getTotalPerformanceScore() {
        return totalPerformanceScore;
    }

    public SupplierPerformanceRate getSupplierPerformanceRate() {
        return supplierPerformanceRate;
    }

    public LocalDate getEvaluatedDate() {
        return evaluatedDate;
    }
}
