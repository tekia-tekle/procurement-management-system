package com.bizeff.procurement.persistences.entity.suppliermanagement;

import com.bizeff.procurement.models.enums.PaymentMethodType;
import com.bizeff.procurement.models.enums.PaymentTerms;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payment_method")
public class PaymentMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_name",nullable = false)
    private String bankName;

    @Column(name = "bank_account_number",nullable = false)
    private String bankAccountNumber;

    @Column(name = "Holder_name",nullable = false)
    private String accountHolderName;
    @Column(name = "Holder_PhoneNumber",nullable = false)
    private String accountHolderPhoneNumber;

    @ElementCollection
    @CollectionTable(
            name = "accepted_payment_types",
            joinColumns = @JoinColumn(name = "payment_method_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private List<PaymentMethodType> acceptedPaymentTypes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_payment_method",nullable = false)
    private PaymentMethodType preferredPaymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_terms",nullable = false)
    private PaymentTerms paymentTerms;
    @Column(name = "currency_type",nullable = false)
    private String currencyType;
    @PositiveOrZero
    @Column(name = "credit_limit", precision = 19)
    private BigDecimal creditLimit;

    @ManyToMany(mappedBy = "paymentMethodEntities",cascade = CascadeType.ALL)
    private List<SupplierEntity> suppliers = new ArrayList<>();

    // Constructors
    public PaymentMethodEntity() {}

    public PaymentMethodEntity(
            String bankName,
            String bankAccountNumber,
            String accountHolderName,
            String accountHolderPhoneNumber,
            List<PaymentMethodType> acceptedPaymentTypes,
            PaymentMethodType preferredPaymentMethod,
            PaymentTerms paymentTerms,
            String currencyType,
            BigDecimal creditLimit)
    {
        this.acceptedPaymentTypes = acceptedPaymentTypes;
        this.preferredPaymentMethod = preferredPaymentMethod;
        this.accountHolderName = accountHolderName;
        this.accountHolderPhoneNumber = accountHolderPhoneNumber;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.currencyType = currencyType;
        this.paymentTerms = paymentTerms;
        this.creditLimit = creditLimit;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public List<PaymentMethodType> getAcceptedPaymentTypes() {
        return acceptedPaymentTypes;
    }

    public void setAcceptedPaymentTypes(List<PaymentMethodType> acceptedPaymentTypes) {
        this.acceptedPaymentTypes = acceptedPaymentTypes;
    }

    public PaymentMethodType getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }

    public void setPreferredPaymentMethod(PaymentMethodType preferredPaymentMethod) {
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

    public PaymentTerms getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(PaymentTerms paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public List<SupplierEntity> getSuppliers() {
        return suppliers;
    }

    public void setSupplier(List<SupplierEntity> suppliers) {
        this.suppliers = suppliers;
    }
}
