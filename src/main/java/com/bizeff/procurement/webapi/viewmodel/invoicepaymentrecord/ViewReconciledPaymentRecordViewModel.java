package com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord;

public class ViewReconciledPaymentRecordViewModel {
    private String paymentId;
    private String invoiceId;
    private String orderId;
    private String supplierId;
    private String receiptId;
    private String expectedAmount;
    private String actualPaidAmount;
    private String discrepancyAmount;
    private String paymentDate;
    private String reconciliationDate;
    private String status;

    public ViewReconciledPaymentRecordViewModel(){}

    public ViewReconciledPaymentRecordViewModel(String paymentId,
                                                String invoiceId,
                                                String orderId,
                                                String supplierId,
                                                String receiptId,
                                                String expectedAmount,
                                                String actualPaidAmount,
                                                String discrepancyAmount,
                                                String paymentDate,
                                                String reconciliationDate,
                                                String status)
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

    public String getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(String expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public String getActualPaidAmount() {
        return actualPaidAmount;
    }

    public void setActualPaidAmount(String actualPaidAmount) {
        this.actualPaidAmount = actualPaidAmount;
    }

    public String getDiscrepancyAmount() {
        return discrepancyAmount;
    }

    public void setDiscrepancyAmount(String discrepancyAmount) {
        this.discrepancyAmount = discrepancyAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(String reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ViewReconciledPaymentRecordViewModel{" +
                "paymentId='" + paymentId + '\'' +
                ", invoiceId='" + invoiceId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", receiptId='" + receiptId + '\'' +
                ", expectedAmount='" + expectedAmount + '\'' +
                ", actualPaidAmount='" + actualPaidAmount + '\'' +
                ", discrepancyAmount='" + discrepancyAmount + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", reconciliationDate='" + reconciliationDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}