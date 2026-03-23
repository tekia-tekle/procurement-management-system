package com.bizeff.procurement.models.supplymanagement;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Inventory {
    private Long id;
    private String itemId;
    private String itemName;
    private int quantityAvailable;
    private BigDecimal unitPrice;
    private String itemCategory;
    private LocalDate expiryDate; // Useful for perishable goods
    private String specification;

    public Inventory(){}
    public Inventory(
            String itemId,
            String itemName,
            int quantityAvailable,
            BigDecimal unitPrice,
            String itemCategory,
            LocalDate expiryDate,
            String specification
    ) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantityAvailable = quantityAvailable;
        this.unitPrice = unitPrice;
        this.itemCategory = itemCategory;

        this.expiryDate = expiryDate;
        this.specification = specification;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantityAvailable));
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", quantityAvailable=" + quantityAvailable +
                ", unitPrice=" + unitPrice +
                ", itemCategory='" + itemCategory + '\'' +
                ", expiryDate=" + expiryDate +
                ", specification='" + specification + '\'' +
                '}';
    }
}
