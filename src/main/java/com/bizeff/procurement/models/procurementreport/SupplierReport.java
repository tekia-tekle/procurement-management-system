package com.bizeff.procurement.models.procurementreport;

import java.util.Map;

public class SupplierReport {
    private Long id;
    private Long totalSuppliers;

    private Map<String, Integer> suppliersByCategory;
    private Long activeSuppliers;

    public SupplierReport() {}

    public SupplierReport(Long totalSuppliers,
                          Map<String, Integer>suppliersByCategory,
                          Long activeSuppliers){
        this.totalSuppliers = totalSuppliers;
        this.suppliersByCategory = suppliersByCategory;
        this.activeSuppliers = activeSuppliers;
    }

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

    // toString
    @Override
    public String toString() {
        return "SupplierReport " +
                "[id=" + id +
                ", totalSuppliers=" + totalSuppliers +
                ", suppliersByCategory=" + suppliersByCategory +
                ", activeSuppliers=" + activeSuppliers +
                "]";
    }
}
