package com.bizeff.procurement.domaininterfaces.outputds.purchaseorder;

import java.time.LocalDate;

public class ApprovePurchaseOrderOutputDS {
    private String approvedBy;
    private String orderId;
    private  boolean approved;
    private LocalDate approvalDate;
    public ApprovePurchaseOrderOutputDS(
            String orderId,
            boolean approved,
            LocalDate approvalDate,
            String approvedBy
    ) {
        this.approvedBy = approvedBy;
        this.orderId = orderId;
        this.approvalDate = approvalDate;
        this.approved = approved;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    @Override
    public String toString() {
        return "ApprovePurchaseOrderOutputDS{" +
                "approvedBy='" + approvedBy + '\'' +
                ", orderId='" + orderId + '\'' +
                ", approved=" + approved +
                ", approvalDate=" + approvalDate +
                '}';
    }
}
