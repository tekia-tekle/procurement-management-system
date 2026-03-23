package com.bizeff.procurement.persistences.entity.supplierPerformance;

import com.bizeff.procurement.models.enums.SupplierPerformanceRate;
import com.bizeff.procurement.persistences.entity.suppliermanagement.SupplierEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "supplier_Performance")
public class SupplierPerformanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id",nullable = false)
    private SupplierEntity supplier;

    @Embedded
    private SupplierQuantitativePerformanceMetricsEntity supplierQuantitativePerformaneMetricsEntity;
    @Embedded
    private SupplierQualitativePerformanceMetricsEntity supplierQualitativePerformanceMetricsEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "performance_rate",nullable = false)
    private SupplierPerformanceRate performanceRate;

    @Column(name = "evaluation_Date",nullable = false,updatable = false)
    private LocalDate evaluationDate;

    @Column(name = "comments")
    private String evaluatorComments;

    public SupplierPerformanceEntity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    public SupplierQuantitativePerformanceMetricsEntity getSupplierQuantitativePerformaneMetricsEntity() {
        return supplierQuantitativePerformaneMetricsEntity;
    }

    public void setSupplierQuantitativePerformaneMetricsEntity(SupplierQuantitativePerformanceMetricsEntity supplierQuantitativePerformaneMetricsEntity) {
        this.supplierQuantitativePerformaneMetricsEntity = supplierQuantitativePerformaneMetricsEntity;
    }

    public SupplierQualitativePerformanceMetricsEntity getSupplierQualitativePerformanceMetricsEntity() {
        return supplierQualitativePerformanceMetricsEntity;
    }

    public void setSupplierQualitativePerformanceMetricsEntity(SupplierQualitativePerformanceMetricsEntity supplierQualitativePerformanceMetricsEntity) {
        this.supplierQualitativePerformanceMetricsEntity = supplierQualitativePerformanceMetricsEntity;
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

    public String getEvaluatorComments() {
        return evaluatorComments;
    }

    public void setEvaluatorComments(String evaluatorComments) {
        this.evaluatorComments = evaluatorComments;
    }
}
