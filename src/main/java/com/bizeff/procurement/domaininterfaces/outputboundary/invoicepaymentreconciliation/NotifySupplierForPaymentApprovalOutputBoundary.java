package com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalOutPutDS;

import java.util.List;

public interface NotifySupplierForPaymentApprovalOutputBoundary {
    void presentNotification(List<NotifySupplierForPaymentApprovalOutPutDS> notifySupplierForPaymentApprovalOutPutDS);
}
