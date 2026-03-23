package com.bizeff.procurement.services.purchaserequisition;


import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.purchaserequisition.*;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.*;

public class PurchaseRequisitionMaintenancesService {

    private final Map<String, PurchaseRequisition> requisitionMap;

    public PurchaseRequisitionMaintenancesService() {
        this.requisitionMap = new HashMap<>();
    }

    /** Create new Requisition. */
    public PurchaseRequisition createPurchaseRequisition(String requisitionNumber,
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
        validateRequestedItemsAgainstSupplier(requisition);

        requisitionMap.put(requisition.getRequisitionId(),requisition);

        return requisition;

    }

    /** Update existed Requisition by new Requisition. */

    public PurchaseRequisition updateRequisition(String existedReqId,
                                                 String newRequisitionNumber,
                                                 User newRequester,
                                                 Department newDepartment,
                                                 CostCenter newCostCenter,
                                                 List<RequestedItem> newItems,
                                                 PriorityLevel newPriorityLevel,
                                                 LocalDate newDeliveryDate,
                                                 String newJustification,
                                                 Supplier supplier,
                                                 LocalDate upDatedDate){

        validateString(newRequisitionNumber, "Requisition number");
        validateDate(newDeliveryDate,"Delivery date");
        validateString(newRequester.getFullName(), "Requested by user");
        validateString(newDepartment.getDepartmentId(), "DepartmentId");
        validateString(newCostCenter.getCostCenterId(),"costCenterId");
        validateRequestedItems(newItems);
        validateString(newJustification, "Justification");
        validateNotNull(newPriorityLevel, "Priority level");
        validateDate(upDatedDate,"Updated date");
        validateRequisitionDate(upDatedDate,newDeliveryDate);

        PurchaseRequisition requisition = getRequisitionById(existedReqId);

        validateRequestedItemsAgainstSupplier(requisition);
        validateRequisitionDate(requisition.getRequisitionDate(),newDeliveryDate);

        requisition.setRequisitionNumber(newRequisitionNumber);
        requisition.setRequestedBy(newRequester);
        requisition.setDepartment(newDepartment);
        requisition.setCostCenter(newCostCenter);
        requisition.setItems(newItems);
        requisition.setPriorityLevel(newPriorityLevel);
        requisition.setExpectedDeliveryDate(newDeliveryDate);
        requisition.setJustification(newJustification);
        requisition.setSupplier(supplier);
        requisition.setUpdatedDate(upDatedDate);

        return requisition;
    }
    /** Delete Requisition by requisition Id. */
    public void deleteRequisition(String requisitionId) {
        validateString(requisitionId,"Requisition Id");

        if (!requisitionMap.containsKey(requisitionId)) {
            throw new NoSuchElementException("Cannot delete. No requisition found with requisitionNumber: " + requisitionId);
        }

        requisitionMap.remove(requisitionId);
    }
    /** Get Requisition By Requisition ID. */

    public PurchaseRequisition getRequisitionById(String requisitionId){
        validateString(requisitionId,"Requisition Id");
        return Optional.ofNullable(requisitionMap.get(requisitionId.trim()))
                .orElseThrow(()-> new NoSuchElementException("there is no requisition with the given id."));
    }
    /** Get Requisition by RequisitionNumber */
    public PurchaseRequisition getRequisitionByRequisitionNumber(String requisitionNumber) {
        validateString(requisitionNumber,"Requisition Number");

        return requisitionMap.values().stream().filter(req->req.getRequisitionNumber().equals(requisitionNumber))
                .findFirst().orElseThrow(() -> new NoSuchElementException("No requisition found with requisitionNumber: " + requisitionNumber));
    }

    /** Get requisitions by their categories. */
    public List<PurchaseRequisition> getRequisitionsByCategory(String category) {
        return requisitionMap.values().stream()
                .filter(req -> req.getItems().stream()
                        .anyMatch(item -> item.getInventory().getItemCategory().equalsIgnoreCase(category)))
                .collect(Collectors.toList());
    }

