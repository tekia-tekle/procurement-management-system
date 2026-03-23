package com.bizeff.procurement.persistences.entity.procurementreport;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "supplier_report")
public class SupplierReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long totalSuppliers;
    @ElementCollection
    @CollectionTable(name = "suppliers_by_Category", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "category")
    @Column(name = "count")
    private Map<String,Integer> suppliersByCategory;
    private Long activeSuppliers;
    @OneToOne(mappedBy = "supplierReport",cascade = CascadeType.ALL)
    private ProcurementReportEntity procurementReportEntity;
    public SupplierReportEntity() {}
    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTotalSuppliers() {
        return totalSuppliers;
    }
    public void setTotalSuppliers(Long totalSuppliers) {
        this.totalSuppliers = totalSuppliers;
    }
    public Map<String, Integer> getSuppliersByCategory() {
        return suppliersByCategory;
    }
    public void setSuppliersByCategory(Map<String, Integer> suppliersByCategory) {
        this.suppliersByCategory = suppliersByCategory;
    }
    public Long getActiveSuppliers() {
        return activeSuppliers;
    }
    public void setActiveSuppliers(Long activeSuppliers) {
        this.activeSuppliers = activeSuppliers;
    }

    public ProcurementReportEntity getProcurementReportEntity() {
        return procurementReportEntity;
    }
    public void setProcurementReportEntity(ProcurementReportEntity procurementReportEntity) {
        this.procurementReportEntity = procurementReportEntity;
    }
    @Override
    public String toString() {
        return "SupplierReportEntity{" +
                "id=" + id +
                ", totalSuppliers=" + totalSuppliers +
                ", suppliersByCategory=" + suppliersByCategory +
                ", activeSuppliers=" + activeSuppliers +
                '}';
    }
}
