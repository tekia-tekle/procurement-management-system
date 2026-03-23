package com.bizeff.procurement.webapi.controller;

import com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement.CreateContractInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement.NotifyExpiringContractInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement.ViewContractDetailsInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.contractsmanagement.CreateContractInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.CreatePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.CreateContractOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.NotifyExpiringContractOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.ViewContractDetailOutputDS;
import com.bizeff.procurement.webapi.presenter.contracts.CreateContractPresenter;
import com.bizeff.procurement.webapi.presenter.contracts.NotifyExpiringContractPresenter;
import com.bizeff.procurement.webapi.presenter.contracts.ViewContractDetailPresenter;
import com.bizeff.procurement.webapi.viewmodel.contracts.CreateContractViewModel;
import com.bizeff.procurement.webapi.viewmodel.contracts.NotifyExpiringContractViewModel;
import com.bizeff.procurement.webapi.viewmodel.contracts.ViewContractDetailsViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
public class ContractController {
    private CreateContractInputBoundary createContractInputBoundary;
    private CreateContractPresenter createContractPresenter;
    private NotifyExpiringContractInputBoundary expiringContractInputBoundary;
    private NotifyExpiringContractPresenter notifyingContractPresenter;
    private ViewContractDetailsInputBoundary viewContractDetailsInputBoundary;
    private ViewContractDetailPresenter viewContractDetailPresenter;

    public ContractController(CreateContractInputBoundary createContractInputBoundary,
                              CreateContractPresenter createContractPresenter,
                              NotifyExpiringContractInputBoundary expiringContractInputBoundary,
                              NotifyExpiringContractPresenter notifyingContractPresenter,
                              ViewContractDetailsInputBoundary viewContractDetailsInputBoundary,
                              ViewContractDetailPresenter viewContractDetailPresenter) {
        this.createContractInputBoundary = createContractInputBoundary;
        this.createContractPresenter = createContractPresenter;
        this.expiringContractInputBoundary = expiringContractInputBoundary;
        this.notifyingContractPresenter = notifyingContractPresenter;
        this.viewContractDetailsInputBoundary = viewContractDetailsInputBoundary;
        this.viewContractDetailPresenter = viewContractDetailPresenter;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createContract(@RequestBody CreateContractInputDS contractManagerInputDS){
        // This method is used to create a contract.
        CreateContractOutputDS contractManagerOutputDS = createContractInputBoundary.createContracts(contractManagerInputDS);

        createContractPresenter.presentContracts(contractManagerOutputDS);

        CreateContractViewModel createContractViewModel = createContractPresenter.getContractManagerViewModel();

        return ResponseEntity.ok(createContractViewModel);
    }
    @GetMapping("/notify")
    public ResponseEntity<?>notifyExpiringContracts(@RequestParam int daysBeforeExpirationThreshold){

        List<NotifyExpiringContractOutputDS> contractNotifierOutputDS = expiringContractInputBoundary.notifyContracts(daysBeforeExpirationThreshold);
        notifyingContractPresenter.notifyContracts(contractNotifierOutputDS);
        List<NotifyExpiringContractViewModel> notifyingContractViewModel = notifyingContractPresenter.getNotifyContractViewModels();
        return ResponseEntity.ok(notifyingContractViewModel);
    }
    @PostMapping("/{contractId}/viewcontractdetails")
    public ResponseEntity<?> viewContractDetailsForPurchaseOrder(@PathVariable String contractId, @RequestBody CreatePurchaseOrderInputDS inputDS) {

        ViewContractDetailOutputDS contractDetailsViewerOutputDS = viewContractDetailsInputBoundary.viewContractDetailWhenCreatingPurchaseOrder(contractId,inputDS);
        viewContractDetailPresenter.presentContractDetails(contractDetailsViewerOutputDS);
        ViewContractDetailsViewModel viewContractDetailsViewModel = viewContractDetailPresenter.getContractDetailsViewModel();
        return ResponseEntity.ok(viewContractDetailsViewModel);
    }
}
