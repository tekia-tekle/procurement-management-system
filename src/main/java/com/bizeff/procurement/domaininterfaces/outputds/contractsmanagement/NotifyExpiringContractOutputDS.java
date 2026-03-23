package com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement;

public class NotifyExpiringContractOutputDS {

    private String contractId;
    private String contractTitle;
    private Long remainingDate;
    private boolean isNotified;

    public NotifyExpiringContractOutputDS(String contractId,
                                          String contractTitle,
                                          Long remainingDate,
                                          boolean isNotified) {
        this.contractId = contractId;
        this.contractTitle = contractTitle;
        this.remainingDate = remainingDate;
        this.isNotified = isNotified;
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
    @Override
    public String toString() {
        return "NotifyExpiringContractOutputDS{" +
                "contractId='" + contractId + '\'' +
                ", contractTitle='" + contractTitle + '\'' +
                ", remainingDate=" + remainingDate +
                ", isNotified=" + isNotified +
                '}';
    }
}
