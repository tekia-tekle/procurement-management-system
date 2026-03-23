package com.bizeff.procurement.webapi.presenter.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.TrackRequisitionOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.TrackRequisitionOutputDS;
import com.bizeff.procurement.webapi.viewmodel.purchaserequisition.TrackPurchaseRequisitionViewModel;
import org.springframework.stereotype.Component;

@Component
public class TrackPurchaseRequisitionPresenter implements TrackRequisitionOutputBoundary {
    private TrackPurchaseRequisitionViewModel trackPurchaseRequisitionViewModel;
    public TrackPurchaseRequisitionPresenter(TrackPurchaseRequisitionViewModel trackPurchaseRequisitionViewModel){
        this.trackPurchaseRequisitionViewModel = trackPurchaseRequisitionViewModel;
    }
    @Override
    public void presentApprovedRequisitions(TrackRequisitionOutputDS outputDS) {

        trackPurchaseRequisitionViewModel = new TrackPurchaseRequisitionViewModel(
                outputDS.getRequisitionId(),
                outputDS.getStatus().toString(),
                outputDS.getManagerName(),
                outputDS.getTrackedDate().toString(),
                outputDS.getComments());
    }

    public TrackPurchaseRequisitionViewModel getApprovedPurchaseRequisitionViewModel() {
        return trackPurchaseRequisitionViewModel;
    }


    @Override
    public String toString() {
        return "TrackPurchaseRequisitionPresenter{" +
                "trackPurchaseRequisitionViewModel=" + trackPurchaseRequisitionViewModel +
                '}';
    }
}