    /** Sorts requisitions by priority level first, then by expected delivery date */
    public List<PurchaseRequisition> sortRequisitionByLevelOfPriority(List<PurchaseRequisition> purchaseRequisitions) {
        return purchaseRequisitions.stream()
                .sorted(Comparator.comparing(PurchaseRequisition::getPriorityLevel).reversed()
                        .thenComparing(PurchaseRequisition::getExpectedDeliveryDate)) // If priority is equal, sort by date
                .collect(Collectors.toList());
    }

    /** Sorts requisitions by expected delivery date first, then by priority level */
    public List<PurchaseRequisition> sortRequisitionByExpectedDeliveryDate(List<PurchaseRequisition> purchaseRequisitionList) {
        return purchaseRequisitionList.stream()
                .sorted(Comparator.comparing(PurchaseRequisition::getExpectedDeliveryDate)
                        .thenComparing(Comparator.comparing(PurchaseRequisition::getPriorityLevel).reversed())) // If date is equal, sort by priority
                .collect(Collectors.toList());
    }

    /** Get requisitionLists by their priority level.*/
    public List<PurchaseRequisition> getRequisitionsByPriority(PriorityLevel priority) {
        if (priority == null|| priority.name().trim().isEmpty()){
            throw new IllegalArgumentException("priority level is required field.");
        }
        return requisitionMap.values().stream()
                .filter(req -> req.getPriorityLevel() == priority)
                .collect(Collectors.toList());
    }

    /** Track requisition over a time. */
    public List<PurchaseRequisition> trackOvertime(int daysThreshold) {
        if (daysThreshold < 0){
            throw new IllegalArgumentException("days of threshold must be positive.");
        }
        LocalDate cutoffDate = LocalDate.now().minusDays(daysThreshold);
        return requisitionMap.values().stream()
                .filter(req -> req.getRequisitionDate().isBefore(cutoffDate))
                .collect(Collectors.toList());
    }
    public List<PurchaseRequisition> getRequisitionInSpecifiedTimeRange(LocalDate startDate,LocalDate endDate){
        validateRequisitionDate(startDate,endDate);
        return requisitionMap.values().stream()
                .filter(requisition -> !requisition.getRequisitionDate().isBefore(startDate)
                        && !requisition.getRequisitionDate().isAfter(endDate)).collect(Collectors.toList());
    }
    /** Get the total requisitions.*/
    public Map<String, PurchaseRequisition> getRequisitionMap() {
        return requisitionMap;
    }
    public PurchaseRequisition validateRequestedItemsAgainstSupplier(PurchaseRequisition purchaseRequisition){
        if (purchaseRequisition.getSupplier() != null){

            Supplier supplier =purchaseRequisition.getSupplier();

            List<Inventory> existedItems = supplier.getExistedItems();

            List<RequestedItem> requestedItems = purchaseRequisition.getItems();

            for (RequestedItem requestedItem:requestedItems){

                Inventory item = existedItems.stream().filter(inventory -> inventory.equals(requestedItem.getInventory())).findFirst().orElseThrow(()->new NoSuchElementException("there is no such element in the supplier"));

                if (item.getQuantityAvailable() < requestedItem.getRequestedQuantity()){
                    throw new IllegalArgumentException("invalid Item requisition, since the quantity of requested item is more than the quantity in supplier.");
                }

                if (purchaseRequisition.getExpectedDeliveryDate() != null && item.getExpiryDate() != null) {

                    if (purchaseRequisition.getExpectedDeliveryDate().isAfter(item.getExpiryDate())) {

                        throw new IllegalArgumentException("Expected delivery date " + purchaseRequisition.getExpectedDeliveryDate() +
                                " for item ID '" + requestedItem.getInventory().getItemId() + "' is after supplier item expiration date " + item.getExpiryDate());

                    }
                }
            }
        }
        return purchaseRequisition;
    }
}