package com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking;

public class EvaluatedSupplierForContractRenewalViewModel {
    private String contractId;
    private String oldSupplierId;
    private String newSupplierId;
    private Boolean isRenewed;

    // Default constructor for serialization/deserialization
    public EvaluatedSupplierForContractRenewalViewModel() {}
    // Constructor to initialize all fields
    public EvaluatedSupplierForContractRenewalViewModel(String contractId,
                                                        String oldSupplierId,
                                                        String newSupplierId,
                                                        Boolean isRenewed) {
        this.contractId = contractId;
        this.oldSupplierId = oldSupplierId;
        this.newSupplierId = newSupplierId;
        this.isRenewed = isRenewed;
    }

    // Getters and Setters
    public String getContractId() {
        return contractId;
    }
    public String getOldSupplierId() {
        return oldSupplierId;
    }
    public String getNewSupplierId() {
        return newSupplierId;
    }
    public Boolean getRenewed() {
        return isRenewed;
    }
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
    public void setOldSupplierId(String oldSupplierId) {
        this.oldSupplierId = oldSupplierId;
    }
    public void setNewSupplierId(String newSupplierId) {
        this.newSupplierId = newSupplierId;
    }
    public void setRenewed(Boolean renewed) {
        isRenewed = renewed;
    }

    @Override
    public String toString() {
        return "EvaluatedSupplierForContractRenewalViewModel{" +
                "contractId='" + contractId + '\'' +
                ", oldSupplierId='" + oldSupplierId + '\'' +
                ", newSupplierId='" + newSupplierId + '\'' +
                ", isRenewed=" + isRenewed +
                '}';
    }
}
