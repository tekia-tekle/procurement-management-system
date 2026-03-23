package com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation;

import com.bizeff.procurement.models.enums.ReconciliationStatus;

import java.time.LocalDate;

public class ReconclieInvoiceOutputDS {
    private String paymentId;
    private ReconciliationStatus status;
    private LocalDate reconciliationDate;
    private String messages;

    public ReconclieInvoiceOutputDS(String paymentId,
                                    ReconciliationStatus status,
                                    LocalDate reconciliationDate,
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

    public ReconciliationStatus getStatus() {
        return status;
    }

    public void setStatus(ReconciliationStatus status) {
        this.status = status;
    }

    public LocalDate getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(LocalDate reconciliationDate) {
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
        return "ReconclieInvoiceOutputDS{" +
                "paymentId='" + paymentId + '\'' +
                ", status='" + status + '\'' +
                ", messages='" + messages + '\'' +
                '}';
    }
}