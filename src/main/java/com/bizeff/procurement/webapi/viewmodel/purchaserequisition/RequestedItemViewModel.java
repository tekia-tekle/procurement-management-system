package com.bizeff.procurement.webapi.viewmodel.purchaserequisition;

public class RequestedItemViewModel {
    private String itemId;
    private String itemName;
    private String itemCategory;
    private int requestedQuantity;
    private String unitPrice;
    private String totalPrice; // e.g., "$96,000.00"
    private String expiryDate; // e.g., "22 Jul 2025"
    private String specification;

    public RequestedItemViewModel(String itemId,
                                  String itemName,
                                  String itemCategory,
                                  int requestedQuantity,
                                  String unitPrice,
                                  String totalPrice,
                                  String expiryDate,
                                  String specification) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.requestedQuantity = requestedQuantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.expiryDate = expiryDate;
        this.specification = specification;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
