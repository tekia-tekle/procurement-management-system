package com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation;

import com.bizeff.procurement.models.enums.ReconciliationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ViewReconciledPaymentRecordOutPutDS {
    private String paymentId;
    private String invoiceId;
    private String orderId;
    private String supplierId;
    private String receiptId;
    private BigDecimal expectedAmount;
    private BigDecimal actualPaidAmount;
    private BigDecimal discrepancyAmount;
    private LocalDate paymentDate;
    private LocalDate reconciliationDate;
    private ReconciliationStatus status;


    public ViewReconciledPaymentRecordOutPutDS(String paymentId,
                                               String invoiceId,
                                               String orderId,
                                               String supplierId,
                                               String receiptId,
                                               BigDecimal expectedAmount,
                                               BigDecimal actualPaidAmount,
                                               BigDecimal discrepancyAmount,
                                               LocalDate paymentDate,
                                               LocalDate reconciliationDate,
                                               ReconciliationStatus status)
    {
        this.paymentId = paymentId;
        this.invoiceId = invoiceId;
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.receiptId = receiptId;
        this.expectedAmount = expectedAmount;
        this.actualPaidAmount = actualPaidAmount;
        this.discrepancyAmount = discrepancyAmount;
        this.paymentDate = paymentDate;
        this.reconciliationDate = reconciliationDate;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
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

    public ReconciliationStatus getStatus() {
        return status;
    }

    public void setStatus(ReconciliationStatus status) {
        this.status = status;
    }
}
