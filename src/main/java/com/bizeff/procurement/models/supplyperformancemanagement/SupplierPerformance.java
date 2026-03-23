package com.bizeff.procurement.models.supplyperformancemanagement;


import com.bizeff.procurement.models.enums.SupplierPerformanceRate;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

public class SupplierPerformance {
    private static final AtomicLong idGenerator = new AtomicLong(1L);

    // Inputs
    private Long id;
    private Supplier supplierModel;
    private SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics;
    private SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics;

    // Evaluation Results
    private SupplierPerformanceRate supplierPerformanceRate;
    private String evaluatorComments;
    private LocalDate evaluationDate;
    public SupplierPerformance(){
        this.id = idGenerator.getAndIncrement();
    }

    // Constructor
    public SupplierPerformance(Supplier supplierModel,
                               SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics,
                               SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics,
                               LocalDate reviewedAt)
    {
        this.id = idGenerator.getAndIncrement();
        this.supplierModel = supplierModel;
        this.supplierQuantitativePerformanceMetrics = supplierQuantitativePerformanceMetrics;
        this.supplierQualitativePerformanceMetrics = supplierQualitativePerformanceMetrics;
        this.supplierPerformanceRate = SupplierPerformanceRate.EXCELLENT;
        this.evaluationDate = reviewedAt;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplierModel;
    }

    public void setSupplier(Supplier supplierModel) {
        this.supplierModel = supplierModel;
    }

    public SupplierQuantitativePerformanceMetrics getQuantitativePerformanceMetrics() {
        calculateSupplierPerformanceRate();
        return supplierQuantitativePerformanceMetrics;
    }

    public void setQuantitativePerformanceMetrics(SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics) {
        this.supplierQuantitativePerformanceMetrics = supplierQuantitativePerformanceMetrics;
    }

    public SupplierQualitativePerformanceMetrics getQualitativePerformanceMetrics() {
        return supplierQualitativePerformanceMetrics;
    }

    public void setQualitativePerformanceMetrics(SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics) {
        this.supplierQualitativePerformanceMetrics = supplierQualitativePerformanceMetrics;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }
    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public SupplierPerformanceRate getSupplierPerformanceRate() {
        return supplierPerformanceRate;
    }

    public void setSupplierPerformanceRate(SupplierPerformanceRate supplierPerformanceRate) {
        this.supplierPerformanceRate = supplierPerformanceRate;
    }

    public String getEvaluatorComments() {
        return evaluatorComments;
    }

    public void setEvaluatorComments(String evaluatorComments) {
        this.evaluatorComments = evaluatorComments;
    }

    public void calculateSupplierPerformanceRate() {
        if (supplierQuantitativePerformanceMetrics == null || supplierQualitativePerformanceMetrics == null) {
            throw new IllegalArgumentException("Quantitative and Qualitative performance metrics must be provided to calculate performance rate.");
        }
        double quantitativeScore = 0.0;
        double qualitativeScore = 0.0;

        quantitativeScore +=  (supplierQuantitativePerformanceMetrics.getDeliveryRate() * 0.3) + (supplierQuantitativePerformanceMetrics.getAccuracyRate() * 0.2) + (supplierQuantitativePerformanceMetrics.getComplianceRate() * 0.2) +
                (supplierQuantitativePerformanceMetrics.getCostEfficiency() * 0.2) + (supplierQuantitativePerformanceMetrics.getCorrectionRate() * 0.1) - (supplierQuantitativePerformanceMetrics.getDefectsRate() * 0.2) - (supplierQuantitativePerformanceMetrics.getCanceledOrders() * 0.1);

        qualitativeScore += (supplierQualitativePerformanceMetrics.getContractAdherence() * 0.2) + (supplierQualitativePerformanceMetrics.getTechnicalExpertise() * 0.2) +
                (supplierQualitativePerformanceMetrics.getInnovation() * 0.2) + (supplierQualitativePerformanceMetrics.getQualityProducts() * 0.2) + (supplierQualitativePerformanceMetrics.getResponsiveness() * 0.1) + (supplierQualitativePerformanceMetrics.getCustomerSatisfaction() * 0.1);

        double totalScore = quantitativeScore + qualitativeScore;

        if (totalScore >= 90) {
            this.supplierPerformanceRate = SupplierPerformanceRate.EXCELLENT;
        }
        else if (totalScore >= 80) {
            this.supplierPerformanceRate = SupplierPerformanceRate.VERY_GOOD;
        }
        else if (totalScore >= 70) {
            this.supplierPerformanceRate = SupplierPerformanceRate.GOOD;
        } else if (totalScore >= 60) {
            this.supplierPerformanceRate = SupplierPerformanceRate.AVERAGE;
        }
        else if (totalScore >= 50) {
            this.supplierPerformanceRate = SupplierPerformanceRate.BELOW_AVERAGE;
        }
        else {
            this.supplierPerformanceRate = SupplierPerformanceRate.POOR;
        }
    }

    @Override
    public String toString() {
        return "SupplierPerformance{" +
                "supplierModel=" + supplierModel +
                ", supplierQuantitativePerformanceMetrics=" + supplierQuantitativePerformanceMetrics +
                ", supplierQualitativePerformanceMetrics=" + supplierQualitativePerformanceMetrics +
                ", supplierPerformanceRate=" + supplierPerformanceRate +
                ", evaluatorComments='" + evaluatorComments + '\'' +
                ", evaluationDate=" + evaluationDate +
                '}';
    }
}