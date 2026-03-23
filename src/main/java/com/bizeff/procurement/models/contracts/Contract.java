package com.bizeff.procurement.models.contracts;

import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.models.enums.DeliveryTerms;
import com.bizeff.procurement.models.enums.PaymentTerms;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
public class Contract {
    private Long id;
    private String contractId;
    private String contractTitle;
    private Supplier supplier;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate renewalDate; // Only relevant if renewable
    private PaymentTerms paymentTerms;
    private DeliveryTerms deliveryTerms;
    private ContractStatus status;
    private BigDecimal totalCost;
    private boolean isRenewable;
    private boolean isApproved;
    private boolean isNotified;
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();// Supports multiple purchase orders.
    private List<ContractFile> attachments = new ArrayList<>();//Holds attached files.
    private LocalDate createdDate;

    public Contract(){}

    public Contract( String contractTitle,
                     Supplier supplier,
                     LocalDate startDate,
                     LocalDate endDate,
                     PaymentTerms paymentTerms,
                     DeliveryTerms deliveryTerms,
                     BigDecimal totalCost,
                     boolean isRenewable,
                     List<PurchaseOrder> purchaseOrders,
                     List<ContractFile> attachments,
                     LocalDate createdDate)
    {
        this.contractId = IdGenerator.generateId("CON");
        this.contractTitle = contractTitle;
        this.supplier = supplier;
        this.startDate = startDate;
        this.endDate = endDate;
        this.renewalDate = startDate;
        this.paymentTerms = paymentTerms;
        this.deliveryTerms = deliveryTerms;
        this.totalCost = totalCost;
        this.status = ContractStatus.PENDING;
        this.isRenewable = isRenewable;
        this.isApproved = true;
        this.isNotified = false;
        this.purchaseOrders = purchaseOrders != null ? new ArrayList<>(purchaseOrders) : new ArrayList<>();
        this.attachments = attachments != null ? new ArrayList<>(attachments) : new ArrayList<>();
        this.createdDate = createdDate;
    }

    /** Returns contract duration in days */
    public long getContractDuration() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /** Getters & Setters. */

    public void addPurchaseOrder(PurchaseOrder purchaseOrder){
        if (purchaseOrder != null && !purchaseOrders.contains(purchaseOrder)){

            this.purchaseOrders.add(purchaseOrder);

        }
    }
    public void addAttachment(ContractFile contractFile){
        attachments.add(contractFile);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContractId() { return contractId; }
    public String getContractTitle() { return contractTitle; }
    public Supplier getSupplier() { return supplier; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public LocalDate getRenewalDate() { return renewalDate; }
    public BigDecimal getTotalCost() { return totalCost; }
    public PaymentTerms getPaymentTerms() { return paymentTerms; }
    public DeliveryTerms getDeliveryTerms() { return deliveryTerms; }
    public ContractStatus getStatus() { return status; }
    public boolean isRenewable() { return isRenewable; }
    public boolean isApproved() { return isApproved; }
    public boolean isNotified() { return isNotified; }
    public List<PurchaseOrder> getPurchaseOrders() { return purchaseOrders; }
    public List<ContractFile> getAttachments() { return attachments; }
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setPaymentTerms(PaymentTerms paymentTerms) {
        this.paymentTerms = paymentTerms;
    }
    public void setDeliveryTerms(DeliveryTerms deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }
    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
    public void setRenewable(boolean renewable) {
        isRenewable = renewable;
    }
    public void setApproved(boolean approved) {
        isApproved = approved;
    }
    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders != null ? new ArrayList<>(purchaseOrders) : new ArrayList<>();
    }

    public void setAttachments(List<ContractFile> attachments) {
        this.attachments = attachments != null ? new ArrayList<>(attachments) : new ArrayList<>();
    }
    public void setStatus(ContractStatus status) { this.status = status; }
    public void setRenewalDate(LocalDate renewalDate) { this.renewalDate = renewalDate; }
    public void setNotified(boolean notified) { isNotified = notified; }
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractId='" + contractId + '\'' +
                ", supplier=" + supplier +
                ", contractTitle='" + contractTitle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", renewalDate=" + renewalDate +
                ", totalCost=" + totalCost +
                ", paymentTerms=" + paymentTerms +
                ", deliveryTerms=" + deliveryTerms +
                ", status=" + status +
                ", isRenewable=" + isRenewable +
                ", isApproved=" + isApproved +
                ", contractDuration=" + getContractDuration() + " days" +
                ", purchaseOrders=" + purchaseOrders +
                ", attachments=" + attachments +
                '}';
    }
}
