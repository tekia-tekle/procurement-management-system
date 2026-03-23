package com.bizeff.procurement.models.invoicepaymentreconciliation;


import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.models.enums.ReconciliationStatus;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentReconciliation {

    private String paymentId;
    private Invoice invoice;
    private PurchaseOrder purchaseOrder;
    private DeliveryReceipt deliveryReceipt;
    private BigDecimal invoiceAmount;
    private BigDecimal expectedAmount;
    private BigDecimal actualPaidAmount;
    private BigDecimal discrepancyAmount;
    private String currency;
    private LocalDate paymentDate;
    private LocalDate reconciliationDate;
    private ReconciliationStatus reconciliationStatus;

    public PaymentReconciliation(){}
    public PaymentReconciliation(
            Invoice invoice,
            PurchaseOrder purchaseOrder,
            DeliveryReceipt deliveryReceipt,
            BigDecimal expectedAmount,
            BigDecimal actualPaidAmount,
            String currency,
            LocalDate paymentDate,
            LocalDate reconciliationDate)
    {
        this.paymentId = IdGenerator.generateId("PAY");
        this.invoice = invoice;
        this.purchaseOrder = purchaseOrder;
        this.deliveryReceipt = deliveryReceipt;
        this.invoiceAmount = invoice.getTotalCosts();
        this.expectedAmount = expectedAmount;
        this.actualPaidAmount = actualPaidAmount != null ? actualPaidAmount : BigDecimal.ZERO;
        this.discrepancyAmount = expectedAmount.subtract(this.actualPaidAmount);
        this.currency = currency;
        this.paymentDate = paymentDate;
        this.reconciliationDate = reconciliationDate;
        this.reconciliationStatus = determineStatus();
    }


    public BigDecimal getDiscrepancyAmount() {
        return discrepancyAmount.abs();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public DeliveryReceipt getDeliveryReceipt() {
        return deliveryReceipt;
    }

    public void setDeliveryReceipt(DeliveryReceipt deliveryReceipt) {
        this.deliveryReceipt = deliveryReceipt;
    }

    public BigDecimal getExpectedAmount() {
        return expectedAmount;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setExpectedAmount(BigDecimal expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getActualPaidAmount() {
        return actualPaidAmount;
    }

    public void setActualPaidAmount(BigDecimal actualPaidAmount) {
        this.actualPaidAmount = actualPaidAmount;
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
    private ReconciliationStatus determineStatus() {
        if (discrepancyAmount.compareTo(BigDecimal.ZERO) == 0) return ReconciliationStatus.MATCHED;
        if (discrepancyAmount.compareTo(BigDecimal.ZERO) > 0) return ReconciliationStatus.UNDERPAID;
        return ReconciliationStatus.OVERPAID;
    }

    @Override
    public String toString() {
        return "PaymentReconciliation{" +
                "paymentId='" + paymentId + '\'' +
                ", invoice=" + invoice +
                ", purchaseOrder=" + purchaseOrder +
                ", deliveryReceipt=" + deliveryReceipt +
                ", invoiceAmount=" + invoiceAmount +
                ", expectedAmount=" + expectedAmount +
                ", actualPaidAmount=" + actualPaidAmount +
                ", discrepancyAmount=" + discrepancyAmount +
                ", currency='" + currency + '\'' +
                ", paymentDate=" + paymentDate +
                ", reconciliationDate=" + reconciliationDate +
                ", reconciliationStatus=" + reconciliationStatus +
                '}';
    }
}
