package com.bizeff.procurement.persistences.entity.purchaserequisition;

import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "requested_item")
public class RequestedItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requested_quantity", nullable = false)
    @PositiveOrZero
    private int requestedQuantity;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private InventoryEntity inventory;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "quantity_available", nullable = false)
    private int quantityAvailable;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "item_category",nullable = false)
    private String itemCategory;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "specification", length = 1000)
    private String specification;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_requisition_id", nullable = false)
    private PurchaseRequisitionEntity purchaseRequisition;

    // Constructors
    public RequestedItemEntity() {}

    // Getters and Setters
    @Transient
    public BigDecimal getTotalPrice(){
        if (inventory != null && inventory.getUnitPrice() != null) {
            this.totalPrice = inventory.getUnitPrice()
                    .multiply(BigDecimal.valueOf(requestedQuantity));
        }
        return totalPrice;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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

    public PurchaseRequisitionEntity getPurchaseRequisition() {
        return purchaseRequisition;
    }

    public void setPurchaseRequisition(PurchaseRequisitionEntity purchaseRequisition) {
        this.purchaseRequisition = purchaseRequisition;
    }

    @Override
    public String toString() {
        return "RequestedItemEntity{" +
                "id=" + id +
                ", inventory=" + (inventory != null ? inventory.getItemId() : null) +
                ", requestedQuantity=" + requestedQuantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}