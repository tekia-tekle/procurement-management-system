package com.bizeff.procurement.persistences.entity.paymentreconciliation;

import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Entity
@Table(name = "delivered_Item")
public class DeliveredItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id",nullable = false)
    private InventoryEntity inventory;
    @Column(name = "delivered_quantity",nullable = false)
    @PositiveOrZero
    private int deliveredQuantity;

    @Column(name = "total_price",nullable = false)
    private BigDecimal totalPrice = BigDecimal.ZERO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private DeliveryReceiptEntity deliveryReceipt;

    public DeliveredItemEntity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInventory(InventoryEntity inventory) {
        this.inventory = inventory;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public DeliveryReceiptEntity getDeliveryReceipt() {
        return deliveryReceipt;
    }
    public void setDeliveryReceipt(DeliveryReceiptEntity deliveryReceipt) {
        this.deliveryReceipt = deliveryReceipt;
    }
    public InventoryEntity getInventory() {
        return inventory;
    }

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public BigDecimal getTotalPrice() {
        return inventory.getUnitPrice().multiply(BigDecimal.valueOf(deliveredQuantity));
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
