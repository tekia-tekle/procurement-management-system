package com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemsInputDS {
    private String itemId;
    private String itemName;
    private int requestedQuantity;
    private BigDecimal unitPrice;
    private String itemCategory;
    private LocalDate expireDate;
    private String specification;

    public ItemsInputDS(String itemId,
                        String itemName,
                        int requestedQuantity,
                        BigDecimal unitPrice,
                        String itemCategory,
                        LocalDate expireDate,
                        String specification)
    {
        this.itemId = itemId;
        this.itemName = itemName;
        this.requestedQuantity = requestedQuantity;
        this.unitPrice = unitPrice;
        this.itemCategory = itemCategory;
        this.expireDate = expireDate;
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

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public BigDecimal getTotalPrice(){
        return unitPrice.multiply(BigDecimal.valueOf(requestedQuantity));
    }
    public String getSpecification() {
        return specification;
    }
}
