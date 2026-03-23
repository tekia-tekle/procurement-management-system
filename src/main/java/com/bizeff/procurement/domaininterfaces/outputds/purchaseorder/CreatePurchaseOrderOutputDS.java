package com.bizeff.procurement.domaininterfaces.outputds.purchaseorder;

import com.bizeff.procurement.models.enums.PurchaseOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreatePurchaseOrderOutputDS {
    private String orderId;
    private String departmentId;
    private LocalDate orderedDate;
    private List<String>orderedRequisitionIds;
    private String supplierId;
    private LocalDate deliveryDate;
    private String shippingMethod;
    private BigDecimal totalCost;
    private PurchaseOrderStatus orderStatus;
    private String orderedBy;
    private boolean isApproved;
    private boolean isShipped;
    public CreatePurchaseOrderOutputDS(
            String orderId,
            String departmentId,
            LocalDate orderedDate,
            List<String> orderedRequisitionIds,
            String supplierId,
            LocalDate deliveryDate,
            String shippingMethod,
            PurchaseOrderStatus orderStatus,
            String orderedBy,
            BigDecimal totalCost,
            boolean isApproved,
            boolean isShipped
    ){
        this.orderId = orderId;
        this.departmentId = departmentId;
        this.orderedDate = orderedDate;
        this.orderedRequisitionIds = orderedRequisitionIds;
        this.supplierId = supplierId;
        this.deliveryDate = deliveryDate;
        this.shippingMethod = shippingMethod;
        this.orderStatus = orderStatus;
        this.totalCost = totalCost;
        this.orderedBy = orderedBy;
        this.isApproved = isApproved;
        this.isShipped = isShipped;
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

    public LocalDate getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(LocalDate orderedDate) {
        this.orderedDate = orderedDate;
    }

    public List<String> getOrderedRequisitionIds() {
        return orderedRequisitionIds;
    }

    public void setOrderedRequisitionIds(List<String> orderedRequisitionIds) {
        this.orderedRequisitionIds = orderedRequisitionIds;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public PurchaseOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(PurchaseOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
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
