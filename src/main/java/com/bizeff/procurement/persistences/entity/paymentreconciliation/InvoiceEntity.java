package com.bizeff.procurement.persistences.entity.paymentreconciliation;

import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.UserEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.SupplierEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Invoice")
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_Id",nullable = false,unique = true)
    private String invoiceId;
    @Column(name = "invoice_Date",nullable = false)
    private LocalDate invoiceDate;
    @Column(name = "due_Date",nullable = false)
    private LocalDate dueDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id",nullable = false)
    private SupplierEntity supplier;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_Id",nullable = false)
    private PurchaseOrderEntity purchaseOrder;

    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL,orphanRemoval = true)
    @Size(min = 1)
    private List<InvoicedItemEntity> invoiceItems = new ArrayList<>();
    @OneToMany(mappedBy = "invoice",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PaymentReconciliationEntity> paymentReconciliationEntities = new ArrayList<>();
    @PositiveOrZero
    @Column(name = "Discount",nullable = false)
    private BigDecimal discount;
    @PositiveOrZero
    @Column(name = "shipping_Cost",nullable = false)
    private BigDecimal shippingCost;

    @PositiveOrZero
    @Column(name = "total_Amount",nullable = false)
    private BigDecimal totalCosts;
    @PositiveOrZero
    @Column(name = "Taxes",nullable = false)
    private BigDecimal taxes;
    @Column(name = "Currency",nullable = false)
    private String currency;
    @Column(name = "payment_Date",nullable = false)
    private LocalDate paymentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;
    @Column(name = "description",nullable = false)
    private String description;
    public InvoiceEntity(){}

    public void addInvoicedItem(InvoicedItemEntity invoicedItem){
        invoiceItems.add(invoicedItem);
        invoicedItem.setInvoice(this);
    }
    public BigDecimal getSubtotal() {
        return invoiceItems.stream()
                .map(InvoicedItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalCosts() {
        return getSubtotal()
                .subtract(discount)
                .add(taxes)
                .add(shippingCost);
    }

    public void setTotalCosts(BigDecimal totalCosts) {
        this.totalCosts = totalCosts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    public List<InvoicedItemEntity> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoicedItemEntity> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public UserEntity getCreatedBy() {
        return user;
    }

    public void setCreatedBy(UserEntity user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
