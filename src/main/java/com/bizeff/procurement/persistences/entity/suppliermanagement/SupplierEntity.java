package com.bizeff.procurement.persistences.entity.suppliermanagement;

import com.bizeff.procurement.persistences.entity.contracts.ContractEntity;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveryReceiptEntity;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoiceEntity;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import com.bizeff.procurement.persistences.entity.supplierPerformance.SupplierPerformanceEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "supplier")
public class SupplierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "supplier_id", nullable = false, unique = true)
    private String supplierId; // Set manually using IdGenerator

    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    @Column(name = "supplier_category",nullable = false)
    private String supplierCategory;

    @Column(name = "tax_id_number",nullable = false,unique = true)
    private String taxIdentificationNumber;

    @Column(name = "registration_number",nullable = false,unique = true)
    private String registrationNumber;

    @Embedded
    private EmbeddedContactDetails supplierContactDetail;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "supplier_payment_method",
            joinColumns = @JoinColumn(name = "supplier_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id")
    )
    private List<PaymentMethodEntity> paymentMethodEntities = new ArrayList<>();

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryEntity> inventoryItems = new ArrayList<>();

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    @OneToMany(mappedBy = "supplierEntity",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PurchaseOrderEntity> purchaseOrder = new ArrayList<>();
    @OneToMany(mappedBy = "supplierEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractEntity> contracts;


    @OneToMany(mappedBy = "supplier",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<InvoiceEntity> invoiceEntities;
    @OneToMany(mappedBy = "supplier",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DeliveryReceiptEntity> deliveryReceiptEntities;

    @OneToMany(mappedBy = "supplier",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<SupplierPerformanceEntity> supplierPerformanceEntities = new ArrayList<>();
    // Constructors
    public SupplierEntity() {}
    /** Getters and Setters. */
    public void addPaymentMethod(PaymentMethodEntity paymentMethod) {
        if (paymentMethod != null && !paymentMethodEntities.contains(paymentMethod)) {
            paymentMethodEntities.add(paymentMethod);
            paymentMethod.getSuppliers().add(this);
        }
    }
    public void addInventory(InventoryEntity inventoryEntity){
        if(inventoryEntity != null && !inventoryItems.contains(inventoryEntity)){
            this.inventoryItems.add(inventoryEntity);
            inventoryEntity.setSupplier(this); // Set the supplier reference in the inventory entity
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getSupplierCategory() { return supplierCategory; }
    public void setSupplierCategory(String supplierCategory) { this.supplierCategory = supplierCategory; }

    public String getTaxIdentificationNumber() { return taxIdentificationNumber; }
    public void setTaxIdentificationNumber(String taxIdentificationNumber) { this.taxIdentificationNumber = taxIdentificationNumber; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public EmbeddedContactDetails getSupplierContactDetail() { return supplierContactDetail; }
    public void setSupplierContactDetail(EmbeddedContactDetails supplierContactDetail) { this.supplierContactDetail = supplierContactDetail; }

    public List<PaymentMethodEntity> getPaymentMethodEntities() {
        return paymentMethodEntities;
    }

    public void setPaymentMethodEntities(List<PaymentMethodEntity> paymentMethodEntities) {
        this.paymentMethodEntities = paymentMethodEntities;
    }
    public List<InventoryEntity> getInventoryItems() { return inventoryItems; }
    public void setInventoryItems(List<InventoryEntity> inventoryItems) { this.inventoryItems = inventoryItems; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public LocalDate getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDate lastUpdated) { this.lastUpdated = lastUpdated; }

    public List<ContractEntity> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractEntity> contracts) {
        this.contracts = contracts;
    }

    public List<PurchaseOrderEntity> getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(List<PurchaseOrderEntity> purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public List<InvoiceEntity> getInvoiceEntities() {
        return invoiceEntities;
    }

    public void setInvoiceEntities(List<InvoiceEntity> invoiceEntities) {
        this.invoiceEntities = invoiceEntities;
    }

    public List<DeliveryReceiptEntity> getDeliveryReceiptEntities() {
        return deliveryReceiptEntities;
    }

    public void setDeliveryReceiptEntities(List<DeliveryReceiptEntity> deliveryReceiptEntities) {
        this.deliveryReceiptEntities = deliveryReceiptEntities;
    }

    public List<SupplierPerformanceEntity> getSupplierPerformanceEntities() {
        return supplierPerformanceEntities;
    }

    public void setSupplierPerformanceEntities(List<SupplierPerformanceEntity> supplierPerformanceEntities) {
        this.supplierPerformanceEntities = supplierPerformanceEntities;
    }
}
