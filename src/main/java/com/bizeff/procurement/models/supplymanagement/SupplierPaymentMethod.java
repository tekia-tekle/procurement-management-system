package com.bizeff.procurement.models.supplymanagement;

import com.bizeff.procurement.models.enums.PaymentMethodType;
import com.bizeff.procurement.models.enums.PaymentTerms;

import java.math.BigDecimal;
import java.util.List;
public class SupplierPaymentMethod {
    private String bankName;
    private String bankAccountNumber;
    private String accountHolderName;
    private String accountHolderPhoneNumber; // used for MOBILE_MONEY
    private List<PaymentMethodType> paymentMethods; // Credit Card, Bank Transfer, PayPal, etc.
    private PaymentMethodType preferredPaymentMethod;
    private PaymentTerms paymentTerms; // e.g., NET30, ADVANCE, DUE_ON_RECEIPT
    private String currencyType; // USD, EUR, etc.
    private BigDecimal creditLimit; // Maximum allowable credit
    public SupplierPaymentMethod(){}
    public SupplierPaymentMethod(
            String bankName,
            String bankAccountNumber,
            String accountHolderName,
            String accountHolderPhoneNumber,
            List<PaymentMethodType> paymentMethods,
            PaymentMethodType preferredPaymentMethod,
            PaymentTerms paymentTerms,
            String currencyType,
            BigDecimal creditLimit
    ) {
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.accountHolderName = accountHolderName;
        this.accountHolderPhoneNumber = accountHolderPhoneNumber;
        this.paymentMethods = paymentMethods;
        this.preferredPaymentMethod = preferredPaymentMethod;
        this.paymentTerms = paymentTerms;
        this.currencyType = currencyType;
        this.creditLimit = creditLimit;
    }

    // Getters and Setters
    public List<PaymentMethodType> getPaymentMethods() { return paymentMethods; }
    public void setPaymentMethods(List<PaymentMethodType> paymentMethods) { this.paymentMethods = paymentMethods; }

    public PaymentMethodType getPreferredPaymentMethod() { return preferredPaymentMethod; }
    public void setPreferredPaymentMethod(PaymentMethodType preferredPaymentMethod) { this.preferredPaymentMethod = preferredPaymentMethod; }

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

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }

    public String getCurrencyType() { return currencyType; }
    public void setCurrencyType(String currencyType) { this.currencyType = currencyType; }

    public PaymentTerms getPaymentTerms() { return paymentTerms; }
    public void setPaymentTerms(PaymentTerms paymentTerms) { this.paymentTerms = paymentTerms; }

    public BigDecimal getCreditLimit() { return creditLimit; }
    public void setCreditLimit(BigDecimal creditLimit) { this.creditLimit = creditLimit; }

    @Override
    public String toString() {
        return "SupplierPaymentMethod{" +
                ", paymentMethods=" + paymentMethods +
                ", preferredPaymentMethod='" + preferredPaymentMethod + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", accountHolderPhoneNumber='" + accountHolderPhoneNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", creditLimit=" + creditLimit +
                ", paymentTerms=" + paymentTerms +
                '}';
    }
}