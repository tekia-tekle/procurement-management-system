package com.bizeff.procurement.services.purchaserequisition;


import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.purchaserequisition.*;
import com.bizeff.procurement.models.supplymanagement.Inventory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.*;

public class PurchaseRequisitionCatalogValidationService {
    private final Map<String, Inventory> existedCatalogMap;
    private final Map<String, PurchaseRequisition> requisitionMap;
    public PurchaseRequisitionCatalogValidationService(){
        this.existedCatalogMap = new HashMap<>();
        this.requisitionMap = new HashMap<>();
    }
    // Add inventory item to catalog
    public Inventory addItemToCatalog(String itemId,
                                      String itemName,
                                      int quantityAvailable,
                                      BigDecimal unitPrice,
                                      String itemCategory,
                                      LocalDate expiryDate,
                                      String specification){

        validateString(itemId, "Item ID");
        validateString(itemName, "Item name");
        validatePositiveValue(quantityAvailable, "Quantity Available");
        validateNotNull(unitPrice, "Unit price");
        validatePositiveValue(unitPrice.compareTo(BigDecimal.ZERO), "Unit price");
        validateString(itemCategory, "Item category");
        validateString(specification, "Item specification");
        validateExpirationDate(expiryDate);
        if (existedCatalogMap.containsKey(itemId)){
            throw new IllegalArgumentException("duplication of id is not allowed.");
        }
        Inventory inventory = new Inventory(itemId, itemName, quantityAvailable, unitPrice, itemCategory, expiryDate, specification);

        existedCatalogMap.put(itemId,inventory);

        return inventory;
    }

    /** Add a requisition with validated fields.*/
    public PurchaseRequisition addPurchaseRequisition(String requisitionNumber,
                                                         LocalDate requisitionDate,
                                                         User requester,
                                                         Department department,
                                                         CostCenter costCenter,
                                                         List<RequestedItem> items,
                                                         PriorityLevel priorityLevel,
                                                         LocalDate expectedDeliveryDate,
                                                         String justification){

        validateString(requisitionNumber, "Requisition number");
        validateDate(requisitionDate,"Requisition date");
        validateString(requester.getFullName(), "Requested by user");
        validateString(department.getDepartmentId(), "DepartmentId");
        validateString(costCenter.getCostCenterId(),"costCenterId");
        validateRequestedItems(items);
        validateString(justification, "Justification");
        validateNotNull(priorityLevel, "Priority level");
        validateDate(expectedDeliveryDate,"Delivery date");

        validateRequisitionDate(requisitionDate,expectedDeliveryDate);

        PurchaseRequisition requisition = new PurchaseRequisition(requisitionNumber,requisitionDate,requester,department, costCenter,priorityLevel,expectedDeliveryDate,justification);
        for (RequestedItem item:items){
            requisition.addItem(item);
        }
        validateRequisitionFieldsWithCatalogeData(requisition);

        requisitionMap.put(requisition.getRequisitionId(),requisition);

        return requisition;
    }
    /** Delete item from catalog if it expire.*/
    public void deleteItemFromCatalog(LocalDate date){
        validateDate(date,"Date");
        List<Inventory> expiredItems = existedCatalogMap.values().stream()
                .filter(inventory -> inventory.getExpiryDate().isBefore(date)).collect(Collectors.toList());
        if (expiredItems.isEmpty()){
            throw new IllegalArgumentException("there is no item that expire with the specified date.");
        }
        for (Inventory item: expiredItems){
            existedCatalogMap.remove(item.getItemId(),item);
        }
    }
    /** validation requisition fields with catalog data.*/
    public void validateRequisitionFieldsWithCatalogeData(PurchaseRequisition purchaseRequisition){
        if (existedCatalogMap.isEmpty()) {
            throw new IllegalArgumentException("Cannot add requisition because the item catalog is empty.");
        }

        // Validate all items in the requisition exist in the catalog
        for (RequestedItem item : purchaseRequisition.getItems()) {

            String itemId = item.getInventory().getItemId();
            // Check item exists in catalog
            Inventory catalogItem = existedCatalogMap.get(itemId);
            if (catalogItem == null) {
                throw new IllegalArgumentException("Item with ID '" + itemId + "' does not exist in catalog.");
            }
            // Check quantity
            if (item.getRequestedQuantity() > catalogItem.getQuantityAvailable()) {
                throw new IllegalArgumentException("Requested quantity (" + item.getRequestedQuantity() +
                        ") for item ID '" + itemId + "' exceeds catalog available quantity (" + catalogItem.getQuantityAvailable() + ").");
            }
            // Check unit price (strict match)
            if (item.getInventory().getUnitPrice().compareTo(catalogItem.getUnitPrice()) != 0) {
                throw new IllegalArgumentException("Unit price mismatch for item ID '" + itemId +
                        "'. Requested: " + item.getInventory().getUnitPrice()+ ", catalog: " + catalogItem.getUnitPrice());
            }
            // Check catalog item expiration
            if (catalogItem.getExpiryDate() != null && catalogItem.getExpiryDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Catalog item with ID '" + itemId + "' is expired.");
            }
            // Optional: Check expected delivery date before expiration
            if (purchaseRequisition.getExpectedDeliveryDate() != null && catalogItem.getExpiryDate() != null) {
                if (purchaseRequisition.getExpectedDeliveryDate().isAfter(catalogItem.getExpiryDate())) {
                    throw new IllegalArgumentException("Expected delivery date " + purchaseRequisition.getExpectedDeliveryDate() +
                            " for item ID '" + itemId + "' is after catalog item expiration date " + catalogItem.getExpiryDate());
                }
            }
        }
    }
    // Optional: get all existing requisitions
    public Map<String, PurchaseRequisition> getAllRequisitions() {
        return requisitionMap;
    }

    public Map<String, Inventory> getExistedCatalogMap() {
        return existedCatalogMap;
    }
}