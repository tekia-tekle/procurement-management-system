package com.bizeff.procurement.webapi.presenter.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.CreateRequisitionOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.CreateRequisitionOutputDS;
import com.bizeff.procurement.models.purchaserequisition.RequestedItem;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.webapi.viewmodel.purchaserequisition.CreatePurchaseRequisitionViewModel;
import com.bizeff.procurement.webapi.viewmodel.purchaserequisition.RequestedItemViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreatePurchaseRequisitionPresenter implements CreateRequisitionOutputBoundary {
    private CreatePurchaseRequisitionViewModel createPurchaseRequisitionViewModel;

    public CreatePurchaseRequisitionPresenter(CreatePurchaseRequisitionViewModel createPurchaseRequisitionViewModel){
        this.createPurchaseRequisitionViewModel = createPurchaseRequisitionViewModel;
    }
    @Override
    public void presentRequestedRequisitions(CreateRequisitionOutputDS requestedRequisition) {
        this.createPurchaseRequisitionViewModel = new CreatePurchaseRequisitionViewModel(
                requestedRequisition.getRequisitionId(),
                requestedRequisition.getRequisitionNumber(),
                requestedRequisition.getRequisitionDate().toString(),
                getRequestedItems(requestedRequisition),
                requestedRequisition.getRequestedBy(),
                requestedRequisition.getDepartmentId(),
                requestedRequisition.getCostCenterId(),
                requestedRequisition.getTotalEstimatedCosts().toString(),
                requestedRequisition.getExpectedDeliveryDate().toString(),
                requestedRequisition.getRequestedto(),
                requestedRequisition.getStatus().toString());
    }

    public List<RequestedItemViewModel> getRequestedItems(CreateRequisitionOutputDS requisitionOutputDS){
        List<RequestedItemViewModel> requestedItemViewModelList = new ArrayList<>();

        for (RequestedItem requestedItem: requisitionOutputDS.getRequestedItems()){
            Inventory inventory = requestedItem.getInventory();
            RequestedItemViewModel requestedItemViewModel = new RequestedItemViewModel(

                    inventory.getItemId(),
                    inventory.getItemName(),
                    inventory.getItemCategory(),
                    requestedItem.getRequestedQuantity(),
                    inventory.getUnitPrice().toString(),
                    requestedItem.getTotalPrice().toString(),
                    inventory.getExpiryDate().toString(),
                    inventory.getSpecification());

            requestedItemViewModelList.add(requestedItemViewModel);
        }
        return  requestedItemViewModelList;
    }
    public CreatePurchaseRequisitionViewModel getRequestedPurchaseRequisitionViewModel() {
        return createPurchaseRequisitionViewModel;
    }
}
