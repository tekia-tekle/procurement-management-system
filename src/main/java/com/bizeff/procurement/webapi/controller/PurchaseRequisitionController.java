package com.bizeff.procurement.webapi.controller;

import com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition.CreateRequisitionInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition.TrackRequisitionInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition.ViewPendingRequisitionsInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.CreateRequisitionInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.TrackRequisitionInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.CreateRequisitionOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.TrackRequisitionOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.ViewPendingRequisitionsOutputData;
import com.bizeff.procurement.webapi.presenter.purchaserequisition.CreatePurchaseRequisitionPresenter;
import com.bizeff.procurement.webapi.presenter.purchaserequisition.TrackPurchaseRequisitionPresenter;
import com.bizeff.procurement.webapi.presenter.purchaserequisition.ViewPendingPurchaseRequisitionPresenter;
import com.bizeff.procurement.webapi.viewmodel.purchaserequisition.CreatePurchaseRequisitionViewModel;
import com.bizeff.procurement.webapi.viewmodel.purchaserequisition.TrackPurchaseRequisitionViewModel;
import com.bizeff.procurement.webapi.viewmodel.purchaserequisition.ViewPendingPurchaseRequisitionViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/purchaserequisition")
public class PurchaseRequisitionController {
    private CreateRequisitionInputBoundary requisitionRequesterInputBoundary;
    private CreatePurchaseRequisitionPresenter createPurchaseRequisitionPresenter;
    private TrackRequisitionInputBoundary requisitionApproverInputBoundary;
    private TrackPurchaseRequisitionPresenter trackPurchaseRequisitionPresenter;
    private ViewPendingRequisitionsInputBoundary requisitionClerkInputBoundary;
    private ViewPendingPurchaseRequisitionPresenter viewPendingPurchaseRequisitionPresenter;

    public PurchaseRequisitionController(CreateRequisitionInputBoundary requisitionRequesterInputBoundary,
                                         CreatePurchaseRequisitionPresenter createPurchaseRequisitionPresenter,
                                         TrackRequisitionInputBoundary requisitionApproverInputBoundary,
                                         TrackPurchaseRequisitionPresenter trackPurchaseRequisitionPresenter,
                                         ViewPendingRequisitionsInputBoundary requisitionClerkInputBoundary,
                                         ViewPendingPurchaseRequisitionPresenter viewPendingPurchaseRequisitionPresenter) {
        this.requisitionRequesterInputBoundary = requisitionRequesterInputBoundary;
        this.createPurchaseRequisitionPresenter = createPurchaseRequisitionPresenter;
        this.requisitionApproverInputBoundary = requisitionApproverInputBoundary;
        this.trackPurchaseRequisitionPresenter = trackPurchaseRequisitionPresenter;
        this.requisitionClerkInputBoundary = requisitionClerkInputBoundary;
        this.viewPendingPurchaseRequisitionPresenter = viewPendingPurchaseRequisitionPresenter;
    }

    // This method handles the creation of a purchase requisition
    @PostMapping("/create")
    public ResponseEntity<?> createRequisition(@RequestBody CreateRequisitionInputDS inputDS){

        CreateRequisitionOutputDS requestedRequisition = requisitionRequesterInputBoundary.createRequisition(inputDS);
        createPurchaseRequisitionPresenter.presentRequestedRequisitions(requestedRequisition);

        CreatePurchaseRequisitionViewModel createPurchaseRequisitionViewModel = createPurchaseRequisitionPresenter.getRequestedPurchaseRequisitionViewModel();

        return ResponseEntity.ok(createPurchaseRequisitionViewModel);
    }
    @PostMapping("/track")
    public ResponseEntity<?>trackRequisition(@RequestBody TrackRequisitionInputDS requisitionApproverInputDS){

        TrackRequisitionOutputDS requisitionApproverOutputDS = requisitionApproverInputBoundary.trackRequisition(requisitionApproverInputDS);

        trackPurchaseRequisitionPresenter.presentApprovedRequisitions(requisitionApproverOutputDS);

        TrackPurchaseRequisitionViewModel trackPurchaseRequisitionViewModel = trackPurchaseRequisitionPresenter.getApprovedPurchaseRequisitionViewModel();
        return ResponseEntity.ok(trackPurchaseRequisitionViewModel);
    }
    @GetMapping("/viewpending")
    public ResponseEntity<List<ViewPendingPurchaseRequisitionViewModel>>gettingAllPendingRequisition(@RequestParam String departmentId) {
        List<ViewPendingRequisitionsOutputData> pendingRequisitions = requisitionClerkInputBoundary.viewAllPendingRequisition(departmentId);
        viewPendingPurchaseRequisitionPresenter.presentPendingRequisition(pendingRequisitions);
        List<ViewPendingPurchaseRequisitionViewModel> pendingPurchaseRequisitionViewModel = viewPendingPurchaseRequisitionPresenter.getPendingPurchaseRequisitionViewModel();

        return  ResponseEntity.ok(pendingPurchaseRequisitionViewModel);
    }
}
