package com.bizeff.procurement.webapi.viewmodel.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.ViewSupplierPerformanceReportOutputDS;

import java.util.List;

public class ViewSupplierPerformanceViewModel {
    private String supplierId;
    private String supplierName;
    private String supplierCategory;
    private String taxIdentificationNumber;
    private String registrationDate;
    private String quantitativePerformanceScore;
    private String qualitativePerformanceScore;
    private String totalSupplierPerformanceScore;
    private String performanceRate;
    private String evaluationDate;
    public ViewSupplierPerformanceViewModel(){

    }

    public ViewSupplierPerformanceViewModel(String supplierId,
                                            String supplierName,
                                            String supplierCategory,
                                            String taxIdentificationNumber,
                                            String registrationDate,
                                            String quantitativePerformanceScore,
                                            String qualitativePerformanceScore,
                                            String totalSupplierPerformanceScore,
                                            String performanceRate,
                                            String evaluationDate) {
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

    private List<ViewSupplierPerformanceReportOutputDS> vendorPerformanceOutput;

    public ViewSupplierPerformanceViewModel(List<ViewSupplierPerformanceReportOutputDS> vendorPerformanceOutput) {
        this.vendorPerformanceOutput = vendorPerformanceOutput;
    }

    public List<ViewSupplierPerformanceReportOutputDS> getVendorPerformanceOutput() {
        return vendorPerformanceOutput;
    }

    public void setVendorPerformanceOutput(List<ViewSupplierPerformanceReportOutputDS> vendorPerformanceOutput) {
        this.vendorPerformanceOutput = vendorPerformanceOutput;
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

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getQuantitativePerformanceScore() {
        return quantitativePerformanceScore;
    }

    public void setQuantitativePerformanceScore(String quantitativePerformanceScore) {
        this.quantitativePerformanceScore = quantitativePerformanceScore;
    }

    public String getQualitativePerformanceScore() {
        return qualitativePerformanceScore;
    }

    public void setQualitativePerformanceScore(String qualitativePerformanceScore) {
        this.qualitativePerformanceScore = qualitativePerformanceScore;
    }

    public String getTotalSupplierPerformanceScore() {
        return totalSupplierPerformanceScore;
    }

    public void setTotalSupplierPerformanceScore(String totalSupplierPerformanceScore) {
        this.totalSupplierPerformanceScore = totalSupplierPerformanceScore;
    }

    public String getPerformanceRate() {
        return performanceRate;
    }

    public void setPerformanceRate(String performanceRate) {
        this.performanceRate = performanceRate;
    }

    public String getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(String evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    @Override
    public String toString() {
        return "ViewSupplierPerformanceViewModel{" +
                "vendorPerformanceOutput=" + vendorPerformanceOutput +
                '}';
    }
}
