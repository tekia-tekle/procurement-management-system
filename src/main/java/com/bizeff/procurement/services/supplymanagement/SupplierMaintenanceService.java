package com.bizeff.procurement.services.supplymanagement;


import com.bizeff.procurement.models.supplymanagement.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SupplierMaintenanceService {
    private Map<String , Supplier> supplierMap; // String Represents supplierId

    public SupplierMaintenanceService() {
        this.supplierMap = new HashMap<>();
    }

    /**Create new SupplierModel.*/
    public Supplier createSupplier(String supplierName,
                                        String supplierCategory,
                                        String taxIdentificationNumber,
                                        String registrationNumber,
                                        SupplierContactDetail supplierContactDetail,
                                        List<SupplierPaymentMethod> supplierPaymentMethods,
                                        List<Inventory> existedItems,
                                        LocalDate registrationDate){


        validateString(supplierName, "SupplierModel name");
        validateString(supplierCategory, "SupplierModel Category");
        validateString(taxIdentificationNumber, "Tax Identification Number");
        validateString(registrationNumber, "Registration Number");
        validateSupplierContactDetail(supplierContactDetail);
        validateItems(existedItems);
        validateSupplierPaymentMethod(supplierPaymentMethods);
        validateDate(registrationDate,"Registration Date");

        Supplier supplierModel = new Supplier(supplierName, supplierCategory, taxIdentificationNumber, registrationNumber, supplierContactDetail, supplierPaymentMethods, existedItems, registrationDate);
        if (supplierModel.getExistedItems().isEmpty()){
            throw new IllegalArgumentException("supplier must contain at least one Inventory.");
        }
        if (supplierMap.containsKey(supplierModel.getSupplierId())){
            throw new IllegalArgumentException("duplication of supplierId is not allowed.");
        }
        if (supplierMap.values().contains(supplierModel.getRegistrationNumber())){
            throw new IllegalArgumentException("duplication of registrationNumber is not allowed.");
        }
        if (supplierMap.values().contains(supplierModel.getTaxIdentificationNumber())){
            throw new IllegalArgumentException("duplication of taxIdentificationNumber is not allowed.");
        }
        supplierMap.put(supplierModel.getSupplierId(), supplierModel);

        return supplierModel;
    }

    /** Update existing SupplierModel details */
    public Supplier updateSupplier(String existingId,
                                        String newSupplierName,
                                        String newSupplierCategory,
                                        String newTaxId,
                                        String newRegNum,
                                        SupplierContactDetail newContactDetail,
                                        List<SupplierPaymentMethod> newPaymentMethods,
                                        List<Inventory> newItems,
                                        LocalDate lastUPdatedDate){

        validateString(newSupplierName, "SupplierName name");
        validateString(newSupplierCategory, "SupplierModel Category");
        validateString(newTaxId, "Tax Identification Number");
        validateString(newRegNum, "Registration Number");
        validateSupplierContactDetail(newContactDetail);
        validateItems(newItems);
        validateSupplierPaymentMethod(newPaymentMethods);
        validateDate(lastUPdatedDate,"LastUPdated Date");

        Supplier supplierModel = getSupplierById(existingId);

        supplierModel.setSupplierName(newSupplierName);
        supplierModel.setSupplierCategory(newSupplierCategory);
        supplierModel.setTaxIdentificationNumber(newTaxId);
        supplierModel.setRegistrationNumber(newRegNum);
        supplierModel.setSupplierContactDetail(newContactDetail);
        supplierModel.setSupplierPaymentMethods(newPaymentMethods);
        supplierModel.setLastUpdated(lastUPdatedDate);
        if (newItems.isEmpty()){
            throw new IllegalArgumentException("we can't update with empty items.");
        }
        supplierModel.setExistedItems(newItems);

        return supplierModel;
    }

    /** Delete SupplierModel from the system */
    public void removeSupplier(String  supplierId) {
        validateNotNull(supplierId,"supplierId");
        if (!supplierMap.containsKey(supplierId)) {
            throw new NoSuchElementException("there is no supplier in the supplier Map with key " + supplierId);
        }
        supplierMap.remove(supplierId);
    }

    /** Retrieve SupplierModel by ID */
    public Supplier getSupplierById(String supplierId) {
        validateNotNull(supplierId,"supplierId");
        return Optional.ofNullable(supplierMap.get(supplierId))
                .orElseThrow(() -> new NoSuchElementException("there is no supplier in the supplierMap with key " + supplierId));
    }

    /** Retrieve suppliers by category/type */
    public List<Supplier> getSupplierByCategory(String supplierCategory) {
        validateString(supplierCategory, "SupplierModel Category");
        return supplierMap.values().stream()
                .filter(supplier -> supplier.getSupplierCategory().equalsIgnoreCase(supplierCategory))
                .collect(Collectors.toList());
    }

    /** Retrieve active suppliers */
    public List<Supplier> getActiveSuppliers() {
        return supplierMap.values().stream()
                .filter(Supplier::isActive)
                .collect(Collectors.toList());
    }


    /** Validates a supplier payment method */
    public static void validateSupplierPaymentMethod(List<SupplierPaymentMethod> paymentMethods) {
        Objects.requireNonNull(paymentMethods, "SupplierModel payment method cannot be null");
        for (SupplierPaymentMethod paymentMethod: paymentMethods){
            Objects.requireNonNull(paymentMethod.getPreferredPaymentMethod(), "Preferred payment method");
            if (!paymentMethod.getPaymentMethods().contains(paymentMethod.getPreferredPaymentMethod())) {
                throw new IllegalArgumentException("Preferred payment method must be in accepted list");
            }
            validateString(paymentMethod.getAccountHolderName(), "Account Holder Name");
            validateString(paymentMethod.getAccountHolderPhoneNumber(), "Account Holder PhoneNumber");
            String phoneRegex = "^\\+\\d{1,3}\\d{4,14}$";
            if (!paymentMethod.getAccountHolderPhoneNumber().matches(phoneRegex)){
                throw new IllegalArgumentException("Invalid phone number, Phone Number should be with country code.");
            }
            validateString(paymentMethod.getBankName(), "Bank name");
            validateString(paymentMethod.getBankAccountNumber(), "Bank account number");
            validateString(paymentMethod.getCurrencyType(), "Currency type");
            validateBigDecimal(paymentMethod.getCreditLimit(), "Credit limit");
        }
    }

    /** Validates a SupplierModel contact detail */
    public static void validateSupplierContactDetail(SupplierContactDetail supplierContactDetail) {
        Objects.requireNonNull(supplierContactDetail, "SupplierModel contact detail cannot be null");
        validateString(supplierContactDetail.getFullName(), "User name");
        validateString(supplierContactDetail.getEmail(), "User email");
        validateString(supplierContactDetail.getPhone(), "User phone number");
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!supplierContactDetail.getEmail().matches(emailRegex)){
            throw new IllegalArgumentException("please Enter valid email address.based on the emailRegex = \"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\";\n");
        }
        String phoneRegex = "^\\+\\d{1,3}\\d{4,14}$";
        if (!supplierContactDetail.getPhone().matches(phoneRegex)){
            throw new IllegalArgumentException("Please Enter valid phone number with country code.");
        }
    }
    /** Validates inventory items within a requisition */
    public static void validateItems(List<Inventory> items) {
        if (items == null) {
            throw new IllegalArgumentException("Inventory can't be null.");
        }
        for (Inventory item : items) {
            validateString(item.getItemId(), "Item ID");
            validateString(item.getItemName(), "Item name");
            validatePositiveValue(item.getQuantityAvailable(), "Quantity Available");
            validateNotNull(item.getUnitPrice(), "Unit price");
            validatePositiveValue(item.getUnitPrice().compareTo(BigDecimal.ZERO), "Unit price");
            validateString(item.getItemCategory(), "Item category");
            validateString(item.getSpecification(), "Item specification");
            validateExpirationDate(item.getExpiryDate());
        }
    }

    /** Validates a string field */
    public static void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
        }
    }

    /** Validates date fields */
    public static void validateDate(LocalDate date, String fieldName) {
        if (date == null) {
            throw new IllegalArgumentException(fieldName + " must not be null.");
        }
    }

    /** Validates non-null objects */
    public static void validateNotNull(Object object, String fieldName) {
        if (object == null) {
            throw new IllegalArgumentException(fieldName + " must not be null.");
        }
    }

    /** Validates numeric values to ensure they are positive */
    public static void validatePositiveValue(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " must be positive.");
        }
    }

    /** Validates a BigDecimal field */
    public static void validateBigDecimal(BigDecimal value, String fieldName) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be null or negative.");
        }
    }

    /** Ensures expiration dates are valid */
    public static void validateExpirationDate(LocalDate expiryDate) {
        if (expiryDate != null && expiryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiration date must not be in the past.");
        }
    }
    public Map<String, Supplier> getSupplierMap() {
        return supplierMap;
    }

}
