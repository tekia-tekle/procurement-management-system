package com.bizeff.procurement.webapi.controller;

import com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation.ReconcileInvoiceInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation.ViewReconciledPaymentRecordInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.invoicepaymentreconciliation.ReconcileInvoiceInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalOutPutDS;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ReconclieInvoiceOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ViewReconciledPaymentRecordOutPutDS;
import com.bizeff.procurement.models.enums.ReconciliationStatus;
import com.bizeff.procurement.webapi.presenter.invoicepaymentrecord.NotifyingSupplierForPaymentApprovalPresenter;
import com.bizeff.procurement.webapi.presenter.invoicepaymentrecord.ReconcileInvoicePresenter;
import com.bizeff.procurement.webapi.presenter.invoicepaymentrecord.ViewReconciledPaymentRecordPresenter;
import com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord.NotifyingSupplierForPaymentApprovalViewModel;
import com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord.ReconcileInvoiceViewModel;
import com.bizeff.procurement.webapi.viewmodel.invoicepaymentrecord.ViewReconciledPaymentRecordViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/paymentreconciliation")
public class PaymentReconciliationController {
    private ReconcileInvoiceInputBoundary accountOfficerInputBoundary;
    private ReconcileInvoicePresenter reconcileInvoicePresenter;
    private NotifySupplierForPaymentApprovalInputBoundary invoiceNotifierInputBoundary;
    private NotifyingSupplierForPaymentApprovalPresenter notifyingSupplierForPaymentApprovalPresenter;
    private ViewReconciledPaymentRecordInputBoundary invoiceManagerInputBoundary;
    private ViewReconciledPaymentRecordPresenter viewReconciledPaymentRecordPresenter;

    public PaymentReconciliationController(ReconcileInvoiceInputBoundary accountOfficerInputBoundary,
                                           ReconcileInvoicePresenter reconcileInvoicePresenter,
                                           NotifySupplierForPaymentApprovalInputBoundary invoiceNotifierInputBoundary,
                                           NotifyingSupplierForPaymentApprovalPresenter notifyingSupplierForPaymentApprovalPresenter,
                                           ViewReconciledPaymentRecordInputBoundary invoiceManagerInputBoundary,
                                           ViewReconciledPaymentRecordPresenter viewReconciledPaymentRecordPresenter) {
        this.accountOfficerInputBoundary = accountOfficerInputBoundary;
        this.reconcileInvoicePresenter = reconcileInvoicePresenter;
        this.invoiceNotifierInputBoundary = invoiceNotifierInputBoundary;
        this.notifyingSupplierForPaymentApprovalPresenter = notifyingSupplierForPaymentApprovalPresenter;
        this.invoiceManagerInputBoundary = invoiceManagerInputBoundary;
        this.viewReconciledPaymentRecordPresenter = viewReconciledPaymentRecordPresenter;
    }

    @PostMapping("/reconcile")
    public ResponseEntity<?> reconcileInvoice(@RequestBody ReconcileInvoiceInputDS input){

        ReconclieInvoiceOutputDS invoiceAccountOfficerOutputDS = accountOfficerInputBoundary.reconcileInvoice(input);

        reconcileInvoicePresenter.presentReconciledInvoice(invoiceAccountOfficerOutputDS);

        ReconcileInvoiceViewModel reconcileInvoiceViewModel = reconcileInvoicePresenter.getInvoiceAccountOfficerViewModel();

        return ResponseEntity.ok(reconcileInvoiceViewModel);
    }


    @GetMapping("/notify")
    public ResponseEntity<?>notifySupplier(@RequestParam LocalDate expectedPaymentDate){
        List<NotifySupplierForPaymentApprovalOutPutDS> invoiceNotifierOutPutDS = invoiceNotifierInputBoundary.notifySupplier(expectedPaymentDate);
        notifyingSupplierForPaymentApprovalPresenter.presentNotification(invoiceNotifierOutPutDS);
        List<NotifyingSupplierForPaymentApprovalViewModel> notifyingSupplierForPaymentApprovalViewModels = notifyingSupplierForPaymentApprovalPresenter.getNotifyingSupplierForPaymentApprovalViewModels();

        return ResponseEntity.ok(notifyingSupplierForPaymentApprovalViewModels);
    }

    /** getting the total reconciliation History.*/
    @GetMapping("/viewhistory/total")
    public ResponseEntity<?> getReconciliationHistory() {
        List<ViewReconciledPaymentRecordOutPutDS> invoiceManagerOutPutDS = invoiceManagerInputBoundary.viewReconciliationTotalHistory();
        viewReconciledPaymentRecordPresenter.presentReconciledPaymentRecordHistory(invoiceManagerOutPutDS);
        List<ViewReconciledPaymentRecordViewModel> viewReconciledPaymentRecordViewModels = viewReconciledPaymentRecordPresenter.getInvoiceManagerViewModel();
        return ResponseEntity.ok(viewReconciledPaymentRecordViewModels);
    }
    /** Get Reconciled Payment Record with in the specified time Range. */
    @GetMapping("/viewhistory/paymentDateBased")
    public ResponseEntity<?>getReconciledPaymentRecordByTimeRange(@RequestParam LocalDate startDate,@RequestParam LocalDate endDate){
        List<ViewReconciledPaymentRecordOutPutDS> reconciledPaymentRecordOutPutDS = invoiceManagerInputBoundary.viewReconciliationHistoryByTimeRange(startDate,endDate);
        viewReconciledPaymentRecordPresenter.presentReconciledPaymentRecordHistory(reconciledPaymentRecordOutPutDS);
        List<ViewReconciledPaymentRecordViewModel> viewReconciledPaymentRecordViewModels = viewReconciledPaymentRecordPresenter.getInvoiceManagerViewModel();
        return ResponseEntity.ok(viewReconciledPaymentRecordViewModels);
    }
    /** Get Reconciled Payment Record With the same supplier.*/
    @GetMapping("/viewhistory/supplierBased")
    public ResponseEntity<?> getReconciledPaymentRecordBySupplierId(@RequestParam String supplierId) {
        List<ViewReconciledPaymentRecordOutPutDS>invoiceWithSameVendor = invoiceManagerInputBoundary.viewReconciledPaymentRecord_WithSameSupplier(supplierId);
        viewReconciledPaymentRecordPresenter.presentReconciledPaymentRecordHistory(invoiceWithSameVendor);
        List<ViewReconciledPaymentRecordViewModel> viewReconciledPaymentRecordViewModels = viewReconciledPaymentRecordPresenter.getInvoiceManagerViewModel();
        return ResponseEntity.ok(viewReconciledPaymentRecordViewModels);
    }

    /** Get Reconciled Payment Record with the same reconcilition status.*/
    @GetMapping("/viewhistory/statusBased")
    public ResponseEntity<?> getReconciledPaymentRecordByStatus(@RequestParam String status) {
        // Validate the status input
        ReconciliationStatus reconciliationStatus = ReconciliationStatus.valueOf(status);
        List<ViewReconciledPaymentRecordOutPutDS>invoiceWithSameStatus = invoiceManagerInputBoundary.viewReconciledPaymentRecordByStatus(reconciliationStatus);
        viewReconciledPaymentRecordPresenter.presentReconciledPaymentRecordHistory(invoiceWithSameStatus);
        List<ViewReconciledPaymentRecordViewModel> viewReconciledPaymentRecordViewModels = viewReconciledPaymentRecordPresenter.getInvoiceManagerViewModel();
        return ResponseEntity.ok(viewReconciledPaymentRecordViewModels);
    }
}
