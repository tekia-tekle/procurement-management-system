package com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ReconclieInvoiceOutputDS;

public interface ReconcileInvoiceOutputBoundary {
    void presentReconciledInvoice(ReconclieInvoiceOutputDS output);
}
