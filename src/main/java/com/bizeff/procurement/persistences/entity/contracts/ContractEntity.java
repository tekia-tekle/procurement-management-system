package com.bizeff.procurement.persistences.entity.contracts;

import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.models.enums.DeliveryTerms;
import com.bizeff.procurement.models.enums.PaymentTerms;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.SupplierEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contract")
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "contract_Id",nullable = false,unique = true)
    private String contractId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplierEntity;

    @Column(name = "contract_title", nullable = false)
    private String contractTitle;

    @Column(name = "start_date", nullable = false,updatable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "renewal_Date",insertable = false)
    private LocalDate renewalDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_terms", nullable = false)
    private PaymentTerms paymentTerms;
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_terms", nullable = false)
    private DeliveryTerms deliveryTerms;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ContractStatus status;
    @Column(name = "total_value", nullable = false)
    @PositiveOrZero
    private BigDecimal totalCost;
    @OneToMany(mappedBy = "contract",cascade = CascadeType.ALL)
    @Size(min = 1)
    private List<PurchaseOrderEntity> purchaseOrders = new ArrayList<>();

    @Column(name = "is_Renewable",nullable = false)
    private boolean isRenewable;
    @Column(name = "is_notified", nullable = false)
    private boolean isNotified;
    private boolean isApproved;
    @OneToMany(mappedBy = "contract",cascade = CascadeType.ALL)
    private List<ContractFileEntity> attachments = new ArrayList<>();

    @Column(name = "created_Date",nullable = false)
    private LocalDate createdDate;

    @Column(name = "Last_Update_Date")
    private LocalDate lastUpdateDate;

    public ContractEntity() {}

    /** Getters and Setters. */
    public void addPurchaseOrder(PurchaseOrderEntity purchaseOrder){
        purchaseOrders.add(purchaseOrder);
        purchaseOrder.setContract(this);
    }
    public void addAttachements(ContractFileEntity contractFile){
        attachments.add(contractFile);
        contractFile.setContract(this);
    }
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public SupplierEntity getSupplierEntity() {
        return supplierEntity;
    }

    public void setSupplierEntity(SupplierEntity supplierEntity) {
        this.supplierEntity = supplierEntity;
    }

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public PaymentTerms getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(PaymentTerms paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public DeliveryTerms getDeliveryTerms() {
        return deliveryTerms;
    }

    public void setDeliveryTerms(DeliveryTerms deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public List<PurchaseOrderEntity> getPurchaseOrder() {
        return purchaseOrders;
    }

    public void setPurchaseOrder(List<PurchaseOrderEntity> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public boolean isRenewable() {
        return isRenewable;
    }

    public void setRenewable(boolean renewable) {
        isRenewable = renewable;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    public List<ContractFileEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ContractFileEntity> attachments) {
        this.attachments = attachments;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @PrePersist
    @PreUpdate
    private void validateContractDates() {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
    }

}
