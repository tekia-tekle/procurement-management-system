package com.bizeff.procurement.webapi.presenter.contracts;

import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.NotifyExpiringContractOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.NotifyExpiringContractOutputDS;
import com.bizeff.procurement.webapi.viewmodel.contracts.NotifyExpiringContractViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotifyExpiringContractPresenter implements NotifyExpiringContractOutputBoundary {
    private List<NotifyExpiringContractViewModel> notifyExpiringContractViewModels;
    public NotifyExpiringContractPresenter(List<NotifyExpiringContractViewModel> notifyExpiringContractViewModels) {
        this.notifyExpiringContractViewModels = notifyExpiringContractViewModels;
    }
    @Override
    public void notifyContracts(List<NotifyExpiringContractOutputDS> notifyExpiringContractOutputs) {
        List<NotifyExpiringContractViewModel> expiringContractViewModels = new ArrayList<>();
        for (NotifyExpiringContractOutputDS notifingExpiringContract: notifyExpiringContractOutputs){
            NotifyExpiringContractViewModel expiringContractViewModel = new NotifyExpiringContractViewModel(notifingExpiringContract.getContractId(),notifingExpiringContract.getContractTitle(),notifingExpiringContract.getRemainingDate(),notifingExpiringContract.isNotified());
            expiringContractViewModels.add(expiringContractViewModel);
        }
        notifyExpiringContractViewModels = expiringContractViewModels;
    }

    public List<NotifyExpiringContractViewModel> getNotifyContractViewModels() {
        return notifyExpiringContractViewModels;
    }

    public void setNotifyingContractViewModels(List<NotifyExpiringContractViewModel> notifyExpiringContractViewModels) {
        this.notifyExpiringContractViewModels = notifyExpiringContractViewModels;
    }

    @Override
    public String toString() {
        return "NotifyExpiringContractPresenter{" +
                "notifyExpiringContractViewModels=" + notifyExpiringContractViewModels +
                '}';
    }
}
