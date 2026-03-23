package com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking;

public class EvaluateSupplierForContractRenewalOutputDS {
    private String contractId;
    private String oldSupplierId;
    private String newSupplierId;
    private Boolean isRenewed;

    public EvaluateSupplierForContractRenewalOutputDS
            (String contractId,
             String oldSupplierId,
             String newSupplierId,
             Boolean isRenewed
            )
    {
        this.contractId = contractId;
        this.oldSupplierId = oldSupplierId;
        this.newSupplierId = newSupplierId;
        this.isRenewed = isRenewed;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getOldSupplierId() {
        return oldSupplierId;
    }

    public void setOldSupplierId(String oldSupplierId) {
        this.oldSupplierId = oldSupplierId;
    }

    public String getNewSupplierId() {
        return newSupplierId;
    }

    public void setNewSupplierId(String newSupplierId) {
        this.newSupplierId = newSupplierId;
    }

    public Boolean getRenewed() {
        return isRenewed;
    }

    public void setRenewed(Boolean renewed) {
        isRenewed = renewed;
    }

    @Override
    public String toString() {
        return "EvaluateSupplierForContractRenewalOutputDS{" +
                "contractId='" + contractId + '\'' +
                ", oldSupplierId='" + oldSupplierId + '\'' +
                ", newSupplierId='" + newSupplierId + '\'' +
                ", isRenewed=" + isRenewed +
                '}';
    }
}
