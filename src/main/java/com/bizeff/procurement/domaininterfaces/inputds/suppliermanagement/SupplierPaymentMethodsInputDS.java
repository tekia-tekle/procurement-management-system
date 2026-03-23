package com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement;

import java.math.BigDecimal;
import java.util.List;

public class SupplierPaymentMethodsInputDS {
    private List<String> paymentMethodsAccepted;
    private String preferredPaymentMethod;
    private String accountHolderName;
    private String accountHolderPhoneNumber;
    private String bankName;
    private String bankAccountNumber;
    private String currencyType;
    private String paymentTerms;
    private BigDecimal creditLimit;
    public SupplierPaymentMethodsInputDS( List<String> paymentMethodsAccepted,
                                          String preferredPaymentMethod,
                                          String accountHolderName,
                                          String accountHolderPhoneNumber,
                                          String bankName,
                                          String bankAccountNumber,
                                          String currencyType,
                                          String paymentTerms,
                                          BigDecimal creditLimit
    ) {
        this.paymentMethodsAccepted = paymentMethodsAccepted;
        this.preferredPaymentMethod = preferredPaymentMethod;
        this.accountHolderName = accountHolderName;
        this.accountHolderPhoneNumber = accountHolderPhoneNumber;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.currencyType = currencyType;
        this.paymentTerms = paymentTerms;
        this.creditLimit = creditLimit;
    }

    public List<String> getPaymentMethodsAccepted() {
        return paymentMethodsAccepted;
    }

    public void setPaymentMethodsAccepted(List<String> paymentMethodsAccepted) {
        this.paymentMethodsAccepted = paymentMethodsAccepted;
    }

    public String getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }

    public void setPreferredPaymentMethod(String preferredPaymentMethod) {
        this.preferredPaymentMethod = preferredPaymentMethod;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountHolderPhoneNumber() {
        return accountHolderPhoneNumber;
    }

    public void setAccountHolderPhoneNumber(String accountHolderPhoneNumber) {
        this.accountHolderPhoneNumber = accountHolderPhoneNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public String toString() {
        return "SupplierPaymentMethodsInputDS{" +
                "paymentMethodsAccepted=" + paymentMethodsAccepted +
                ", preferredPaymentMethod='" + preferredPaymentMethod + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", accountHolderPhoneNumber='" + accountHolderPhoneNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", paymentTerms='" + paymentTerms + '\'' +
                ", creditLimit=" + creditLimit +
                '}';
    }
}
