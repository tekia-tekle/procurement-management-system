package com.bizeff.procurement.persistences.entity.supplierPerformance;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public class SupplierQuantitativePerformanceMetricsEntity {
    @Column(name = "delivery_rate")
    @Size(min = 1, max = 100)
    private double deliveryRate;     // On-Time Delivery Rate (Completed Orders)

    @Column(name = "defects_rate")
    @Size(min = 1, max = 100)
    private double defectsRate;      // Defect Rate
    @Column(name = "accuracy_rate")
    @Size(min = 1, max = 100)
    private double accuracyRate;     // Order Accuracy

    @Column(name = "Compliance_rate")
    @Size(min = 1, max = 100)
    private double complianceRate;   // Regulatory Compliance

    @Column(name = "cost_efficiency")
    @Size(min = 1, max = 100)
    private double costEfficiency;         // Cost Efficiency Score

    @Column(name = "correction")
    @Size(min = 1, max = 100)
    private double correction;   // Corrective Action Rate

    @Column(name = "cancledOrders")
    @Size(min = 1, max = 100)
    private double canceledOrders;     // Canceled Orders

    public SupplierQuantitativePerformanceMetricsEntity(){}

    /** Ensures percentage values are between 0 - 100 */

    // Getters
    public double getDeliveryRate() { return deliveryRate; }
    public double getDefectsRate() { return defectsRate; }
    public double getAccuracyRate() { return accuracyRate; }
    public double getComplianceRate() { return complianceRate; }
    public double getCostEfficiency() { return costEfficiency; }
    public double getCorrectionRate() { return correction; }
    public double getCanceledOrders() { return canceledOrders; }

    public void setDeliveryRate(double deliveryRate) {
        this.deliveryRate = deliveryRate;
    }

    public void setDefectsRate(double defectsRate) {
        this.defectsRate = defectsRate;
    }

    public void setAccuracyRate(double accuracyRate) {
        this.accuracyRate = accuracyRate;
    }

    public void setComplianceRate(double complianceRate) {
        this.complianceRate = complianceRate;
    }

    public void setCostEfficiency(double costEfficiency) {
        this.costEfficiency = costEfficiency;
    }

    public void setCorrection(double correction) {
        this.correction = correction;
    }

    public void setCanceledOrders(double canceledOrders) {
        this.canceledOrders = canceledOrders;
    }

}
