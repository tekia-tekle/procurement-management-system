package com.bizeff.procurement.domaininterfaces.inputds.purchaseorder;
import java.time.LocalDate;
public class ApprovePurchaseOrderInputDS {
    private PurchaseOrderContactDetailsInputDS managerDetails;
    private String orderId;
    private LocalDate approvalDate;
    public ApprovePurchaseOrderInputDS(){}
    public ApprovePurchaseOrderInputDS(PurchaseOrderContactDetailsInputDS managerDetails,
                                       String orderId,
                                       LocalDate approvalDate){
        this.managerDetails = managerDetails;
        this.orderId = orderId;
        this.approvalDate = approvalDate;
    }
    public PurchaseOrderContactDetailsInputDS getManagerDetails() {
        return managerDetails;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setManagerDetails(PurchaseOrderContactDetailsInputDS managerDetails) {
        this.managerDetails = managerDetails;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }
}
