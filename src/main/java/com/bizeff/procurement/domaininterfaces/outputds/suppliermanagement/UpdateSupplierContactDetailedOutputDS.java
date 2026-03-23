package com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement;

import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.SupplierContactDetail;
import com.bizeff.procurement.models.supplymanagement.SupplierPaymentMethod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UpdateSupplierContactDetailedOutputDS {
    private String supplierId;
    private String supplierName;
    private String supplierCategory;
    private String taxIdentificationNumber;
    private String registrationNumber;
    private LocalDate registrationDate;
    private SupplierContactDetail contactDetail;
    private List<SupplierPaymentMethod> paymentMethods;
    private List<Inventory>existedItems = new ArrayList<>();
    private LocalDate updatedDate;
    private Boolean isUpdated;

    public UpdateSupplierContactDetailedOutputDS(String supplierId,
                                                 String supplierName,
                                                 String supplierCategory,
                                                 String taxIdentificationNumber,
                                                 String registrationNumber,
                                                 LocalDate registrationDate,
                                                 SupplierContactDetail contactDetail,
                                                 List<SupplierPaymentMethod> paymentMethods,
                                                 LocalDate updatedDate)
    {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCategory = supplierCategory;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.registrationNumber = registrationNumber;
        this.registrationDate = registrationDate;
        this.contactDetail = contactDetail;
        this.paymentMethods = paymentMethods;
        this.updatedDate = updatedDate;
        this.isUpdated = false;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCategory() {
        return supplierCategory;
    }

    public void setSupplierCategory(String supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
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

    public void setPaymentMethod(List<SupplierPaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<Inventory> getExistedItems() {
        return existedItems;
    }

    public void setExistedItems(List<Inventory> existedItems) {
        this.existedItems = existedItems;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public void setUpdated(Boolean updated) {
        isUpdated = updated;
    }
}
