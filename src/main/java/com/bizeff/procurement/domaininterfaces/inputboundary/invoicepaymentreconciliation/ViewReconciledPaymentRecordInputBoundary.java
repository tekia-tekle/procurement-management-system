package com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ViewReconciledPaymentRecordOutPutDS;
import com.bizeff.procurement.models.enums.ReconciliationStatus;

import java.time.LocalDate;
import java.util.List;

public interface ViewReconciledPaymentRecordInputBoundary {
    List<ViewReconciledPaymentRecordOutPutDS> viewReconciliationTotalHistory();
    List<ViewReconciledPaymentRecordOutPutDS> viewReconciliationHistoryByTimeRange(LocalDate startDate, LocalDate endDate);
    List<ViewReconciledPaymentRecordOutPutDS> viewReconciledPaymentRecord_WithSameSupplier(String supplierId);
    List<ViewReconciledPaymentRecordOutPutDS> viewReconciledPaymentRecordByStatus(ReconciliationStatus status);
}
