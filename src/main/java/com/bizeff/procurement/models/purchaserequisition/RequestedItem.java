package com.bizeff.procurement.models.purchaserequisition;

import com.bizeff.procurement.models.supplymanagement.Inventory;

import java.math.BigDecimal;

public class RequestedItem {
    private Inventory inventory;
    private int requestedQuantity;
    private BigDecimal totalPrice;

    public RequestedItem(){
    }
    public RequestedItem(Inventory inventory, int requestedQuantity){
        this.inventory = inventory;
        this.requestedQuantity = requestedQuantity;
        this.totalPrice = getTotalPrice();
    }
    public Inventory getInventory() {
        return inventory;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public BigDecimal getTotalPrice() {
        return inventory.getUnitPrice().multiply(BigDecimal.valueOf(requestedQuantity));
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "RequestedItem{" +
                "inventory=" + inventory +
                ", requestedQuantity=" + requestedQuantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
