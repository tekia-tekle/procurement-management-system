package com.bizeff.procurement.domaininterfaces.inputds.invoicepaymentreconciliation;

import java.time.LocalDate;

public class ReconcileInvoiceInputDS {
    private AccountantContactDetails officerDetails;
    private String invoiceId;
    private String orderId;
    private String receiptId;
    private LocalDate reconciliationDate;
    public ReconcileInvoiceInputDS(AccountantContactDetails officerDetails,
                                   String invoiceId,
                                   String orderId,
                                   String receiptId,
                                   LocalDate reconciliationDate) {
        this.officerDetails = officerDetails;
        this.invoiceId = invoiceId;
        this.orderId = orderId;
        this.receiptId = receiptId;
        this.reconciliationDate = reconciliationDate;
    }
    public AccountantContactDetails getOfficerDetails() {
        return officerDetails;
    }

    public void setOfficerDetails(AccountantContactDetails officerDetails) {
        this.officerDetails = officerDetails;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getOrderId() { return orderId; }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReceiptId() { return receiptId; }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public LocalDate getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(LocalDate reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }

    @Override
    public String toString() {
        return "ReconcileInvoiceInputDS{" +
                "officerDetails=" + officerDetails +
                ", invoiceId='" + invoiceId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", receiptId='" + receiptId + '\'' +
                ", reconciliationDate=" + reconciliationDate +
                '}';
    }
}