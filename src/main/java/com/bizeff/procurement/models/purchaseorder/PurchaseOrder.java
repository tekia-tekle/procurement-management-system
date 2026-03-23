package com.bizeff.procurement.models.purchaseorder;

import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.purchaserequisition.RequestedItem;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrder {
    private Long id;
    private String orderId;
    private Department department;
    private List<PurchaseRequisition> requisitionList = new ArrayList<>();
    private LocalDate orderDate;
    private Supplier supplier;
    private String shippingMethod;
    private LocalDate deliveryDate;
    private PurchaseOrderStatus purchaseOrderStatus;
    private boolean isApproved;
    private boolean isShipped;
    private LocalDate lastUpdatedDate;
    private List<OrderedItem>orderedItems = new ArrayList<>();
    public PurchaseOrder(){}
    public PurchaseOrder(Department department,
                         List<PurchaseRequisition> requisitionList,
                         LocalDate orderDate,
                         Supplier supplier,
                         String shippingMethod,
                         LocalDate deliveryDate) {
        this.orderId = IdGenerator.generateId("PO");
        this.department = department;
        this.requisitionList = requisitionList;
        this.orderDate = orderDate;
        this.supplier = supplier;
        this.shippingMethod = shippingMethod;
        this.deliveryDate = deliveryDate;
        this.purchaseOrderStatus = PurchaseOrderStatus.PENDING;
        this.isApproved = false;
        this.isShipped = false;
        this.lastUpdatedDate = orderDate;
    }

    public BigDecimal getTotalCost() {
        return requisitionList.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(OrderedItem orderedItem){
        orderedItems.add(orderedItem);
    }
    public void addRequisition(PurchaseRequisition requisition) {
        if (requisition != null && !requisitionList.contains(requisition)) {
            requisitionList.add(requisition);
            this.lastUpdatedDate = LocalDate.now();
        }
    }
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<PurchaseRequisition> getRequisitionList() {
        return requisitionList;
    }

    public void setRequisitionList(List<PurchaseRequisition> requisitionList) {
        this.requisitionList = requisitionList;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public PurchaseOrderStatus getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public void setPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isShipped() {
        return isShipped;
    }

    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    public void setOrderedItems(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }
    public List<OrderedItem>getOrderedItems(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        for (PurchaseRequisition requisition: getRequisitionList()){
            List<RequestedItem> requestedItems = requisition.getItems();
            for (RequestedItem requestedItem: requestedItems){
                OrderedItem orderedItem = new OrderedItem(requestedItem.getInventory(),requestedItem.getRequestedQuantity());
                orderedItemList.add(orderedItem);
            }
        }
        return orderedItemList;
    }
    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "orderId='" + orderId + '\'' +
                ", department=" + department +
                ", requisitionList=" + requisitionList +
                ", orderDate=" + orderDate +
                ", supplier=" + supplier +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", purchaseOrderStatus=" + purchaseOrderStatus +
                ", totalCost=" + getTotalCost() +
                ", isApproved=" + isApproved +
                ", isShipped=" + isShipped +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }
}