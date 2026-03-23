package com.bizeff.procurement.webapi.presenter.contracts;

import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.CreateContractOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.CreateContractOutputDS;
import com.bizeff.procurement.webapi.viewmodel.contracts.CreateContractViewModel;
import org.springframework.stereotype.Component;

@Component
public class CreateContractPresenter implements CreateContractOutputBoundary {
    private CreateContractViewModel createContractViewModel;

    public CreateContractPresenter(CreateContractViewModel createContractViewModel) {
        this.createContractViewModel = createContractViewModel;
    }

    @Override
    public void presentContracts(CreateContractOutputDS createdContract) {
        CreateContractViewModel newContractViewModel = new CreateContractViewModel(
                createdContract.getContractId(),
                createdContract.getContractTitle(),
                createdContract.getStartDate().toString(),
                createdContract.getEndDate().toString(),
                createdContract.getPaymentTerms().toString(),
                createdContract.getDeliveryTerms().toString(),
                createdContract.getStatus().toString(),
                createdContract.isRenewable(),
                createdContract.getSupplierId(),
                createdContract.getPurchaseOrderIds(),
                createdContract.getAttachecdFiles(),
                createdContract.getCreatedAt().toString(),
                createdContract.getCreatedBy());

        this.createContractViewModel = newContractViewModel;
    }

    public CreateContractViewModel getContractManagerViewModel() {
        return createContractViewModel;
    }
    @Override
    public String toString() {
        return "CreateContractPresenter{" +
                "createContractViewModel=" + createContractViewModel +
                '}';
    }
}
