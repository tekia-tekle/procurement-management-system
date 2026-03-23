package com.bizeff.procurement.models.supplyperformancemanagement;

public class SupplierQualitativePerformanceMetrics {
    private double contractAdherence;     // Contract Adherence
    private double technicalExpertise;     // Technical Expertise
    private double innovation;    // Innovation & Improvement
    private double qualityProducts;       // Product Quality Consistency
    private double responsiveness; // SupplierModel Responsiveness including reporting if there is update.
    private double customerSatisfaction;   // Customer Satisfaction Score

    public SupplierQualitativePerformanceMetrics(){}
    public SupplierQualitativePerformanceMetrics(double contractAdherence, double technicalExpertise, double innovation,
                                                 double qualityProducts, double responsiveness, double customerSatisfaction) {
        this.contractAdherence = validateScore(contractAdherence);
        this.technicalExpertise = validateScore(technicalExpertise);
        this.innovation = validateScore(innovation);
        this.qualityProducts = validateScore(qualityProducts);
        this.responsiveness = validateScore(responsiveness);
        this.customerSatisfaction = validateScore(customerSatisfaction);
    }

    /** Ensures score is between 1-10 */
    private double validateScore(double score) {
        if (score < 1 || score > 10) {
            throw new IllegalArgumentException("Score must be between 1 and 10.");
        }
        return score;
    }
    // Getters

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

    @Override
    public String toString() {
        return "SupplierQualitativePerformanceMetrics{" +
                "contractAdherence=" + contractAdherence +
                ", technicalExpertise=" + technicalExpertise +
                ", innovation=" + innovation +
                ", qualityProducts=" + qualityProducts +
                ", responsiveness=" + responsiveness +
                ", customerSatisfaction=" + customerSatisfaction +
                '}';
    }
}
