package com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement;

import com.bizeff.procurement.models.contracts.ContractFile;
import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.models.enums.DeliveryTerms;
import com.bizeff.procurement.models.enums.PaymentTerms;

import java.time.LocalDate;
import java.util.List;

public class ViewContractDetailOutputDS {
    private String contractId;
    private String contractTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isRenewable;
    private PaymentTerms paymentTerms;
    private DeliveryTerms deliveryTerms;
    private ContractStatus contractStatus;
    private LocalDate createdDate;
    private String supplierId;
    private List<String>PurchaseOrderIds;
    private List<ContractFile> attachedFiles;
    private String message; // if added the new purchase order to the contract successfully.or not.

    public ViewContractDetailOutputDS(String contractId, String contractTitle,
                                      LocalDate startDate, LocalDate endDate,
                                      boolean isRenewable, PaymentTerms paymentTerms,
                                      DeliveryTerms deliveryTerms, ContractStatus contractStatus,
                                      LocalDate createdDate, String supplierId,
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

    public boolean isRenewable() {
        return isRenewable;
    }

    public void setRenewable(boolean renewable) {
        isRenewable = renewable;
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

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
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
        return "ViewContractDetailOutputDS{" +
                "contractId='" + contractId + '\'' +
                ", contractTitle='" + contractTitle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isRenewable=" + isRenewable +
                ", paymentTerms=" + paymentTerms +
                ", deliveryTerms=" + deliveryTerms +
                ", contractStatus=" + contractStatus +
                ", createdDate=" + createdDate +
                ", supplierId='" + supplierId + '\'' +
                ", PurchaseOrderIds=" + PurchaseOrderIds +
                ", attachedFiles=" + attachedFiles +
                '}';
    }
}