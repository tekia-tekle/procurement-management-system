package com.bizeff.procurement.persistences.entity.suppliermanagement;

import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveredItemEntity;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoicedItemEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.RequestedItemEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventory",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"item_id", "supplier_id"}))
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "quantity_available", nullable = false)
    @PositiveOrZero
    private int quantityAvailable;

    @Column(name = "unit_price", nullable = false)
    @PositiveOrZero
    private BigDecimal unitPrice;

    @Column(name = "item_category")
    private String itemCategory;

    @Column(name = "expiry_date")
    @FutureOrPresent
    private LocalDate expiryDate;

    @Column(name = "specification", length = 1000)
    private String specification;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestedItemEntity> requestedItemEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;
    @OneToMany(mappedBy = "inventory",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<InvoicedItemEntity> invoicedItemEntities = new ArrayList<>();

    @OneToMany(mappedBy = "inventory",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DeliveredItemEntity>deliveredItemEntities = new ArrayList<>();


    // Constructors
    public InventoryEntity() {}

    // Getters and Setters
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

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    public List<RequestedItemEntity> getRequestedItemEntity() {
        return requestedItemEntity;
    }

    public void setRequestedItemEntity(List<RequestedItemEntity> requestedItemEntity) {
        this.requestedItemEntity = requestedItemEntity;
    }

    @Transient
    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantityAvailable));
    }

    public List<InvoicedItemEntity> getInvoicedItemEntities() {
        return invoicedItemEntities;
    }

    public void setInvoicedItemEntities(List<InvoicedItemEntity> invoicedItemEntities) {
        this.invoicedItemEntities = invoicedItemEntities;
    }

    public List<DeliveredItemEntity> getDeliveredItemEntities() {
        return deliveredItemEntities;
    }

    public void setDeliveredItemEntities(List<DeliveredItemEntity> deliveredItemEntities) {
        this.deliveredItemEntities = deliveredItemEntities;
    }

    @Override
    public String toString() {
        return "InventoryEntity{" +
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
