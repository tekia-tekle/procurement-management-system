package com.bizeff.procurement.services.purchaseorder;


import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.purchaserequisition.RequestedItem;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.*;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateNotNull;

public class PurchaseOrderGeneratingService {

    private Map<String, PurchaseOrder> purchaseOrderMap;
    private Map<String, PurchaseRequisition> purchaseRequisitionMap;
    private Map<String, Supplier> supplierMap;
    private Map<String , Department> departmentMap;
    public PurchaseOrderGeneratingService() {
        this.purchaseOrderMap = new HashMap<>();
        this.purchaseRequisitionMap = new HashMap<>();
        this.supplierMap = new HashMap<>();
        this.departmentMap = new HashMap<>();
    }

    public Department addDepartment(Department department){
        validateNotNull(department,"Department");
        if (departmentMap.containsKey(department.getDepartmentId())){
            throw new IllegalArgumentException("Duplication of department Id is not allowed, so we can't create new department with Id"+ department.getDepartmentId());
        }
        departmentMap.put(department.getDepartmentId(), department);
        return department;
    }
    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (departmentMap == null || departmentMap.isEmpty()){
            throw new IllegalArgumentException("department must not null to create purchase Order.");
        }
        validateRequestedItemAgainstSupplier(purchaseOrder);
        String orderId = purchaseOrder.getOrderId();
        if (purchaseOrderMap.containsKey(orderId)) {
            throw new IllegalArgumentException("Purchase Order with ID " + orderId + " already exists.");
        }
        if (purchaseOrder.getOrderDate().isAfter(purchaseOrder.getDeliveryDate())) {
            throw new IllegalArgumentException("Order date cannot be after delivery date.");
        }
        purchaseOrderMap.put(orderId, purchaseOrder);
        return purchaseOrder;
    }
    public PurchaseOrder updatePurchaseOrder(String orderId,
                                             Department newDepartment,
                                             List<PurchaseRequisition> newRequisitions,
                                             Supplier newSupplier,
                                             String newShippingMethod,
                                             LocalDate newDeliveryDate,
                                             BigDecimal newTotalCost,
                                             LocalDate lastUpdateDate){

        PurchaseOrder purchaseOrder = getPurchaseOrderById(orderId);

        validateDate(lastUpdateDate,"Updated Date");
        if (newDeliveryDate.isBefore(lastUpdateDate)){
            throw new IllegalArgumentException("we can.t update purchase order fields after delivered.");
        }
        purchaseOrder.setDepartment(newDepartment);
        purchaseOrder.setRequisitionList(newRequisitions);
        purchaseOrder.setSupplier(newSupplier);
        purchaseOrder.setShippingMethod(newShippingMethod);
        purchaseOrder.setDeliveryDate(newDeliveryDate);
        purchaseOrder.setLastUpdatedDate(lastUpdateDate);

        validateRequestedItemAgainstSupplier(purchaseOrder);

        return purchaseOrder;
    }
    public void deletePurchaseOrder(String orderId) {
        validateString(orderId, "Order ID");

        if (!purchaseOrderMap.containsKey(orderId)) {
            throw new NoSuchElementException("No Purchase Order found with ID " + orderId);
        }
        purchaseOrderMap.remove(orderId);
    }

    public Department getDepartmentById(String departmentId){
        validateString(departmentId,"Department ID");
        return Optional.ofNullable(departmentMap.get(departmentId)).orElseThrow(()-> new NoSuchElementException("there is no department created before"));
    }
    public PurchaseOrder getPurchaseOrderById(String orderId) {
        validateString(orderId, "Order ID");

        return Optional.ofNullable(purchaseOrderMap.get(orderId))
                .orElseThrow(() -> new NoSuchElementException("No Purchase Order found with ID " + orderId));
    }

    public List<PurchaseOrder> purchaseOrderBySupplier(String supplierId){
        validateString(supplierId,"supplier Id");
        return purchaseOrderMap.values().stream().
                filter(purchaseOrder -> purchaseOrder.getSupplier().getSupplierId().equals(supplierId))
                .collect(Collectors.toList());
    }
    public List<PurchaseOrder> findOrderBySupplier(Supplier supplier){
        validateNotNull(supplier,"supplier Id");
        return purchaseOrderMap.values().stream().
                filter(purchaseOrder -> purchaseOrder.getSupplier().equals(supplier))
                .collect(Collectors.toList());
    }

    public List<PurchaseOrder> findOrdersByDepartment(String departmentId) {
        validateString(departmentId, "Department Id");

        return purchaseOrderMap.values().stream()
                .filter(order -> order.getDepartment().getDepartmentId().equals(departmentId))
                .collect(Collectors.toList());
    }

    public Map<String, Supplier> getSupplierMap() {
        return supplierMap;
    }
    public Map<String, PurchaseRequisition> getPurchaseRequisitionMap() {
        return purchaseRequisitionMap;
    }
    public Map<String, Department> getDepartmentMap() {
        return departmentMap;
    }
    public Map<String, PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderMap;
    }


    /** validation of the purchase order fields. */
    public static void validatePurchaseOrder(PurchaseOrder purchaseOrder){
        validateNotNull(purchaseOrder,"Purchase Order");
        validateString(purchaseOrder.getOrderId(), "Order ID");
        validateNotNull(purchaseOrder.getDepartment(), "Department");
        validateNotNull(purchaseOrder.getRequisitionList(), "Purchase Requisition");
        validateNotEmptyList(purchaseOrder.getRequisitionList(),"Purchase Requisition");
        validateDateNotInPast(purchaseOrder.getOrderDate(), "Order Date");
        validateNotNull(purchaseOrder.getSupplier(), "Supplier");
        validateString(purchaseOrder.getShippingMethod(), "Shipping Method");
        validateDateNotInPast(purchaseOrder.getDeliveryDate(), "Delivery Date");
        validatePositiveValue(purchaseOrder.getTotalCost(), "Total Cost");
        BigDecimal totalRequisitionCost = purchaseOrder.getRequisitionList().stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (purchaseOrder.getTotalCost().compareTo(totalRequisitionCost) < 0){
            throw new IllegalArgumentException("cost for Order can't be less than cost for requisition.");
        }
    }
    public static void validateRequestedItemAgainstSupplier(PurchaseOrder purchaseOrder){
        validatePurchaseOrder(purchaseOrder);
        Supplier supplier = purchaseOrder.getSupplier();
        List<Inventory> supplierItems = supplier.getExistedItems();

        for (PurchaseRequisition requisition : purchaseOrder.getRequisitionList()) {
            for (RequestedItem requestedItem : requisition.getItems()) {
                Inventory item = supplierItems.stream()
                        .filter(inventory -> inventory.equals(requestedItem.getInventory()))
                        .findFirst()
                        .orElseThrow(()->new NoSuchElementException("there is no such element in the supplier"));

                if (item.getQuantityAvailable() < requestedItem.getRequestedQuantity()){
                    throw new IllegalArgumentException("invalid Item requisition, since the quantity of requested item is more than the quantity in supplier.");
                }
                if (requisition.getExpectedDeliveryDate() != null && item.getExpiryDate() != null) {
                    if (requisition.getExpectedDeliveryDate().isAfter(item.getExpiryDate())) {
                        throw new IllegalArgumentException("Expected delivery date " + requisition.getExpectedDeliveryDate() +
                                " for item ID '" + requestedItem.getInventory().getItemId() + "' is after supplier item expiration date " + item.getExpiryDate());
                    }
                }
            }
        }
    }
    public static void validateNotEmptyList(List<?> list, String fieldName) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty.");
        }
    }
    public static void validateDateNotInPast(LocalDate date, String fieldName) {
        validateDate(date,fieldName);
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(fieldName + " must not be in the past.");
        }
    }
    public static void validatePositiveValue(BigDecimal value, String fieldName) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(fieldName + " must be a positive value.");
        }
    }
}
