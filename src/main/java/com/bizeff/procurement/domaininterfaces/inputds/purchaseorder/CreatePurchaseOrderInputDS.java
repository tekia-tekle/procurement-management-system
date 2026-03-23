package com.bizeff.procurement.domaininterfaces.inputds.purchaseorder;

import java.time.LocalDate;
import java.util.List;

public class CreatePurchaseOrderInputDS {
    private PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS;
    private String departmentId;
    private String supplierId;
    private List<String> requisitionIdLists;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String shippingMethod;
    public CreatePurchaseOrderInputDS(String departmentId,
                                      PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS,
                                      String supplierId,
                                      List<String> requisitionIdLists,
                                      LocalDate orderDate,
                                      LocalDate deliveryDate,
                                      String shippingMethod){
        this.departmentId = departmentId;
        this.purchaseOrderContactDetailsInputDS = purchaseOrderContactDetailsInputDS;
        this.supplierId = supplierId;
        this.requisitionIdLists = requisitionIdLists;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.shippingMethod = shippingMethod;
    }

    // Constructor
    public PurchaseOrderContactDetailsInputDS getPurchaseOrderContactDetailsInputDS() {
        return purchaseOrderContactDetailsInputDS;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public List<String> getRequisitionIdLists() {
        return requisitionIdLists;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setPurchaseOrderContactDetailsInputDS(PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS) {
        this.purchaseOrderContactDetailsInputDS = purchaseOrderContactDetailsInputDS;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public void setRequisitionIdLists(List<String> requisitionIdLists) {
        this.requisitionIdLists = requisitionIdLists;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
    public String getShippingMethod() {
        return shippingMethod;
    }
}