package com.bizeff.procurement.persistences.entity.purchaseorder;

import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.persistences.entity.contracts.ContractEntity;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveryReceiptEntity;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoiceEntity;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.PaymentReconciliationEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.DepartmentEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.PurchaseRequisitionEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.RequestedItemEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.SupplierEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false,unique = true)
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity departmentEntity;

    @OneToMany(mappedBy = "purchaseOrderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(min = 1)
    private List<PurchaseRequisitionEntity> purchaseRequisitionEntities = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderedItemEntity> orderedItems = new ArrayList<>();
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplierEntity;

    @Column(name = "shipping_method",nullable = false)
    private String shippingMethod;

    @Column(name = "delivery_date",nullable = false)
    private LocalDate deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PurchaseOrderStatus purchaseOrderStatus;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @Column(name = "is_shipped", nullable = false)
    private boolean isShipped;

    @Column(name = "last_updated_date", nullable = false)
    private LocalDate lastUpdatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_Id")
    private ContractEntity contract;
    @OneToOne(mappedBy = "purchaseOrder",cascade = CascadeType.ALL,orphanRemoval = true)
    private InvoiceEntity invoiceEntities;
    @OneToMany(mappedBy = "purchaseOrder",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DeliveryReceiptEntity>deliveryReceiptEntities;

    @OneToMany(mappedBy = "purchaseOrder",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PaymentReconciliationEntity> paymentReconciliationEntities = new ArrayList<>();

    public BigDecimal totalCost;
    public PurchaseOrderEntity() {}

    public void addPurchaseRequisition(PurchaseRequisitionEntity purchaseRequisitionEntity) {
        if (purchaseRequisitionEntity != null && !purchaseRequisitionEntities.contains(purchaseRequisitionEntity)) {
            this.purchaseRequisitionEntities.add(purchaseRequisitionEntity);
            purchaseRequisitionEntity.setPurchaseOrderEntity(this);
        } else {
            throw new IllegalArgumentException("Purchase Requisition cannot be null or already exists in the list.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public DepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(DepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    public List<PurchaseRequisitionEntity> getPurchaseRequisitionEntities() {
        return purchaseRequisitionEntities;
    }

    public void setOrderedItems(List<OrderedItemEntity> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public void setPurchaseRequisitionEntities(List<PurchaseRequisitionEntity> purchaseRequisitionEntities) {
        this.purchaseRequisitionEntities = purchaseRequisitionEntities;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public SupplierEntity getSupplierEntity() {
        return supplierEntity;
    }

    public void setSupplierEntity(SupplierEntity supplierEntity) {
        this.supplierEntity = supplierEntity;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public PurchaseOrderStatus getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public void setPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isShipped() {
        return isShipped;
    }

    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Transient
    public BigDecimal getTotalCost() {
        return purchaseRequisitionEntities.stream()
                .map(PurchaseRequisitionEntity::getTotalEstimatedCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public void addRequisition(PurchaseRequisitionEntity req) {
        if (req.getPurchaseOrderEntity() != null && !req.getPurchaseOrderEntity().equals(this)) {
            throw new IllegalStateException("Requisition already belongs to another PO");
        }
        purchaseRequisitionEntities.add(req);
        req.setPurchaseOrderEntity(this);
    }

    public void addOrderedItem(OrderedItemEntity orderedItemEntity){
        orderedItems.add(orderedItemEntity);
        orderedItemEntity.setPurchaseOrder(this);
    }
    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }

    public InvoiceEntity getInvoiceEntities() {
        return invoiceEntities;
    }

    public void setInvoiceEntities(InvoiceEntity invoiceEntities) {
        this.invoiceEntities = invoiceEntities;
    }

    public List<DeliveryReceiptEntity> getDeliveryReceiptEntities() {
        return deliveryReceiptEntities;
    }

    public void setDeliveryReceiptEntities(List<DeliveryReceiptEntity> deliveryReceiptEntities) {
        this.deliveryReceiptEntities = deliveryReceiptEntities;
    }

    public List<PaymentReconciliationEntity> getPaymentReconciliationEntities() {
        return paymentReconciliationEntities;
    }

    public void setPaymentReconciliationEntities(List<PaymentReconciliationEntity> paymentReconciliationEntities) {
        this.paymentReconciliationEntities = paymentReconciliationEntities;
    }
    public  List<OrderedItemEntity> getOrderedItems(){
        List<OrderedItemEntity> orderedItemEntityList = new ArrayList<>();
        for (PurchaseRequisitionEntity purchaseRequisitionEntity: purchaseRequisitionEntities){
            List<RequestedItemEntity> requestedItemEntities = purchaseRequisitionEntity.getItems();
            for (RequestedItemEntity requestedItem: requestedItemEntities){
                OrderedItemEntity orderedItemEntity = new OrderedItemEntity();
                orderedItemEntity.setInventory(requestedItem.getInventory());
                orderedItemEntity.setItemId(requestedItem.getItemId());
                orderedItemEntity.setItemName(requestedItem.getItemName());
                orderedItemEntity.setItemCategory(requestedItem.getItemCategory());
                orderedItemEntity.setQuantityAvailable(requestedItem.getQuantityAvailable());
                orderedItemEntity.setOrderedQuantity(requestedItem.getRequestedQuantity());
                orderedItemEntity.setUnitPrice(requestedItem.getUnitPrice());
                orderedItemEntity.setTotalPrice(requestedItem.getTotalPrice());
                orderedItemEntity.setExpiryDate(requestedItem.getExpiryDate());
                orderedItemEntity.setSpecification(requestedItem.getSpecification());

                orderedItemEntityList.add(orderedItemEntity);
            }
        }
        return orderedItemEntityList;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}