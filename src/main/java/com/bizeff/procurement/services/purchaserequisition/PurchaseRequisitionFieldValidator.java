package com.bizeff.procurement.services.purchaserequisition;


import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.purchaserequisition.RequestedItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PurchaseRequisitionFieldValidator {

    /** Validates inventory items within a requisition */
    public static void validateRequestedItems(List<RequestedItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Requisition must contain at least one item.");
        }
        for (RequestedItem item : items) {
            validateString(item.getInventory().getItemId(), "Item ID");
            validateString(item.getInventory().getItemName(), "Item name");
            validatePositiveValue(item.getRequestedQuantity(), "Quantity Available");
            validateNotNull(item.getInventory().getUnitPrice(), "Unit price");
            validatePositiveValue(item.getInventory().getUnitPrice().compareTo(BigDecimal.ZERO), "Unit price");
            validateString(item.getInventory().getItemCategory(), "Item category");
            validateString(item.getInventory().getSpecification(), "Item specification");
            validateExpirationDate(item.getInventory().getExpiryDate());
        }
    }

    /** Validates string fields */
    public static void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be null or empty.");
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
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive.");
        }
    }

    /** validate Requisition and expectedDeliveryDate */
    public static void validateRequisitionDate(LocalDate requisitionDate, LocalDate expectedDeliveryDate) {
        validateDate(requisitionDate, "Requisition date");

        if (expectedDeliveryDate == null) {
            throw new IllegalArgumentException("Expected delivery date must not be null.");
        }

        if (expectedDeliveryDate.isBefore(requisitionDate)) {
            throw new IllegalArgumentException("Expected delivery date cannot be before requisition date.");
        }
    }

    /** Ensures expiration dates are valid */
    public static void validateExpirationDate(LocalDate expiryDate) {
        if (expiryDate != null && expiryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiration date must not be in the past.");
        }
    }

}


