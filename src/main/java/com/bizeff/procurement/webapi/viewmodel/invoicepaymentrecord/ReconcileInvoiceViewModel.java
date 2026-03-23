package com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord;

public class ReconcileInvoiceViewModel {

    private String paymentId;
    private String status;
    private String reconciliationDate;
    private String messages;
    public ReconcileInvoiceViewModel(){}

    public ReconcileInvoiceViewModel(String paymentId,
                                     String status,
                                     String reconciliationDate,
                                     String messages) {
        this.paymentId = paymentId;
        this.status = status;
        this.reconciliationDate = reconciliationDate;
        this.messages = messages;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(String reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ReconcileInvoiceViewModel{" +
                "paymentId='" + paymentId + '\'' +
                ", status='" + status + '\'' +
                ", reconciliationDate='" + reconciliationDate + '\'' +
                ", messages='" + messages + '\'' +
                '}';
    }
}
