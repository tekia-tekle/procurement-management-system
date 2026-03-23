package com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement;

import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.SupplierContactDetail;
import com.bizeff.procurement.models.supplymanagement.SupplierPaymentMethod;

import java.time.LocalDate;
import java.util.List;

public class AddSupplierOutputDS {
    private String supplierId;
    private String supplierName;
    private String supplierCategory;
    private String taxIdentificationNumber;
    private String registrationNumber;
    private SupplierContactDetail contactDetail;
    private List<SupplierPaymentMethod> paymentMethods;
    private List<Inventory> existedItems;
    private boolean isActive;
    private LocalDate registrationDate;
    private LocalDate lastUpdated;

    public AddSupplierOutputDS(String supplierId,
                               String supplierName,
                               String supplierCategory,
                               String taxIdentificationNumber,
                               String registrationNumber,
                               SupplierContactDetail contactDetail,
                               List<SupplierPaymentMethod> paymentMethods,
                               List<Inventory> existedItems,
                               boolean isActive,
                               LocalDate registrationDate,
                               LocalDate lastUpdated)
    {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCategory = supplierCategory;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.registrationNumber = registrationNumber;
        this.contactDetail = contactDetail;
        this.paymentMethods = paymentMethods;
        this.existedItems = existedItems;
        this.isActive = isActive;
        this.registrationDate = registrationDate;
        this.lastUpdated = lastUpdated;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierCategory() {
        return supplierCategory;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSupplierCategory(String supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public SupplierContactDetail getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(SupplierContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }

    public List<SupplierPaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<SupplierPaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<Inventory> getExistedItems() {
        return existedItems;
    }

    public void setExistedItems(List<Inventory> existedItems) {
        this.existedItems = existedItems;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "AddSupplierOutputDS{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierCategory='" + supplierCategory + '\'' +
                ", taxIdentificationNumber='" + taxIdentificationNumber + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", contactDetail=" + contactDetail +
                ", paymentMethods=" + paymentMethods +
                ", existedItems=" + existedItems +
                ", isActive=" + isActive +
                ", registrationDate=" + registrationDate +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
