package com.bizeff.procurement.webapi.viewmodel.contracts;

import com.bizeff.procurement.models.contracts.ContractFile;

import java.util.List;

public class ViewContractDetailsViewModel {
    private String contractId;
    private String contractTitle;
    private String startDate;
    private String endDate;
    private boolean isRenewable;
    private String paymentTerms;
    private String deliveryTerms;
    private String contractStatus;
    private String createdDate;
    private String supplierId;
    private List<String> PurchaseOrderIds;
    private List<ContractFile> attachedFiles;

    private String message; // if added the new purchase order to the contract successfully.or not.
    public ViewContractDetailsViewModel(){}

    public ViewContractDetailsViewModel(String contractId, String contractTitle,
                                        String startDate, String endDate,
                                        boolean isRenewable, String paymentTerms,
                                        String deliveryTerms, String contractStatus,
                                        String createdDate, String supplierId,
                                        List<String> purchaseOrderIds, List<ContractFile> attachedFiles)
    {
        this.contractId = contractId;
        this.contractTitle = contractTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isRenewable = isRenewable;
        this.paymentTerms = paymentTerms;
        this.deliveryTerms = deliveryTerms;
        this.contractStatus = contractStatus;
        this.createdDate = createdDate;
        this.supplierId = supplierId;
        PurchaseOrderIds = purchaseOrderIds;
        this.attachedFiles = attachedFiles;
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

    public boolean isRenewable() {
        return isRenewable;
    }

    public void setRenewable(boolean renewable) {
        isRenewable = renewable;
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

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public List<String> getPurchaseOrderIds() {
        return PurchaseOrderIds;
    }

    public void setPurchaseOrderIds(List<String> purchaseOrderIds) {
        PurchaseOrderIds = purchaseOrderIds;
    }

    public List<ContractFile> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<ContractFile> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ViewContractDetailsViewModel{" +
                "contractId='" + contractId + '\'' +
                ", contractTitle='" + contractTitle + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", isRenewable=" + isRenewable +
                ", paymentTerms='" + paymentTerms + '\'' +
                ", deliveryTerms='" + deliveryTerms + '\'' +
                ", contractStatus='" + contractStatus + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", PurchaseOrderIds=" + PurchaseOrderIds +
                ", attachedFiles=" + attachedFiles +
                ", message='" + message + '\'' +
                '}';
    }
}
