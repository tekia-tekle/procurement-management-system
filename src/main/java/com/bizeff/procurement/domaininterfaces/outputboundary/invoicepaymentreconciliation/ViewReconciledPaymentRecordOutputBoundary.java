package com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ViewReconciledPaymentRecordOutPutDS;

import java.util.List;

public interface ViewReconciledPaymentRecordOutputBoundary {
    void presentReconciledPaymentRecordHistory(List<ViewReconciledPaymentRecordOutPutDS> viewReconciledPaymentRecordOutPutDS);
}
