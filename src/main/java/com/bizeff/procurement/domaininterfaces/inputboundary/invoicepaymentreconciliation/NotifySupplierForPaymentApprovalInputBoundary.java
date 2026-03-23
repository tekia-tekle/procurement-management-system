package com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalOutPutDS;

import java.time.LocalDate;
import java.util.List;

public interface NotifySupplierForPaymentApprovalInputBoundary {
    List<NotifySupplierForPaymentApprovalOutPutDS> notifySupplier(LocalDate expectedPaymentDate);
}
