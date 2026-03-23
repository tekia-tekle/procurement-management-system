package com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement;

import java.time.LocalDate;
import java.util.List;

public class UpdateSupplierContactDetailInputDS {
    private String supplierId;
    private String newSupplierName;
    private String newSupplierCategory;
    private String newTaxIdentificationNumber;
    private String newRegistrationNumber;
    private SupplierContactDetailsInputDS newContactDetail;
    private List<SupplierPaymentMethodsInputDS> newPaymentMethods;
    private LocalDate updatedDate;

    public UpdateSupplierContactDetailInputDS(){}

    public UpdateSupplierContactDetailInputDS(String supplierId,
                                              String newSupplierName,
                                              String newSupplierCategory,
                                              String newTaxIdentificationNumber,
                                              String newRegistrationNumber,
                                              SupplierContactDetailsInputDS newContactDetail,
                                              List<SupplierPaymentMethodsInputDS> newPaymentMethods,
                                              LocalDate updatedDate)
    {
        this.supplierId = supplierId;
        this.newSupplierName = newSupplierName;
        this.newSupplierCategory = newSupplierCategory;
        this.newTaxIdentificationNumber = newTaxIdentificationNumber;
        this.newRegistrationNumber = newRegistrationNumber;
        this.newContactDetail = newContactDetail;
        this.newPaymentMethods = newPaymentMethods;
        this.updatedDate = updatedDate;
    }
    public String getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getNewSupplierName() {
        return newSupplierName;
    }

    public void setNewSapplierName(String newSupplierName) {
        this.newSupplierName = newSupplierName;
    }

    public String getNewSupplierCategory() {
        return newSupplierCategory;
    }

    public void setNewSupplierCategory(String newSupplierCategory) {
        this.newSupplierCategory = newSupplierCategory;
    }

    public String getNewTaxIdentificationNumber() {
        return newTaxIdentificationNumber;
    }

    public void setNewTaxIdentificationNumber(String newTaxIdentificationNumber) {
        this.newTaxIdentificationNumber = newTaxIdentificationNumber;
    }

    public String getNewRegistrationNumber() {
        return newRegistrationNumber;
    }

    public void setNewRegistrationNumber(String newRegistrationNumber) {
        this.newRegistrationNumber = newRegistrationNumber;
    }

    public SupplierContactDetailsInputDS getNewContactDetail() {
        return newContactDetail;
    }

    public void setNewContactDetail(SupplierContactDetailsInputDS newContactDetail) {
        this.newContactDetail = newContactDetail;
    }

    public List<SupplierPaymentMethodsInputDS> getNewPaymentMethods() {
        return newPaymentMethods;
    }

    public void setNewPaymentMethod(List<SupplierPaymentMethodsInputDS> newPaymentMethods) {
        this.newPaymentMethods = newPaymentMethods;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }
}
