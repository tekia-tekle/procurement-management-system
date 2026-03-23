package com.bizeff.procurement.webapi.viewmodel.contracts;

import com.bizeff.procurement.models.contracts.ContractFile;

import java.util.List;

public class CreateContractViewModel {
    private String contractId;
    private String contractTitle;
    private String startDate;
    private String endDate;
    private String paymentTerms;
    private String deliveryTerms;
    private String status;
    private boolean isRenewable;
    private String supplierId;
    private List<String> purchaseOrderIds;
    private List<ContractFile> attachecdFiles;
    private String createdAt;
    private String createdBy;

    public CreateContractViewModel(){}

    public CreateContractViewModel(String contractId,
                                   String contractTitle,
                                   String startDate,
                                   String endDate,
                                   String paymentTerms,
                                   String deliveryTerms,
                                   String status,
                                   boolean isRenewable,
                                   String supplierId,
                                   List<String> purchaseOrderIds,
                                   List<ContractFile> attachecdFiles,
                                   String createdAt,
                                   String createdBy)
    {
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
    }

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getDeliveryTerms() {
        return deliveryTerms;
    }

    public void setDeliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "CreateContractViewModel{" +
                "contractId='" + contractId + '\'' +
                ", contractTitle='" + contractTitle + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", paymentTerms='" + paymentTerms + '\'' +
                ", deliveryTerms='" + deliveryTerms + '\'' +
                ", status='" + status + '\'' +
                ", isRenewable=" + isRenewable +
                ", supplierId='" + supplierId + '\'' +
                ", purchaseOrderIds=" + purchaseOrderIds +
                ", attachecdFiles=" + attachecdFiles +
                ", createdAt='" + createdAt + '\'' +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}

