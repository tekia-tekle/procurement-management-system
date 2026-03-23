package com.bizeff.procurement.webapi.controller;

import com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder.ApprovePurchaseOrderInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder.CreatePurchaseOrderInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder.SendPurchaseOrderToSupplierInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.ApprovePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.CreatePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.SendPurchaseOrderToSupplierInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.ApprovePurchaseOrderOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.CreatePurchaseOrderOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.SendPurchaseOrderToSupplierOutPutDS;
import com.bizeff.procurement.webapi.presenter.purchaseorder.ApprovedPurchaseOrderPresenter;
import com.bizeff.procurement.webapi.presenter.purchaseorder.CreatePurchaseOrderPresenter;
import com.bizeff.procurement.webapi.presenter.purchaseorder.SendPurchaseOrderToSupplierPresenter;
import com.bizeff.procurement.webapi.viewmodel.purchaseorder.ApprovedPurchaseOrderViewModel;
import com.bizeff.procurement.webapi.viewmodel.purchaseorder.CreatePurchaseOrderViewModel;
import com.bizeff.procurement.webapi.viewmodel.purchaseorder.SendPurchaseOrderToSupplierViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {
    private CreatePurchaseOrderInputBoundary purchaseOrderClerkInputBoundary;
    private CreatePurchaseOrderPresenter createdPurchaseOrderPresenter;
    private ApprovePurchaseOrderInputBoundary purchaseOrderManagerInputBoundary;
    private ApprovedPurchaseOrderPresenter approvedPurchaseOrderPresenter;
    private SendPurchaseOrderToSupplierInputBoundary purchaseOrdersVendorInputBoundary;
    private SendPurchaseOrderToSupplierPresenter sendPurchaseOrderToSupplierPresenter;

    public PurchaseOrderController(CreatePurchaseOrderInputBoundary purchaseOrderClerkInputBoundary,
                                   CreatePurchaseOrderPresenter createdPurchaseOrderPresenter,
                                   ApprovePurchaseOrderInputBoundary purchaseOrderManagerInputBoundary,
                                   ApprovedPurchaseOrderPresenter approvedPurchaseOrderPresenter,
                                   SendPurchaseOrderToSupplierInputBoundary purchaseOrdersVendorInputBoundary,
                                   SendPurchaseOrderToSupplierPresenter sendPurchaseOrderToSupplierPresenter) {
        this.purchaseOrderClerkInputBoundary = purchaseOrderClerkInputBoundary;
        this.createdPurchaseOrderPresenter = createdPurchaseOrderPresenter;
        this.purchaseOrderManagerInputBoundary = purchaseOrderManagerInputBoundary;
        this.approvedPurchaseOrderPresenter = approvedPurchaseOrderPresenter;
        this.purchaseOrdersVendorInputBoundary = purchaseOrdersVendorInputBoundary;
        this.sendPurchaseOrderToSupplierPresenter = sendPurchaseOrderToSupplierPresenter;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody CreatePurchaseOrderInputDS purchaseOrderClerkInputDS){

        CreatePurchaseOrderOutputDS purchaseOrderClerkOutputDS = purchaseOrderClerkInputBoundary.createOrder(purchaseOrderClerkInputDS);

        createdPurchaseOrderPresenter.presentCreatedPurchaseOrder(purchaseOrderClerkOutputDS);

        CreatePurchaseOrderViewModel createdPurchaseOrderViewModel = createdPurchaseOrderPresenter.getCreatedPurchaseOrderViewModel();

        return ResponseEntity.ok(createdPurchaseOrderViewModel);
    }
    @PostMapping("/approve")
    public ResponseEntity<?> trackPurchaseOrder(@RequestBody ApprovePurchaseOrderInputDS purchaseOrderManagerInputDS){
        ApprovePurchaseOrderOutputDS purchaseOrderManagerOutputDS = purchaseOrderManagerInputBoundary.trackPurchaseOrderStatus(purchaseOrderManagerInputDS);

        approvedPurchaseOrderPresenter.presentPurchaseOrderWithStatus(purchaseOrderManagerOutputDS);

        ApprovedPurchaseOrderViewModel approvedPurchaseOrderViewModel = approvedPurchaseOrderPresenter.getApprovedPurchaseOrderViewModel();
        return ResponseEntity.ok(approvedPurchaseOrderViewModel);
    }
    @PostMapping("/generate-electronically")
    public ResponseEntity<?>generatePurchaseOrderElectronically(@RequestBody SendPurchaseOrderToSupplierInputDS inputDS){
        // This method is used to send a purchase order to a supplier electronically.
        List<SendPurchaseOrderToSupplierOutPutDS> electornicallyGeneratedPurchaseOrders = purchaseOrdersVendorInputBoundary.sendPurchaseOrderToSupplier(inputDS);

        sendPurchaseOrderToSupplierPresenter.presentElectronicallyTransferredPurchaseOrders(electornicallyGeneratedPurchaseOrders);

        List<SendPurchaseOrderToSupplierViewModel> sendPurchaseOrderToSupplierViewModels = sendPurchaseOrderToSupplierPresenter.getSendPurchaseOrderToSupplierViewModels();

        return ResponseEntity.ok(sendPurchaseOrderToSupplierViewModels);
    }
}
