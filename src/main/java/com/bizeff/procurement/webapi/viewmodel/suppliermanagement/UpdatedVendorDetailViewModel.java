package com.bizeff.procurement.webapi.viewmodel.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.UpdateSupplierContactDetailedOutputDS;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.SupplierContactDetail;
import com.bizeff.procurement.models.supplymanagement.SupplierPaymentMethod;

import java.util.List;

public class UpdatedVendorDetailViewModel {
    private String supplierId;
    private String supplierName;
    private String supplierCategory;
    private String taxIdentificationNumber;
    private String registrationNumber;
    private String registrationDate;
    private SupplierContactDetail contactDetail;
    private List<SupplierPaymentMethod> paymentMethods;
    private List<Inventory> existedItems;
    private String updatedDate;
    private Boolean isUpdated;
    public UpdatedVendorDetailViewModel(){}

    public UpdatedVendorDetailViewModel(String supplierId,
                                        String supplierName,
                                        String supplierCategory,
                                        String taxIdentificationNumber,
                                        String registrationNumber,
                                        String registrationDate,
                                        SupplierContactDetail contactDetail,
                                        List<SupplierPaymentMethod> paymentMethods,
                                        List<Inventory> existedItems,
                                        String updatedDate,
                                        Boolean isUpdated) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierCategory = supplierCategory;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.registrationNumber = registrationNumber;
        this.registrationDate = registrationDate;
        this.contactDetail = contactDetail;
        this.paymentMethods = paymentMethods;
        this.existedItems = existedItems;
        this.updatedDate = updatedDate;
        this.isUpdated = isUpdated;
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

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
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

    public void setPaymentMethods(List<SupplierPaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public List<Inventory> getExistedItems() {
        return existedItems;
    }

    public void setExistedItems(List<Inventory> existedItems) {
        this.existedItems = existedItems;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public void setUpdated(Boolean updated) {
        isUpdated = updated;
    }

    private UpdateSupplierContactDetailedOutputDS updatedVendor;
    public UpdatedVendorDetailViewModel(UpdateSupplierContactDetailedOutputDS updatedVendor){
        this.updatedVendor = updatedVendor;
    }

    public UpdateSupplierContactDetailedOutputDS getUpdatedVendor() {
        return updatedVendor;
    }

    public void setUpdatedVendor(UpdateSupplierContactDetailedOutputDS updatedVendor) {
        this.updatedVendor = updatedVendor;
    }

    @Override
    public String toString() {
        return "UpdatedVendorDetailViewModel{" +
                "updatedVendor=" + updatedVendor +
                '}';
    }
}
