package com.bizeff.procurement.persistences.entity.supplierPerformance;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public class SupplierQualitativePerformanceMetricsEntity {
    @Column(name = "Contract_Adherence")
    @Size(min = 1,max = 10)
    private double contractAdherence;     // Contract Adherence
    @Column(name = "technical_Expertise")
    @Size(min = 1,max = 10)
    private double technicalExpertise;     // Technical Expertise

    @Column(name = "innovation")
    @Size(min = 1,max = 10)
    private double innovation;    // Innovation & Improvement
    @Column(name = "quality_Product")
    @Size(min = 1,max = 10)
    private double qualityProducts;       // Product Quality Consistency

    @Column(name = "responsiveness")
    @Size(min = 1,max = 10)
    private double responsiveness; // SupplierModel Responsiveness including reporting if there is update.

    @Column(name = "customenr_Satisfaction")
    @Size(min = 1,max = 10)
    private double customerSatisfaction;   // Customer Satisfaction Score

    public SupplierQualitativePerformanceMetricsEntity(){}
    public double getContractAdherence() {
        return contractAdherence;
    }

    public void setContractAdherence(double contractAdherence) {
        this.contractAdherence = contractAdherence;
    }

    public double getTechnicalExpertise() {
        return technicalExpertise;
    }

    public void setTechnicalExpertise(double technicalExpertise) {
        this.technicalExpertise = technicalExpertise;
    }

    public double getInnovation() {
        return innovation;
    }

    public void setInnovation(double innovation) {
        this.innovation = innovation;
    }

    public double getQualityProducts() {
        return qualityProducts;
    }

    public void setQualityProducts(double qualityProducts) {
        this.qualityProducts = qualityProducts;
    }

    public double getResponsiveness() {
        return responsiveness;
    }

    public void setResponsiveness(double responsiveness) {
        this.responsiveness = responsiveness;
    }

    public double getCustomerSatisfaction() {
        return customerSatisfaction;
    }

    public void setCustomerSatisfaction(double customerSatisfaction) {
        this.customerSatisfaction = customerSatisfaction;
    }
}
