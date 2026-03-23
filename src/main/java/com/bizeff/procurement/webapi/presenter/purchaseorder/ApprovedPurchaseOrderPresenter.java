package com.bizeff.procurement.webapi.presenter.purchaseorder;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.ApprovePurchaseOrderOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.ApprovePurchaseOrderOutputDS;
import com.bizeff.procurement.webapi.viewmodel.purchaseorder.ApprovedPurchaseOrderViewModel;
import org.springframework.stereotype.Component;
@Component
public class ApprovedPurchaseOrderPresenter implements ApprovePurchaseOrderOutputBoundary {
    private ApprovedPurchaseOrderViewModel approvedPurchaseOrderViewModel;
    @Override
    public void presentPurchaseOrderWithStatus(ApprovePurchaseOrderOutputDS trackedPurchaseOrder) {

        ApprovedPurchaseOrderViewModel approvedOrderViewModel = new ApprovedPurchaseOrderViewModel
                (
                        trackedPurchaseOrder.getOrderId(),
                        trackedPurchaseOrder.isApproved(),
                        trackedPurchaseOrder.getApprovalDate().toString(),
                        trackedPurchaseOrder.getApprovedBy()
                );

        this.approvedPurchaseOrderViewModel = approvedOrderViewModel;
    }
    public ApprovedPurchaseOrderViewModel getApprovedPurchaseOrderViewModel() {
        return approvedPurchaseOrderViewModel;
    }

    @Override
    public String toString() {
        return "ApprovedPurchaseOrderViewModel{" +
                "approvedPurchaseOrderViewModel =" + approvedPurchaseOrderViewModel +
                '}';
    }
}
