package com.bizeff.procurement.webapi.presenter.invoicepaymentrecord;

import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.ViewReconciledPaymentRecordOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ViewReconciledPaymentRecordOutPutDS;
import com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord.ViewReconciledPaymentRecordViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ViewReconciledPaymentRecordPresenter implements ViewReconciledPaymentRecordOutputBoundary {
    private List<ViewReconciledPaymentRecordViewModel> viewReconciledPaymentRecordViewModels;
    public ViewReconciledPaymentRecordPresenter(List<ViewReconciledPaymentRecordViewModel> viewReconciledPaymentRecordViewModels){
        this.viewReconciledPaymentRecordViewModels = viewReconciledPaymentRecordViewModels;
    }
    @Override
    public void presentReconciledPaymentRecordHistory(List<ViewReconciledPaymentRecordOutPutDS> invoiceRecordHistory) {

        List<ViewReconciledPaymentRecordViewModel> reconciledPaymentRecordViewModels = new ArrayList<>();
        for (ViewReconciledPaymentRecordOutPutDS invoiceRecord : invoiceRecordHistory) {
            ViewReconciledPaymentRecordViewModel reconciledPaymentRecordViewModel = new ViewReconciledPaymentRecordViewModel(
                    invoiceRecord.getPaymentId(),
                    invoiceRecord.getInvoiceId(),
                    invoiceRecord.getOrderId(),
                    invoiceRecord.getSupplierId(),
                    invoiceRecord.getReceiptId(),
                    invoiceRecord.getExpectedAmount().toString(),
                    invoiceRecord.getActualPaidAmount().toString(),
                    invoiceRecord.getDiscrepancyAmount().toString(),
                    invoiceRecord.getPaymentDate().toString(),
                    invoiceRecord.getReconciliationDate().toString(),
                    invoiceRecord.getStatus().toString()
            );
            reconciledPaymentRecordViewModels.add(reconciledPaymentRecordViewModel);
        }
        this.viewReconciledPaymentRecordViewModels = reconciledPaymentRecordViewModels;
    }

    public List<ViewReconciledPaymentRecordViewModel> getInvoiceManagerViewModel() {
        return viewReconciledPaymentRecordViewModels;
    }

    @Override
    public String toString() {
        return "ViewReconciledPaymentRecordPresenter{" +
                "viewReconciledPaymentRecordViewModels=" + viewReconciledPaymentRecordViewModels +
                '}';
    }
}
