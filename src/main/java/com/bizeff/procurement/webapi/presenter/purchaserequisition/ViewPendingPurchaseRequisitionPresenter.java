package com.bizeff.procurement.webapi.presenter.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.ViewPendingRequisitionsOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.ViewPendingRequisitionsOutputData;
import com.bizeff.procurement.webapi.viewmodel.purchaserequisition.ViewPendingPurchaseRequisitionViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ViewPendingPurchaseRequisitionPresenter implements ViewPendingRequisitionsOutputBoundary {
    private List<ViewPendingPurchaseRequisitionViewModel> pendingPurchaseRequisitionViewModel;
    public ViewPendingPurchaseRequisitionPresenter(List<ViewPendingPurchaseRequisitionViewModel> pendingPurchaseRequisitionViewModel){
        this.pendingPurchaseRequisitionViewModel = pendingPurchaseRequisitionViewModel;
    }
    @Override
    public void presentPendingRequisition(List<ViewPendingRequisitionsOutputData> pendingRequisitions) {
        pendingPurchaseRequisitionViewModel = new ArrayList<>();
        for (ViewPendingRequisitionsOutputData outputData:pendingRequisitions){

            ViewPendingPurchaseRequisitionViewModel viewModel = new ViewPendingPurchaseRequisitionViewModel(
                    outputData.getRequisitionId(),
                    outputData.getRequisitionNumber(),
                    outputData.getRequisitionStatus().toString(),
                    outputData.getRequisitionDate().toString(),
                    outputData.getPriorityLevel().toString(),
                    outputData.getExpectedDeliveryDate().toString()
            );

            pendingPurchaseRequisitionViewModel.add(viewModel);
        }
    }

    public List<ViewPendingPurchaseRequisitionViewModel> getPendingPurchaseRequisitionViewModel() {
        return pendingPurchaseRequisitionViewModel;
    }

    @Override
    public String toString() {
        return "ViewPendingPurchaseRequisitionPresenter{" +
                "pendingPurchaseRequisitionViewModel=" + pendingPurchaseRequisitionViewModel +
                '}';
    }
}
