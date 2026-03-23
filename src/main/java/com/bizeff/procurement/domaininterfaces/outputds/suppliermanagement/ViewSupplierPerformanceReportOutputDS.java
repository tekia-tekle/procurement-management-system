package com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement;

import com.bizeff.procurement.models.enums.SupplierPerformanceRate;

import java.time.LocalDate;

public class ViewSupplierPerformanceReportOutputDS {
    private String supplierId;
    private String supplierName;
    private String supplierCategory;
    private String taxIdentificationNumber;
    private LocalDate registrationDate;
    private double quantitativePerformanceScore;
    private double qualitativePerformanceScore;
    private double totalSupplierPerformanceScore;
    private SupplierPerformanceRate performanceRate;
    private LocalDate evaluationDate;

    public ViewSupplierPerformanceReportOutputDS(){}
    public ViewSupplierPerformanceReportOutputDS(
            String supplierId,
            String supplierName,
            String supplierCategory,
            String taxIdentificationNumber,
            LocalDate registrationDate,
            double quantitativePerformanceScore,
            double qualitativePerformanceScore,
            double totalSupplierPerformanceScore,
            SupplierPerformanceRate performanceRate,
            LocalDate evaluationDate)
    {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCategory = supplierCategory;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.registrationDate = registrationDate;
        this.quantitativePerformanceScore = quantitativePerformanceScore;
        this.qualitativePerformanceScore = qualitativePerformanceScore;
        this.totalSupplierPerformanceScore = totalSupplierPerformanceScore;
        this.performanceRate = performanceRate;
        this.evaluationDate = evaluationDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCategory() {
        return supplierCategory;
    }

    public void setSupplierCategory(String supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
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

    public double getTotalSupplierPerformanceScore() {
        return totalSupplierPerformanceScore;
    }

    public void setTotalSupplierPerformanceScore(double totalSupplierPerformanceScore) {
        this.totalSupplierPerformanceScore = totalSupplierPerformanceScore;
    }

    public SupplierPerformanceRate getPerformanceRate() {
        return performanceRate;
    }

    public void setPerformanceRate(SupplierPerformanceRate performanceRate) {
        this.performanceRate = performanceRate;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
}