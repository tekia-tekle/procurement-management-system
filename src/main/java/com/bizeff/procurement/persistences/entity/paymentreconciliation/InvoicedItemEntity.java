package com.bizeff.procurement.persistences.entity.paymentreconciliation;

import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "invoiced_Item")
public class InvoicedItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id",nullable = false)
    private InventoryEntity inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private InvoiceEntity invoice;
    private int invoicedQuantity;
    private BigDecimal totalPrice;

    public InvoicedItemEntity(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryEntity getInventory() {
        return inventory;
    }

    public void setInventory(InventoryEntity inventory) {
        this.inventory = inventory;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public int getInvoicedQuantity() {
        return invoicedQuantity;
    }

    public void setInvoicedQuantity(int invoicedQuantity) {
        this.invoicedQuantity = invoicedQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
