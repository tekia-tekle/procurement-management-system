package com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation;

import java.math.BigDecimal;

public class NotifySupplierForPaymentApprovalOutPutDS {
    private String paymentId;
    private BigDecimal remainingAmount;
    private int remainingDate;
    private boolean isnotified;
    private String message;
    public NotifySupplierForPaymentApprovalOutPutDS(
            String paymentId,
            BigDecimal remainingAmount,
            int remainingDate,
            boolean isnotified,
            String message){
        this.paymentId = paymentId;
        this.remainingAmount = remainingAmount;
        this.remainingDate = remainingDate;
        this.isnotified = isnotified;
        this.message = message;
    }
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public int getRemainingDate() {
        return remainingDate;
    }

    public void setRemainingDate(int remainingDate) {
        this.remainingDate = remainingDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsnotified() {
        return isnotified;
    }

    public void setIsnotified(boolean isnotified) {
        this.isnotified = isnotified;
    }

    @Override
    public String toString() {
        return "NotifySupplierForPaymentApprovalOutPutDS{" +
                "paymentId='" + paymentId + '\'' +
                ", remainingAmount=" + remainingAmount +
                ", remainingDate=" + remainingDate +
                ", isnotified=" + isnotified +
                ", message='" + message + '\'' +
                '}';
    }
}
