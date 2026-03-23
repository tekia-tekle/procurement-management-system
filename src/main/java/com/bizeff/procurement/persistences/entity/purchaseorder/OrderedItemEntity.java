package com.bizeff.procurement.persistences.entity.purchaseorder;

import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "oredered_item")
public class OrderedItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id",nullable = false)
    private InventoryEntity inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    private PurchaseOrderEntity purchaseOrder;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "quantity_available", nullable = false)
    private int quantityAvailable;
    @PositiveOrZero
    @Column(name = "ordered_quantity",nullable = false)
    private int orderedQuantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    private BigDecimal totalprice;
    @Column(name = "item_category",nullable = false)
    private String itemCategory;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "specification", length = 1000)
    private String specification;

    // Constructors
    public OrderedItemEntity() {}

    // Getters and Setters
    @Transient
    public BigDecimal getTotalPrice(){
        if (inventory != null && inventory.getUnitPrice() != null) {
            this.totalprice = inventory.getUnitPrice()
                    .multiply(BigDecimal.valueOf(orderedQuantity));
        }
        return totalprice;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public void setTotalPrice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public InventoryEntity getInventory() {
        return inventory;
    }

    public void setInventory(InventoryEntity inventory) {
        this.inventory = inventory;
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

    public PurchaseOrderEntity getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderEntity purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
