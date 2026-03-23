package com.bizeff.procurement.models.supplyperformancemanagement;

public class SupplierQuantitativePerformanceMetrics {
    private double deliveryRate;     // On-Time Delivery Rate (Completed Orders)
    private double defectsRate;      // Defect Rate
    private double accuracyRate;     // Order Accuracy
    private double complianceRate;   // Regulatory Compliance
    private double costEfficiency;         // Cost Efficiency Score
    private double correction;   // Corrective Action Rate
    private double canceledOrders;     // Canceled Orders

    public SupplierQuantitativePerformanceMetrics(){}
    public SupplierQuantitativePerformanceMetrics( double deliveryRate, double defectsRate, double accuracyRate,
                                                  double complianceRate, double costEfficiency, double correction, double canceledOrders) {
        this.deliveryRate = validatePercentage(deliveryRate);     // Fix field name
        this.defectsRate = validatePercentage(defectsRate);
        this.accuracyRate = validatePercentage(accuracyRate);
        this.complianceRate = validatePercentage(complianceRate);
        this.costEfficiency = validatePercentage(costEfficiency);
        this.correction = validatePercentage(correction);
        this.canceledOrders = validatePercentage(canceledOrders);
    }

    /** Ensures percentage values are between 0 - 100 */
    private double validatePercentage(double value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100.");
        }
        return Math.round(value * 100.0) / 100.0; // Rounds to 2 decimal places
    }

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

    @Override
    public String toString() {
        return "SupplierQuantitativePerformanceMetrics{" +
                "deliveryRate=" + deliveryRate +
                ", defectsRate=" + defectsRate +
                ", accuracyRate=" + accuracyRate +
                ", complianceRate=" + complianceRate +
                ", costEfficiency=" + costEfficiency +
                ", correction=" + correction +
                ", canceledOrders=" + canceledOrders +
                '}';
    }
}
