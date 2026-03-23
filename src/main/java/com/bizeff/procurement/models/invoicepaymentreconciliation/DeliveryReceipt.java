package com.bizeff.procurement.models.invoicepaymentreconciliation;

import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.models.enums.DeliveryStatus;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.User;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class DeliveryReceipt {
    private Long id;
    private String receiptId;// Unique ID for the receipt
    private Supplier supplier;
    private PurchaseOrder purchaseOrder;// Associated purchase order ID
    private List<DeliveredItem> receivedItems;
    private LocalDate deliveryDate;
    private String deliveryLocation;

    private User receivedBy; // Person who confirmed delivery

    private DeliveryStatus deliveryStatus;    // Status of delivery (e.g., "Delivered", "Pending", "Partial")
    private String deliveryNotes;

    /**Constructor.*/
    public DeliveryReceipt(){}
    public DeliveryReceipt(
            Supplier supplier,
            PurchaseOrder purchaseOrder,
            List<DeliveredItem> receivedItems,
            LocalDate deliveryDate,
            String deliveryLocation,
            User receivedBy,
            String deliveryNotes)
    {
        this.receiptId = IdGenerator.generateId("REP");
        this.supplier = supplier;
        this.purchaseOrder = purchaseOrder;
        this.receivedItems = receivedItems;
        this.deliveryDate = deliveryDate;
        this.deliveryLocation = deliveryLocation;
        this.receivedBy = receivedBy;
        this.deliveryStatus = DeliveryStatus.PENDING;
        this.deliveryNotes = deliveryNotes;
    }


    /** Getter and Setter. */

    public BigDecimal getTotalCost() {
        return receivedItems.stream()
                .map(DeliveredItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public List<DeliveredItem> getReceivedItems() {
        return receivedItems;
    }

    public void setReceivedItems(List<DeliveredItem> receivedItems) {
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

    public User getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(User receivedBy) {
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

    @Override
    public String toString() {
        return "DeliveryReceipt{" +
                "receiptId='" + receiptId + '\'' +
                ", supplier=" + supplier +
                ", purchaseOrder=" + purchaseOrder +
                ", receivedItems=" + receivedItems +
                ",totalCost=" + getTotalCost() +
                ", deliveryDate=" + deliveryDate +
                ", deliveryLocation='" + deliveryLocation + '\'' +
                ", receivedBy=" + receivedBy +
                ", deliveryStatus=" + deliveryStatus +
                ", deliveryNotes='" + deliveryNotes + '\'' +
                '}';
    }
}
