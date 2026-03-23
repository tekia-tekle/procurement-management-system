package com.bizeff.procurement.models.invoicepaymentreconciliation;


import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.User;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private Long id;
    private String invoiceId;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private Supplier supplier;
    private PurchaseOrder purchaseOrder;
    private List<InvoicedItem> invoiceItems = new ArrayList<>();

    private BigDecimal discount;
    private BigDecimal shippingCost;
    private BigDecimal taxes;

    private BigDecimal totalCosts;
    private String currency;
    private LocalDate paymentDate;


    private User createdBy;
    private String description;

    public Invoice(){}

    public Invoice(LocalDate invoiceDate,
                   LocalDate dueDate,
                   Supplier supplier,
                   PurchaseOrder purchaseOrder,
                   List<InvoicedItem> invoiceItems,
                   BigDecimal discount,
                   BigDecimal shippingCost,
                   BigDecimal taxes,
                   String currency,
                   LocalDate paymentDate,
                   User createdBy,
                   String description)
    {

        this.invoiceId = IdGenerator.generateId("INV");
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.supplier = supplier;
        this.purchaseOrder = purchaseOrder;
        this.invoiceItems = invoiceItems;
        this.discount = discount != null ? discount : BigDecimal.ZERO;
        this.shippingCost = shippingCost != null ? shippingCost : BigDecimal.ZERO;
        this.taxes = taxes != null ? taxes : BigDecimal.ZERO;
        this.currency = currency;
        this.paymentDate = paymentDate;
        this.createdBy = createdBy;
        this.description = description;
    }

    public BigDecimal getSubtotal() {
        return invoiceItems.stream()
                .map(InvoicedItem::getTotalPrice)
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

    public void addInvoicedItem(InvoicedItem invoicedItem){
        invoiceItems.add(invoicedItem);
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

    public List<InvoicedItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoicedItem> invoiceItems) {
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", dueDate=" + dueDate +
                ", supplier=" + supplier +
                ", purchaseOrder=" + purchaseOrder +
                ", invoiceItems=" + invoiceItems +
                ", discount=" + discount +
                ", shippingCost=" + shippingCost +
                ", taxes=" + taxes +
                ", currency='" + currency + '\'' +
                ", paymentDate=" + paymentDate +
                ", createdBy=" + createdBy +
                ", description='" + description + '\'' +
                '}';
    }
}
