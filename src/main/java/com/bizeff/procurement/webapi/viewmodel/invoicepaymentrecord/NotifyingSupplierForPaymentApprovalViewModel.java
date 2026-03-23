package com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord;

public class NotifyingSupplierForPaymentApprovalViewModel {
    private String paymentId;
    private String remainingAmount;
    private Long remainingDate;
    private boolean isnotified;
    private String message;

    // Default constructor for creating an empty view model
    public NotifyingSupplierForPaymentApprovalViewModel(){}

    // Constructor for creating a view model with all fields
    public NotifyingSupplierForPaymentApprovalViewModel(String paymentId,
                                                        String remainingAmount,
                                                        Long remainingDate,
                                                        boolean isnotified,
                                                        String message) {
        this.paymentId = paymentId;
        this.remainingAmount = remainingAmount;
        this.remainingDate = remainingDate;
        this.isnotified = isnotified;
        this.message = message;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public String getRemainingAmount() {
        return remainingAmount;
    }
    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
    public Long getRemainingDate() {
        return remainingDate;
    }
    public void setRemainingDate(Long remainingDate) {
        this.remainingDate = remainingDate;
    }
    public boolean isIsnotified() {
        return isnotified;
    }
    public void setIsnotified(boolean isnotified) {
        this.isnotified = isnotified;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "NotifyingSupplierForPaymentApprovalViewModel{" +
                "paymentId='" + paymentId + '\'' +
                ", remainingAmount='" + remainingAmount + '\'' +
                ", remainingDate=" + remainingDate +
                ", isnotified=" + isnotified +
                ", message='" + message + '\'' +
                '}';
    }
}

