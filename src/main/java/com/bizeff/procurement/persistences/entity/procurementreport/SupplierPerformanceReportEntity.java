package com.bizeff.procurement.persistences.entity.procurementreport;

import com.bizeff.procurement.models.enums.SupplierPerformanceRate;
import com.bizeff.procurement.persistences.entity.supplierPerformance.SupplierPerformanceEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Entity
@Table(name = "supplier_performance_reports")
public class SupplierPerformanceReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "supplier_id")
    private Map<String, SupplierPerformanceEntity> latestSupplierPerformance; // Using String for performance data for simplicity
    @ElementCollection
    @CollectionTable(name = "average_ratings_per_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "average_rating")
    private Map<String, Double> averageRatingsPerSupplier;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierPerformanceEntity> performancesPerSupplier = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "performance_by_current_rate", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "performance_rate")
    @Column(name = "count")
    private Map<SupplierPerformanceRate, Integer> performanceByCurrentRate = new HashMap<>();
    @OneToOne(mappedBy = "supplierPerformanceReport",cascade = CascadeType.ALL)
    private ProcurementReportEntity procurementReportEntity;
    public SupplierPerformanceReportEntity() {}
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Map<String, SupplierPerformanceEntity> getLatestSupplierPerformance() {
        return latestSupplierPerformance;
    }
    public void setLatestSupplierPerformance(Map<String, SupplierPerformanceEntity> latestSupplierPerformance) {
        this.latestSupplierPerformance = latestSupplierPerformance;
    }
    public Map<String, Double> getAverageRatingsPerSupplier() {
        return averageRatingsPerSupplier;
    }
    public void setAverageRatingsPerSupplier(Map<String, Double> averageRatingsPerSupplier) {
        this.averageRatingsPerSupplier = averageRatingsPerSupplier;
    }
    public List<SupplierPerformanceEntity> getPerformancesPerSupplier() {
        return performancesPerSupplier;
    }
    public void setPerformancesPerSupplier(List<SupplierPerformanceEntity> performancesPerSupplier) {
        this.performancesPerSupplier = performancesPerSupplier;
    }
    public Map<SupplierPerformanceRate, Integer> getPerformanceByCurrentRate() {
        return performanceByCurrentRate;
    }
    public void setPerformanceByCurrentRate(Map<SupplierPerformanceRate, Integer> performanceByCurrentRate) {
        this.performanceByCurrentRate = performanceByCurrentRate;
    }
    public ProcurementReportEntity getProcurementReportEntity() {
        return procurementReportEntity;
    }
    public void setProcurementReportEntity(ProcurementReportEntity procurementReportEntity) {
        this.procurementReportEntity = procurementReportEntity;
    }
    //toString
    @Override
    public String toString() {
        return "SupplierPerformanceReportEntity{" +
                "id=" + id +
                ", latestSupplierPerformance=" + latestSupplierPerformance +
                ", averageRatingsPerSupplier=" + averageRatingsPerSupplier +
                ", performancesPerSupplier=" + performancesPerSupplier +
                ", performanceByCurrentRate=" + performanceByCurrentRate +
                '}';
    }

}
