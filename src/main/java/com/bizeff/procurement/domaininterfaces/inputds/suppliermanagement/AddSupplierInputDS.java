package com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.ItemsInputDS;

import java.time.LocalDate;
import java.util.List;
public class AddSupplierInputDS {
    private String supplierName;
    private String supplierCategory;
    private String taxIdentificationNumber;
    private String registrationNumber;
    private SupplierContactDetailsInputDS supplierContactDetailsInputDS;
    private List<SupplierPaymentMethodsInputDS> supplierPaymentMethodsInputDS;
    private List<ItemsInputDS> itemstobeIncluded;
    private LocalDate registrationDate;
    public AddSupplierInputDS(){}

    public AddSupplierInputDS(
            String supplierName,
            String supplierCategory,
            String taxIdentificationNumber,
            String registrationNumber,
            SupplierContactDetailsInputDS supplierContactDetailsInputDS,
            List<SupplierPaymentMethodsInputDS> supplierPaymentMethodsInputDS,
            List<ItemsInputDS> itemstobeIncluded,
            LocalDate registrationDate
    ) {
        this.supplierName = supplierName;
        this.supplierCategory = supplierCategory;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.registrationNumber = registrationNumber;
        this.supplierContactDetailsInputDS = supplierContactDetailsInputDS;
        this.supplierPaymentMethodsInputDS = supplierPaymentMethodsInputDS;
        this.itemstobeIncluded = itemstobeIncluded;
        this.registrationDate = registrationDate;
    }

    // Getters only (immutable use case data)

    public String getSupplierName() { return supplierName; }
    public String getSupplierCategory() { return supplierCategory; }
    public String getTaxIdentificationNumber() { return taxIdentificationNumber; }
    public String getRegistrationNumber() { return registrationNumber; }
    public SupplierContactDetailsInputDS getSupplierContactDetailsInputDS() { return supplierContactDetailsInputDS; }
    public List<SupplierPaymentMethodsInputDS> getSupplierPaymentMethodsInputDS() { return supplierPaymentMethodsInputDS; }
    public List<ItemsInputDS> getItemstobeIncluded() { return itemstobeIncluded; }
    public LocalDate getRegistrationDate() { return registrationDate; }
}
