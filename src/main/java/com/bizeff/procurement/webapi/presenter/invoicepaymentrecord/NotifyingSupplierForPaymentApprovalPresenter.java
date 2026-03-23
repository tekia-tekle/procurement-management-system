package com.bizeff.procurement.webapi.presenter.invoicepaymentrecord;

import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalOutPutDS;
import com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord.NotifyingSupplierForPaymentApprovalViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class NotifyingSupplierForPaymentApprovalPresenter implements NotifySupplierForPaymentApprovalOutputBoundary {
    private List<NotifyingSupplierForPaymentApprovalViewModel> notifyingSupplierForPaymentApprovalViewModels;

    public NotifyingSupplierForPaymentApprovalPresenter(List<NotifyingSupplierForPaymentApprovalViewModel> notifyingSupplierForPaymentApprovalViewModels) {
        this.notifyingSupplierForPaymentApprovalViewModels = notifyingSupplierForPaymentApprovalViewModels;
    }

    @Override
    public void presentNotification(List<NotifySupplierForPaymentApprovalOutPutDS> invoiceNotifierOutPutDS) {

        List<NotifyingSupplierForPaymentApprovalViewModel> notifingSupplierViewModels = new ArrayList<>();
        for (NotifySupplierForPaymentApprovalOutPutDS invoiceNotifier : invoiceNotifierOutPutDS) {
            NotifyingSupplierForPaymentApprovalViewModel notifyingSupplierForPaymentApprovalViewModel = new NotifyingSupplierForPaymentApprovalViewModel(
                    invoiceNotifier.getPaymentId(),
                    invoiceNotifier.getRemainingAmount().toString(),
                    Long.valueOf(invoiceNotifier.getRemainingDate()),
                    invoiceNotifier.isIsnotified(),
                    invoiceNotifier.getMessage()
            );
            notifingSupplierViewModels.add(notifyingSupplierForPaymentApprovalViewModel);
        }
        this.notifyingSupplierForPaymentApprovalViewModels = notifingSupplierViewModels;
    }
    public List<NotifyingSupplierForPaymentApprovalViewModel> getNotifyingSupplierForPaymentApprovalViewModels() {
        return notifyingSupplierForPaymentApprovalViewModels;
    }
    @Override
    public String toString() {
        return "NotifyingSupplierForPaymentApprovalPresenter{" +
                "notifyingSupplierForPaymentApprovalViewModels=" + notifyingSupplierForPaymentApprovalViewModels +
                '}';
    }
}
