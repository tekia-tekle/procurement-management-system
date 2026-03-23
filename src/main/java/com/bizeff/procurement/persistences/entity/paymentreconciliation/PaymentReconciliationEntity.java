package com.bizeff.procurement.persistences.entity.paymentreconciliation;

import com.bizeff.procurement.models.enums.ReconciliationStatus;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment_reconciliation")
public class PaymentReconciliationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "payment_id", nullable = false, unique = true)
    private String paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id",nullable = false)
    private InvoiceEntity invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    private PurchaseOrderEntity purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id",nullable = false)
    private DeliveryReceiptEntity deliveryReceipt;

    @PositiveOrZero
    @Column(name = "invoice_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal invoiceAmount;

    @PositiveOrZero
    @Column(name = "expected_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal expectedAmount;
    @PositiveOrZero
    @Column(name = "actual_paid_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal actualPaidAmount;
    @Column(name = "discrepancy_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal discrepancyAmount;
    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;
    @Column(name = "reconciliation_date", nullable = false)
    private LocalDate reconciliationDate;

    @Enumerated(EnumType.STRING)
    private ReconciliationStatus reconciliationStatus;

    public PaymentReconciliationEntity(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }

    public PurchaseOrderEntity getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrderEntity purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public DeliveryReceiptEntity getDeliveryReceipt() {
        return deliveryReceipt;
    }

    public void setDeliveryReceipt(DeliveryReceiptEntity deliveryReceipt) {
        this.deliveryReceipt = deliveryReceipt;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(BigDecimal expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public BigDecimal getActualPaidAmount() {
        return actualPaidAmount;
    }

    public void setActualPaidAmount(BigDecimal actualPaidAmount) {
        this.actualPaidAmount = actualPaidAmount;
    }

    public BigDecimal getDiscrepancyAmount() {
        return discrepancyAmount;
    }

    public void setDiscrepancyAmount(BigDecimal discrepancyAmount) {
        this.discrepancyAmount = discrepancyAmount;
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

    public LocalDate getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(LocalDate reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }

    public ReconciliationStatus getReconciliationStatus() {
        return reconciliationStatus;
    }

    public void setReconciliationStatus(ReconciliationStatus reconciliationStatus) {
        this.reconciliationStatus = reconciliationStatus;
    }
}
