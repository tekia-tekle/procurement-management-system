package com.bizeff.procurement.services.purchaseorder;

import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.*;
import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateNotNull;
import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateString;

public class PurchaseOrderEnsuringServices {
    private Map<String, PurchaseOrder> purchaseOrderMap;
    private Map<String, PurchaseRequisition> purchaseRequisitionMap;
    private Map<String, Department> departmentMap;
    private Map<String, Supplier> supplierMap;

    public PurchaseOrderEnsuringServices(){
        this.purchaseOrderMap = new HashMap<>();
        this.purchaseRequisitionMap = new HashMap<>();
        this.departmentMap = new HashMap<>();
        this.supplierMap = new HashMap<>();
    }
    public PurchaseOrder createPurchaseOrder(
                                               Department department,
                                               List<PurchaseRequisition> requisitionList,
                                               LocalDate orderDate,
                                               Supplier supplier,
                                               String shippingMethod,
                                               LocalDate deliveryDate)
    {
        validateNotNull(department, "Department");
        validateNotNull(requisitionList, "Purchase Requisition");
        validateNotEmptyList(requisitionList,"Purchase Requisition");
        validateDateNotInPast(orderDate, "Order Date");
        validateNotNull(supplier, "Supplier");
        validateString(shippingMethod, "Shipping Method");
        validateDateNotInPast(deliveryDate, "Delivery Date");

        department = getDepartmentById(department.getDepartmentId());
        supplier = getSupplierById(supplier.getSupplierId());
        requisitionList = requisitionList.stream().map(purchaseRequisition -> getRequisitionByRequisitionId(purchaseRequisition.getRequisitionId())).collect(Collectors.toList());

        List<PurchaseRequisition> approvedRequisition = requisitionList.stream()
                .filter(requisition -> requisition.getRequisitionStatus().equals(RequisitionStatus.APPROVED))
                .collect(Collectors.toList());

        if (approvedRequisition.isEmpty()){
            throw new IllegalArgumentException("there is no requisition in which status is approved,so we can't create any purchase order for not approved requisitions.");
        }

        if (orderDate.isAfter(deliveryDate)) {
            throw new IllegalArgumentException("Order date cannot be after delivery date.");
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,approvedRequisition,orderDate, supplier,shippingMethod,deliveryDate);

        purchaseOrderMap.put(purchaseOrder.getOrderId(), purchaseOrder);

        return purchaseOrder;
    }

    public RequisitionStatus ensureRequisitionStatusOfPurchaseOrder(String orderId) {
        validateString(orderId, "Order Id");
        PurchaseOrder purchaseOrder = getPurchaseOrderByOrderId(orderId);
        List<PurchaseRequisition> requisitions = purchaseOrder.getRequisitionList();

        if (requisitions.isEmpty()) {
            throw new IllegalStateException("No requisitions are associated with the given Purchase Order.");
        }

        boolean allApproved = requisitions.stream()
                .allMatch(req -> req.getRequisitionStatus() == RequisitionStatus.APPROVED);

        if (!allApproved) {
            throw new IllegalStateException("Not all requisitions associated with the Purchase Order are APPROVED.");
        }

        return RequisitionStatus.APPROVED;
    }
    public PurchaseOrder getPurchaseOrderByOrderId(String orderId){
        PurchaseRequisitionFieldValidator.validateString(orderId, "Order Id");

        return Optional.ofNullable(purchaseOrderMap.get(orderId)).orElseThrow(()->new NoSuchElementException("there is no such purchase order in the order list with the order Id :" + orderId));
    }
    public List<PurchaseOrder> findBySupplierId(String supplierId) {
        validateString(supplierId, "Supplier Id");

        return purchaseOrderMap.values().stream()
                .filter(order -> order.getSupplier().getSupplierId().equals(supplierId))
                .collect(Collectors.toList());
    }
    public PurchaseRequisition getRequisitionByRequisitionId(String requisitionId){
        validateString(requisitionId,"Requisition Id");

        return Optional.ofNullable(purchaseRequisitionMap.get(requisitionId)).orElseThrow(()->new NoSuchElementException("there is no such requisition with the requisition id "+ requisitionId));
    }
    public Department getDepartmentById(String departmentId){

        validateString(departmentId, "Department Id");

        return Optional.ofNullable(departmentMap.get(departmentId)).orElseThrow(()->new NoSuchElementException("there is no Such department in the Department map with key "+ departmentId));
    }

    public Supplier getSupplierById(String supplierId){

        validateString(supplierId,"Supplier Id");

        return Optional.ofNullable(supplierMap.get(supplierId)).orElseThrow(()-> new NoSuchElementException("there is no supplier with the key "+ supplierId));
    }
    public Map<String, PurchaseOrder> getPurchaseOrderMap() {
        return purchaseOrderMap;
    }

    public Map<String, PurchaseRequisition> getPurchaseRequisitionMap() {
        return purchaseRequisitionMap;
    }
    public Map<String, Department> getDepartmentMap() {
        return departmentMap;
    }
    public Map<String, Supplier> getSupplierMap() {
        return supplierMap;
    }
}
