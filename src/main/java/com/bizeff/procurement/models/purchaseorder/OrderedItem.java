package com.bizeff.procurement.models.purchaseorder;

import com.bizeff.procurement.models.supplymanagement.Inventory;

import java.math.BigDecimal;

public class OrderedItem {
    private Long id;
    private Inventory inventory;
    private int orderedQuantity;
    private BigDecimal totalPrice;

    public OrderedItem(){
    }
    public OrderedItem(Inventory inventory, int orderedQuantity){
        this.inventory = inventory;
        this.orderedQuantity = orderedQuantity;
        this.totalPrice = getTotalPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public BigDecimal getTotalPrice() {
        return inventory.getUnitPrice().multiply(BigDecimal.valueOf(orderedQuantity));
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    //toString
    @Override
    public String toString() {
        return "OrderedItem [inventory=" + inventory + ", orderedQuantity=" + orderedQuantity + ", totalPrice=" + totalPrice + "]";
    }

}
