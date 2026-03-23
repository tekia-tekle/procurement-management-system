package com.bizeff.procurement.webapi.viewmodel.purchaseorder;

public class ApprovedPurchaseOrderViewModel {
    private String orderId;
    private  boolean approved;
    private String approvalDate;
    private String approvedBy;
    public ApprovedPurchaseOrderViewModel(){}

    public ApprovedPurchaseOrderViewModel(String orderId,
                                          boolean approved,
                                          String approvalDate,
                                          String approvedBy)
    {
        this.orderId = orderId;
        this.approved = approved;
        this.approvalDate = approvalDate;
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

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Override
    public String toString() {
        return "ApprovedPurchaseOrderViewModel{" +
                "orderId='" + orderId + '\'' +
                ", approved=" + approved +
                ", approvalDate='" + approvalDate + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                '}';
    }
}