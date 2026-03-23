package com.bizeff.procurement.webapi.presenter.contracts;

import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.ViewContractDetailOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.ViewContractDetailOutputDS;
import com.bizeff.procurement.webapi.viewmodel.contracts.ViewContractDetailsViewModel;
import org.springframework.stereotype.Component;
@Component
public class ViewContractDetailPresenter implements ViewContractDetailOutputBoundary {
    private ViewContractDetailsViewModel viewContractDetailsViewModel;
    public ViewContractDetailPresenter(ViewContractDetailsViewModel viewContractDetailsViewModel) {
        this.viewContractDetailsViewModel = viewContractDetailsViewModel;
    }

    @Override
    public void presentContractDetails(ViewContractDetailOutputDS contractDetailOutputDS) {
        ViewContractDetailsViewModel contractDetailsViewModel = new ViewContractDetailsViewModel(contractDetailOutputDS.getContractId(),
                contractDetailOutputDS.getContractTitle(), contractDetailOutputDS.getStartDate().toString(),
                contractDetailOutputDS.getEndDate().toString(),contractDetailOutputDS.isRenewable(),contractDetailOutputDS.getPaymentTerms().toString(),
                contractDetailOutputDS.getDeliveryTerms().toString(),contractDetailOutputDS.getContractStatus().toString(),contractDetailOutputDS.getCreatedDate().toString(),
                contractDetailOutputDS.getSupplierId(), contractDetailOutputDS.getPurchaseOrderIds(),contractDetailOutputDS.getAttachedFiles());
        contractDetailsViewModel.setMessage(contractDetailOutputDS.getMessage());
        viewContractDetailsViewModel = contractDetailsViewModel;
    }
    public ViewContractDetailsViewModel getContractDetailsViewModel() {
        return viewContractDetailsViewModel;
    }
    @Override
    public String toString() {
        return "ContractDetailsPresenter{" +
                "viewContractDetailsViewModel=" + viewContractDetailsViewModel +
                '}';
    }
}
