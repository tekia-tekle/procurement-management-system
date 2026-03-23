package com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement;

import com.bizeff.procurement.models.contracts.ContractFile;
import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.models.enums.DeliveryTerms;
import com.bizeff.procurement.models.enums.PaymentTerms;

import java.time.LocalDate;
import java.util.List;

public class ContractOutputDS {
    private String contractId;
    private String contractTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private PaymentTerms paymentTerms;
    private DeliveryTerms deliveryTerms;
    private ContractStatus status;
    private boolean isRenewable;
    private String supplierId;
    private List<String> purchaseOrderIds;
    private List<ContractFile> attachecdFiles;
    private LocalDate createdAt;
    private String createdBy;
    private Long remainingDate;
    private boolean isNotified;
    private String message;

    // defaultConstructor
    public ContractOutputDS(){}

    public ContractOutputDS(String contractId,
                            String contractTitle,
                            LocalDate startDate,
                            LocalDate endDate,
                            PaymentTerms paymentTerms,
                            DeliveryTerms deliveryTerms,
                            ContractStatus status,
                            boolean isRenewable,
                            String supplierId,
                            List<String> purchaseOrderIds,
                            List<ContractFile> attachecdFiles,
                            LocalDate createdAt,
                            String createdBy,
                            Long remainingDate,
                            boolean isNotified,
                            String message) {
        this.contractId = contractId;
        this.contractTitle = contractTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentTerms = paymentTerms;
        this.deliveryTerms = deliveryTerms;
        this.status = status;
        this.isRenewable = isRenewable;
        this.supplierId = supplierId;
        this.purchaseOrderIds = purchaseOrderIds;
        this.attachecdFiles = attachecdFiles;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.remainingDate = remainingDate;
        this.isNotified = isNotified;
        this.message = message;
    }

    // Getter and Setters

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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

    public boolean isRenewable() {
        return isRenewable;
    }

    public void setRenewable(boolean renewable) {
        isRenewable = renewable;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public List<String> getPurchaseOrderIds() {
        return purchaseOrderIds;
    }

    public void setPurchaseOrderIds(List<String> purchaseOrderIds) {
        this.purchaseOrderIds = purchaseOrderIds;
    }

    public List<ContractFile> getAttachecdFiles() {
        return attachecdFiles;
    }

    public void setAttachecdFiles(List<ContractFile> attachecdFiles) {
        this.attachecdFiles = attachecdFiles;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getRemainingDate() {
        return remainingDate;
    }

    public void setRemainingDate(Long remainingDate) {
        this.remainingDate = remainingDate;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
