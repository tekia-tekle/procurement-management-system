package com.bizeff.procurement.models.invoicepaymentreconciliation;

import com.bizeff.procurement.models.supplymanagement.Inventory;

import java.math.BigDecimal;

public class DeliveredItem {
    private Inventory inventory;
    private int deliveredQuantity;
    private BigDecimal totalPrice;

    public DeliveredItem(){}
    public DeliveredItem(Inventory inventory,int deliveredQuantity){
        this.inventory = inventory;
        this.deliveredQuantity = deliveredQuantity;
        this.totalPrice = getTotalPrice();
    }
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public BigDecimal getTotalPrice() {
        return inventory.getUnitPrice().multiply(BigDecimal.valueOf(deliveredQuantity));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
