package com.bizeff.procurement.webapi.presenter.purchaseorder;

import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.CreatePurchaseOrderOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.CreatePurchaseOrderOutputDS;
import com.bizeff.procurement.webapi.viewmodel.purchaseorder.CreatePurchaseOrderViewModel;
import org.springframework.stereotype.Component;

@Component
public class CreatePurchaseOrderPresenter implements CreatePurchaseOrderOutputBoundary {
    private CreatePurchaseOrderViewModel createdPurchaseOrderViewModel;

    public CreatePurchaseOrderPresenter(CreatePurchaseOrderViewModel createdPurchaseOrderViewModel) {
        this.createdPurchaseOrderViewModel = createdPurchaseOrderViewModel;
    }

    @Override
    public void presentCreatedPurchaseOrder(CreatePurchaseOrderOutputDS newPurchaseOrder) {
        CreatePurchaseOrderViewModel purchaseOrderViewModel = new CreatePurchaseOrderViewModel(newPurchaseOrder.getOrderId(),
                newPurchaseOrder.getDepartmentId(),
                newPurchaseOrder.getOrderedDate().toString(),
                newPurchaseOrder.getOrderedRequisitionIds(),
                newPurchaseOrder.getSupplierId(),
                newPurchaseOrder.getDeliveryDate().toString(),
                newPurchaseOrder.getShippingMethod(),
                newPurchaseOrder.getTotalCost().toString(),
                newPurchaseOrder.getOrderStatus().toString(),
                newPurchaseOrder.getOrderedBy(),
                newPurchaseOrder.isApproved(),
                newPurchaseOrder.isShipped());
        createdPurchaseOrderViewModel = purchaseOrderViewModel;
    }

    public CreatePurchaseOrderViewModel getCreatedPurchaseOrderViewModel() {
        return createdPurchaseOrderViewModel;
    }

    public void setCreatedPurchaseOrderViewModel(CreatePurchaseOrderViewModel createdPurchaseOrderViewModel) {
        this.createdPurchaseOrderViewModel = createdPurchaseOrderViewModel;
    }

    @Override
    public String toString() {
        return "CreatePurchaseOrderPresenter{" +
                "createdPurchaseOrderViewModel=" + createdPurchaseOrderViewModel +
                '}';
    }
}
