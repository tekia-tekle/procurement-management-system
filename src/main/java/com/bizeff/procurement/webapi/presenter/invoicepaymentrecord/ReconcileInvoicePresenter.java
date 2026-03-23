package com.bizeff.procurement.webapi.presenter.invoicepaymentrecord;

import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.ReconcileInvoiceOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ReconclieInvoiceOutputDS;
import com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord.ReconcileInvoiceViewModel;
import org.springframework.stereotype.Component;

@Component
public class ReconcileInvoicePresenter implements ReconcileInvoiceOutputBoundary {
    private ReconcileInvoiceViewModel reconcileInvoiceViewModel;

    public ReconcileInvoicePresenter(ReconcileInvoiceViewModel reconcileInvoiceViewModel) {
        this.reconcileInvoiceViewModel = reconcileInvoiceViewModel;
    }
    @Override
    public void presentReconciledInvoice(ReconclieInvoiceOutputDS output) {
        ReconcileInvoiceViewModel reconciledInvoiceViewModel = new ReconcileInvoiceViewModel(output.getPaymentId(), output.getStatus().toString(),output.getReconciliationDate().toString(),output.getMessages());
        reconcileInvoiceViewModel = reconciledInvoiceViewModel;
    }

    public ReconcileInvoiceViewModel getInvoiceAccountOfficerViewModel() {
        return reconcileInvoiceViewModel;
    }

    public void setInvoiceAccountOfficerViewModel(ReconcileInvoiceViewModel reconcileInvoiceViewModel) {
        this.reconcileInvoiceViewModel = reconcileInvoiceViewModel;
    }

    @Override
    public String toString() {
        return "ReconcileInvoicePresenter{" +
                "reconcileInvoiceViewModel=" + reconcileInvoiceViewModel +
                '}';
    }
}
