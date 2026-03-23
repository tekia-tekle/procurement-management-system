package com.bizeff.procurement.persistences.entity.paymentreconciliation;

import com.bizeff.procurement.models.enums.DeliveryStatus;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.UserEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.SupplierEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_receipt")
public class DeliveryReceiptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receipt_Id",nullable = false,unique = true)
    private String receiptId;// Unique ID for the receipt
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id",nullable = false)
    private SupplierEntity supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    private PurchaseOrderEntity purchaseOrder;// Associated purchase order ID

    @OneToMany(mappedBy = "deliveryReceipt",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DeliveredItemEntity> receivedItems;
    @OneToMany(mappedBy = "deliveryReceipt",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PaymentReconciliationEntity> paymentReconciliationEntities = new ArrayList<>();
    @Column(name = "delivery_Date",nullable = false)
    private LocalDate deliveryDate;
    @Column(name = "delivery_Location",nullable = false)
    private String deliveryLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity receivedBy; // Person who confirmed delivery

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_Status",nullable = false)
    private DeliveryStatus deliveryStatus;    // Status of delivery (e.g., "Delivered", "Pending", "Partial")
    @Column(name = "delivery_notes",nullable = false)
    private String deliveryNotes;

    public DeliveryReceiptEntity(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    public PurchaseOrderEntity getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderEntity purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public List<DeliveredItemEntity> getReceivedItems() {
        return receivedItems;
    }

    public void setReceivedItems(List<DeliveredItemEntity> receivedItems) {
        this.receivedItems = receivedItems;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public UserEntity getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(UserEntity receivedBy) {
        this.receivedBy = receivedBy;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }

    public List<PaymentReconciliationEntity> getPaymentReconciliationEntities() {
        return paymentReconciliationEntities;
    }

    public void setPaymentReconciliationEntities(List<PaymentReconciliationEntity> paymentReconciliationEntities) {
        this.paymentReconciliationEntities = paymentReconciliationEntities;
    }
}

