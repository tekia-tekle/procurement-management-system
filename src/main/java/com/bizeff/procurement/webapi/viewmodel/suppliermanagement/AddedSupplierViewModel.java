package com.bizeff.procurement.webapi.viewmodel.suppliermanagement;

import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.SupplierContactDetail;
import com.bizeff.procurement.models.supplymanagement.SupplierPaymentMethod;

import java.util.List;

public class AddedSupplierViewModel {
    private String supplierId;
    private String supplierName;
    private String supplierCategory;
    private String taxIdentificationNumber;
    private String registrationNumber;
    private SupplierContactDetail supplierContactDetail;
    private List<SupplierPaymentMethod> supplierPaymentMethods;
    private List<Inventory> existedItems;
    private boolean isActive;
    private String registrationDate;
    private String lastUpdatedDate;
    public AddedSupplierViewModel(){}

    public AddedSupplierViewModel(String supplierId,
                                  String supplierName,
                                  String supplierCategory,
                                  String taxIdentificationNumber,
                                  String registrationNumber,
                                  SupplierContactDetail supplierContactDetail,
                                  List<SupplierPaymentMethod> supplierPaymentMethods,
                                  List<Inventory> existedItems,
                                  boolean isActive,
                                  String registrationDate,
                                  String lastUpdatedDate)
    {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCategory = supplierCategory;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.registrationNumber = registrationNumber;
        this.supplierContactDetail = supplierContactDetail;
        this.supplierPaymentMethods = supplierPaymentMethods;
        this.existedItems = existedItems;
        this.isActive = isActive;
        this.registrationDate = registrationDate;
        this.lastUpdatedDate = lastUpdatedDate;
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

    public SupplierContactDetail getSupplierContactDetail() {
        return supplierContactDetail;
    }

    public void setSupplierContactDetail(SupplierContactDetail supplierContactDetail) {
        this.supplierContactDetail = supplierContactDetail;
    }

    public List<SupplierPaymentMethod> getSupplierPaymentMethods() {
        return supplierPaymentMethods;
    }

    public void setSupplierPaymentMethods(List<SupplierPaymentMethod> supplierPaymentMethods) {
        this.supplierPaymentMethods = supplierPaymentMethods;
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

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public String toString() {
        return "AddedSupplierViewModel{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierCategory='" + supplierCategory + '\'' +
                ", taxIdentificationNumber='" + taxIdentificationNumber + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", supplierContactDetail=" + supplierContactDetail +
                ", supplierPaymentMethods=" + supplierPaymentMethods +
                ", existedItems=" + existedItems +
                ", isActive=" + isActive +
                ", registrationDate=" + registrationDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }
}
