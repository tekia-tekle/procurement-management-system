package com.bizeff.procurement.domaininterfaces.inputds.contractsmanagement;
import com.bizeff.procurement.models.contracts.ContractFile;
import com.bizeff.procurement.models.enums.DeliveryTerms;
import com.bizeff.procurement.models.enums.PaymentTerms;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreateContractInputDS {
    private ContractsContactDetailsInputDS managersDetails;
    private String supplierId;
    private String contractTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalCosts;
    private PaymentTerms paymentTerms;
    private DeliveryTerms deliveryTerms;

    private boolean isRenewable;
    private List<String> purchaseOrderIdLists;

    private List<ContractFile>attachments;

    private LocalDate createdDate;
    // Constructor
    public CreateContractInputDS(ContractsContactDetailsInputDS managersDetails,
                                 String  supplierId, String contractTitle,
                                 LocalDate startDate, LocalDate endDate, BigDecimal totalCosts,
                                 PaymentTerms paymentTerms, DeliveryTerms deliveryTerms,
                                 boolean isRenewable, List<String> purchaseOrderIdLists,
                                 List<ContractFile> attachments,LocalDate createdDate) {
        this.managersDetails = managersDetails;
        this.supplierId = supplierId;
        this.contractTitle = contractTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCosts = totalCosts;
        this.paymentTerms = paymentTerms;
        this.deliveryTerms = deliveryTerms;
        this.isRenewable = isRenewable;
        this.purchaseOrderIdLists = purchaseOrderIdLists;
        this.attachments = attachments;
        this.createdDate = createdDate;
    }
    // Getters and Setters

    public ContractsContactDetailsInputDS getManagersDetails() {
        return managersDetails;
    }

    public void setManagersDetails(ContractsContactDetailsInputDS managersDetails) {
        this.managersDetails = managersDetails;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    public BigDecimal getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(BigDecimal totalCosts) {
        this.totalCosts = totalCosts;
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

    public boolean isRenewable() {
        return isRenewable;
    }

    public void setRenewable(boolean renewable) {
        isRenewable = renewable;
    }

    public List<String> getPurchaseOrderIdLists() {
        return purchaseOrderIdLists;
    }

    public void setPurchaseOrderIdLists(List<String> purchaseOrderIdLists) {
        this.purchaseOrderIdLists = purchaseOrderIdLists;
    }

    public List<ContractFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ContractFile> attachments) {
        this.attachments = attachments;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "CreateContractInputDS{" +
                "managersDetails=" + managersDetails +
                ", supplierId='" + supplierId + '\'' +
                ", contractTitle='" + contractTitle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalCosts=" + totalCosts +
                ", paymentTerms=" + paymentTerms +
                ", deliveryTerms=" + deliveryTerms +
                ", isRenewable=" + isRenewable +
                ", purchaseOrderIdLists=" + purchaseOrderIdLists +
                ", attachments=" + attachments +
                ", createdDate=" + createdDate +
                '}';
    }
}