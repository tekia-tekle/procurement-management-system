package com.bizeff.procurement.models.procurementreport;

import com.bizeff.procurement.models.enums.ReconciliationStatus;

import java.math.BigDecimal;
import java.util.Map;

public class PaymentReconciliationReport {
    private Long id;
    private Long totalReconciliations;
    private BigDecimal totalPaidAmount;
    private BigDecimal totalExpectedAmount;
    private BigDecimal totalDiscrepancyAmount;
    private Map<ReconciliationStatus, Integer> reconciliationByStatus;
    private Map<String, Integer> reconciliationsBySupplier;
    private Map<String, BigDecimal> totalDiscrepancyPerSupplier;
    private Map<String, BigDecimal> totalPaidAmountPerSupplier;

    public PaymentReconciliationReport(){}

    public PaymentReconciliationReport(Long totalReconciliations,
                                       BigDecimal totalPaidAmount,
                                       BigDecimal totalExpectedAmount,
                                       BigDecimal totalDiscrepancyAmount,
                                       Map<ReconciliationStatus, Integer> reconciliationByStatus,
                                       Map<String, Integer> reconciliationsBySupplier,
                                       Map<String, BigDecimal> totalPaidAmountPerSupplier,
                                       Map<String, BigDecimal> totalDiscrepancyPerSupplier)
    {
        this.totalReconciliations = totalReconciliations;
        this.totalPaidAmount = totalPaidAmount;
        this.totalExpectedAmount = totalExpectedAmount;
        this.totalDiscrepancyAmount = totalDiscrepancyAmount;
        this.reconciliationByStatus = reconciliationByStatus;
        this.reconciliationsBySupplier = reconciliationsBySupplier;
        this.totalPaidAmountPerSupplier = totalPaidAmountPerSupplier;
        this.totalDiscrepancyPerSupplier = totalDiscrepancyPerSupplier;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTotalReconciliations() {
        return totalReconciliations;
    }
    public void setTotalReconciliations(Long totalReconciliations) {
        this.totalReconciliations = totalReconciliations;
    }
    public BigDecimal getTotalPaidAmount() {
        return totalPaidAmount;
    }
    public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }
    public BigDecimal getTotalExpectedAmount() {
        return totalExpectedAmount;
    }
    public void setTotalExpectedAmount(BigDecimal totalExpectedAmount) {
        this.totalExpectedAmount = totalExpectedAmount;
    }
    public BigDecimal getTotalDiscrepancyAmount() {
        return totalDiscrepancyAmount;
    }
    public void setTotalDiscrepancyAmount(BigDecimal totalDiscrepancyAmount) {
        this.totalDiscrepancyAmount = totalDiscrepancyAmount;
    }
    public Map<ReconciliationStatus, Integer> getReconciliationByStatus() {
        return reconciliationByStatus;
    }
    public void setReconciliationByStatus(Map<ReconciliationStatus, Integer> reconciliationByStatus) {
        this.reconciliationByStatus = reconciliationByStatus;
    }
    public Map<String, Integer> getReconciliationsBySupplier() {
        return reconciliationsBySupplier;
    }
    public void setReconciliationsBySupplier(Map<String, Integer> reconciliationsBySupplier) {
        this.reconciliationsBySupplier = reconciliationsBySupplier;
    }
    public Map<String, BigDecimal> getTotalPaidAmountPerSupplier() {
        return totalPaidAmountPerSupplier;
    }
    public void setTotalPaidAmountPerSupplier(Map<String, BigDecimal> totalPaidAmountPerSupplier) {
        this.totalPaidAmountPerSupplier = totalPaidAmountPerSupplier;
    }
    public Map<String, BigDecimal> getTotalDiscrepancyPerSupplier() {
        return totalDiscrepancyPerSupplier;
    }
    public void setTotalDiscrepancyPerSupplier(Map<String, BigDecimal> totalDiscrepancyPerSupplier) {
        this.totalDiscrepancyPerSupplier = totalDiscrepancyPerSupplier;
    }
}
