package com.bizeff.procurement.persistences.entity.procurementreport;

import com.bizeff.procurement.models.enums.ReconciliationStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "payment_reconciliation_reports")
public class PaymentReconciliationReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long totalReconciliations;
    private BigDecimal totalPaidAmount;
    private BigDecimal totalExpectedAmount;
    private BigDecimal totalDiscrepancyAmount;
    @ElementCollection
    @CollectionTable(name = "reconciliations_by_status", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "status")
    @Column(name = "count")
    private Map<ReconciliationStatus, Integer> reconciliationsByStatus; // Using String for status for simplicity

    @ElementCollection
    @CollectionTable(name = "paid_amounts_per_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "total_paid_amount")
    private Map<String, BigDecimal> paidAmountsPerSupplier;

    @ElementCollection
    @CollectionTable(name = "discrepancy_amounts_per_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "total_discrepancy_amount")
    private Map<String, BigDecimal> discrepancyAmountsPerSupplier;

    @ElementCollection
    @CollectionTable(name = "reconciliations_per_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "reconciliation_count")
    private Map<String, Integer> reconciliationsPerSupplier;

    @OneToOne(mappedBy = "paymentReconciliationReport",cascade = CascadeType.ALL)
    private ProcurementReportEntity procurementReportEntity;
    public PaymentReconciliationReportEntity() {}

    // Getters and Setters
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
    public Map<ReconciliationStatus, Integer> getReconciliationsByStatus() {
        return reconciliationsByStatus;
    }
    public void setReconciliationsByStatus(Map<ReconciliationStatus, Integer> reconciliationsByStatus) {
        this.reconciliationsByStatus = reconciliationsByStatus;
    }
    public Map<String, BigDecimal> getPaidAmountsPerSupplier() {
        return paidAmountsPerSupplier;
    }
    public void setPaidAmountsPerSupplier(Map<String, BigDecimal> paidAmountsPerSupplier) {
        this.paidAmountsPerSupplier = paidAmountsPerSupplier;
    }
    public Map<String, BigDecimal> getDiscrepancyAmountsPerSupplier() {
        return discrepancyAmountsPerSupplier;
    }
    public void setDiscrepancyAmountsPerSupplier(Map<String, BigDecimal> discrepancyAmountsPerSupplier) {
        this.discrepancyAmountsPerSupplier = discrepancyAmountsPerSupplier;
    }
    public Map<String, Integer> getReconciliationsPerSupplier() {
        return reconciliationsPerSupplier;
    }
    public void setReconciliationsPerSupplier(Map<String, Integer> reconciliationsPerSupplier) {
        this.reconciliationsPerSupplier = reconciliationsPerSupplier;
    }
    public ProcurementReportEntity getProcurementReportEntity() {
        return procurementReportEntity;
    }
    public void setProcurementReportEntity(ProcurementReportEntity procurementReportEntity) {
        this.procurementReportEntity = procurementReportEntity;
    }
    @Override
    public String toString() {
        return "PaymentReconciliationReportEntity " +
                "[id=" + id +
                ", totalReconciliations=" + totalReconciliations +
                ", totalPaidAmount=" + totalPaidAmount +
                ", totalExpectedAmount=" + totalExpectedAmount +
                ", totalDiscrepancyAmount=" + totalDiscrepancyAmount +
                ", reconciliationsByStatus=" + reconciliationsByStatus +
                ", paidAmountsPerSupplier=" + paidAmountsPerSupplier +
                ", discrepancyAmountsPerSupplier=" + discrepancyAmountsPerSupplier +
                ", reconciliationsPerSupplier=" + reconciliationsPerSupplier +
                "]";
    }
}
