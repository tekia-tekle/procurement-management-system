package com.bizeff.procurement.models.invoicepaymentreconciliation;

import com.bizeff.procurement.models.supplymanagement.Inventory;

import java.math.BigDecimal;

public class InvoicedItem {
    private Inventory inventory;
    private int invoicedQuantity;
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public InvoicedItem(){}
    public InvoicedItem(Inventory inventory,int invoicedQuantity){
        this.inventory = inventory;
        this.invoicedQuantity = invoicedQuantity;
        this.totalPrice = getTotalPrice();
    }
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getInvoicedQuantity() {
        return invoicedQuantity;
    }

    public void setInvoicedQuantity(int invoicedQuantity) {
        this.invoicedQuantity = invoicedQuantity;
    }

    public BigDecimal getTotalPrice() {
        return inventory.getUnitPrice().multiply(BigDecimal.valueOf(invoicedQuantity));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
