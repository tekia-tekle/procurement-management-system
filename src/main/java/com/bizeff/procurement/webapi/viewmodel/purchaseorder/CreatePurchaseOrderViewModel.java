package com.bizeff.procurement.webapi.viewmodel.purchaseorder;

import java.util.List;

public class CreatePurchaseOrderViewModel {
    private String orderId;
    private String departmentId;
    private String orderedDate;
    private List<String> orderedRequisitionIds;
    private String orderedTo;
    private String deliveryDate;
    private String shippingMethod;
    private String totalCost;
    private String orderStatus;
    private String orderedBy;
    private boolean isApproved;
    private boolean isShipped;
    private String lastUpdatedDate;
    public CreatePurchaseOrderViewModel(){}

    public CreatePurchaseOrderViewModel(String orderId,
                                        String departmentId,
                                        String orderedDate,
                                        List<String> orderedRequisitionIds,
                                        String orderedTo,
                                        String deliveryDate,
                                        String shippingMethod,
                                        String totalCost,
                                        String orderStatus,
                                        String orderedBy,
                                        boolean isApproved,
                                        boolean isShipped)
    {
        this.orderId = orderId;
        this.departmentId = departmentId;
        this.orderedDate = orderedDate;
        this.orderedRequisitionIds = orderedRequisitionIds;
        this.orderedTo = orderedTo;
        this.deliveryDate = deliveryDate;
        this.shippingMethod = shippingMethod;
        this.totalCost = totalCost;
        this.orderStatus = orderStatus;
        this.orderedBy = orderedBy;
        this.isApproved = isApproved;
        this.isShipped = isShipped;
        this.lastUpdatedDate = orderedDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public List<String> getOrderedRequisitionIds() {
        return orderedRequisitionIds;
    }

    public void setOrderedRequisitionIds(List<String> orderedRequisitionIds) {
        this.orderedRequisitionIds = orderedRequisitionIds;
    }

    public String getSupplier() {
        return orderedTo;
    }

    public void setSupplier(String orderedTo) {
        this.orderedTo = orderedTo;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String  getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String  totalCost) {
        this.totalCost = totalCost;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
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
}
