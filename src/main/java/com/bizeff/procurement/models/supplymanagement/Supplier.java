package com.bizeff.procurement.models.supplymanagement;

import com.bizeff.procurement.models.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private Long id;
    private String supplierId;
    private String supplierName;
    private String supplierCategory; // Manufacturer, Distributor, Vendor, etc.
    private String taxIdentificationNumber;
    private String registrationNumber;
    private SupplierContactDetail supplierContactDetail;
    private List<SupplierPaymentMethod> supplierPaymentMethods;
    private List<Inventory> existedItems = new ArrayList<>();
    private boolean isActive;
    private LocalDate registrationDate;
    private LocalDate lastUpdated;

    public Supplier(){}
    public Supplier(
            String supplierName,
            String supplierCategory,
            String taxIdentificationNumber,
            String registrationNumber,
            SupplierContactDetail supplierContactDetail,
            List<SupplierPaymentMethod> supplierPaymentMethods,
            List<Inventory> existedItems,
            LocalDate registrationDate
    ) {

        this.supplierId = IdGenerator.generateId("SUP");
        this.supplierName = supplierName;
        this.supplierCategory = supplierCategory;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.registrationNumber = registrationNumber;
        this.supplierContactDetail = supplierContactDetail;
        this.supplierPaymentMethods = supplierPaymentMethods;
        this.existedItems = existedItems;
        this.isActive = true;
        this.registrationDate = registrationDate;
        this.lastUpdated = registrationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Getters & Setters. */

    public String  getSupplierId() { return supplierId; } // Immutable

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public void addInventory(Inventory inventory){
        if (inventory != null && !existedItems.contains(inventory)){
        this.existedItems.add(inventory);
        }
    }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getSupplierCategory() { return supplierCategory; }
    public void setSupplierCategory(String supplierCategory) { this.supplierCategory = supplierCategory; }

    public String getTaxIdentificationNumber() { return taxIdentificationNumber; }
    public void setTaxIdentificationNumber(String taxIdentificationNumber) { this.taxIdentificationNumber = taxIdentificationNumber; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public SupplierContactDetail getSupplierContactDetail() { return supplierContactDetail; }
    public void setSupplierContactDetail(SupplierContactDetail supplierContactDetail) { this.supplierContactDetail = supplierContactDetail; }

    public List<SupplierPaymentMethod> getSupplierPaymentMethods() { return supplierPaymentMethods; }
    public void setSupplierPaymentMethods(List<SupplierPaymentMethod> supplierPaymentMethods) { this.supplierPaymentMethods = supplierPaymentMethods; }

    public List<Inventory> getExistedItems() {
        return existedItems; // Prevent modification
    }
    public void setExistedItems(List<Inventory> existedItems) { this.existedItems = existedItems; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }

    public LocalDate getRegistrationDate() { return registrationDate; } // Immutable

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDate lastUpdated) { this.lastUpdated = lastUpdated; }

    public void addItem(Inventory inventory) {
        if (inventory != null && !existedItems.contains(inventory)) {
            existedItems.add(inventory);
        }
    }
    public void addPaymentMethod(SupplierPaymentMethod paymentMethod) {
        if (paymentMethod != null && !supplierPaymentMethods.contains(paymentMethod)) {
            supplierPaymentMethods.add(paymentMethod);
        }
    }
    @Override
    public String toString() {
        return "SupplierModel{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierCategory='" + supplierCategory + '\'' +
                ", taxIdentificationNumber='" + taxIdentificationNumber + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", supplierContactDetail=" + supplierContactDetail +
                ", supplierPaymentMethod=" + supplierPaymentMethods +
                ", existedItems=" + existedItems +
                ", isActive=" + isActive +
                ", registrationDate=" + registrationDate +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
