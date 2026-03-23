package com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking;

public class GeneratedSupplierPerformanceReportViewModel {
    private String supplierId;
    private double quantitativePerformanceScore;
    private double qualitativePerformanceScore;
    private double totalPerformanceScore;

    private String performanceRate;
    private String evaluatedDate;

    // Default constructor for serialization/deserialization
    public GeneratedSupplierPerformanceReportViewModel() {}

    // Constructor to initialize all fields
    public GeneratedSupplierPerformanceReportViewModel(String supplierId,
                                                       double quantitativePerformanceScore,
                                                       double qualitativePerformanceScore,
                                                       double totalPerformanceScore,
                                                       String performanceRate,
                                                       String evaluatedDate) {
        this.supplierId = supplierId;
        this.quantitativePerformanceScore = quantitativePerformanceScore;
        this.qualitativePerformanceScore = qualitativePerformanceScore;
        this.totalPerformanceScore = totalPerformanceScore;
        this.performanceRate = performanceRate;
        this.evaluatedDate = evaluatedDate;
    }

    // Getters and Setters
    public String getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
    public double getQuantitativePerformanceScore() {
        return quantitativePerformanceScore;
    }
    public void setQuantitativePerformanceScore(double quantitativePerformanceScore) {
        this.quantitativePerformanceScore = quantitativePerformanceScore;
    }
    public double getQualitativePerformanceScore() {
        return qualitativePerformanceScore;
    }
    public void setQualitativePerformanceScore(double qualitativePerformanceScore) {
        this.qualitativePerformanceScore = qualitativePerformanceScore;
    }
    public double getTotalPerformanceScore() {
        return totalPerformanceScore;
    }
    public void setTotalPerformanceScore(double totalPerformanceScore) {
        this.totalPerformanceScore = totalPerformanceScore;
    }
    public String getPerformanceRate() {
        return performanceRate;
    }
    public void setPerformanceRate(String performanceRate) {
        this.performanceRate = performanceRate;
    }
    public String getEvaluatedDate() {
        return evaluatedDate;
    }
    public void setEvaluatedDate(String evaluatedDate) {
        this.evaluatedDate = evaluatedDate;
    }

    @Override
    public String toString() {
        return "GeneratedSupplierPerformanceReportViewModel{" +
                "supplierId='" + supplierId + '\'' +
                ", quantitativePerformanceScore=" + quantitativePerformanceScore +
                ", qualitativePerformanceScore=" + qualitativePerformanceScore +
                ", totalPerformanceScore=" + totalPerformanceScore +
                ", performanceRate='" + performanceRate + '\'' +
                ", evaluatedDate='" + evaluatedDate + '\'' +
                '}';
    }
}
