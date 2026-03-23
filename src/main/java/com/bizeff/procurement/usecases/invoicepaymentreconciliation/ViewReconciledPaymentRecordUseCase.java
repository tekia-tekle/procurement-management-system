package com.bizeff.procurement.usecases.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation.ViewReconciledPaymentRecordInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.ViewReconciledPaymentRecordOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ViewReconciledPaymentRecordOutPutDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.PaymentReconciliationRepository;
import com.bizeff.procurement.models.enums.ReconciliationStatus;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;
import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;
import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.services.invoicepaymentreconciliation.PaymentReconciliationMaintainingService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateNotNull;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateString;

public class ViewReconciledPaymentRecordUseCase implements ViewReconciledPaymentRecordInputBoundary {
    private PaymentReconciliationRepository paymentReconciliationRepository;
    private PaymentReconciliationMaintainingService paymentReconciliationMaintainingService;
    private ViewReconciledPaymentRecordOutputBoundary viewReconciledPaymentRecordOutputBoundary;

    public ViewReconciledPaymentRecordUseCase(PaymentReconciliationMaintainingService paymentReconciliationMaintainingService,
                                              PaymentReconciliationRepository paymentReconciliationRepository,
                                              ViewReconciledPaymentRecordOutputBoundary viewReconciledPaymentRecordOutputBoundary)
    {
        this.paymentReconciliationRepository = paymentReconciliationRepository;
        this.paymentReconciliationMaintainingService = paymentReconciliationMaintainingService;
        this.viewReconciledPaymentRecordOutputBoundary = viewReconciledPaymentRecordOutputBoundary;
    }
    @Override
    public List<ViewReconciledPaymentRecordOutPutDS> viewReconciliationTotalHistory() {

        List<PaymentReconciliation> reconciledPaymentRecordLists = paymentReconciliationRepository.findAll();
        if (reconciledPaymentRecordLists == null || reconciledPaymentRecordLists.isEmpty()) {
            throw new IllegalArgumentException("There is no reconciled payment record.");
        }

        for (PaymentReconciliation paymentReconciliation : reconciledPaymentRecordLists) {
            paymentReconciliationMaintainingService.getPaymentReconciliationMap().put(paymentReconciliation.getPaymentId(), paymentReconciliation);
        }

        Map<String,PaymentReconciliation> paymentReconciliationMap = paymentReconciliationMaintainingService.getPaymentReconciliationMap();
        if (paymentReconciliationMap == null || paymentReconciliationMap.isEmpty()){
            throw new IllegalArgumentException("there is no Payment Reconciliation recorded before");
        }

        return reconcilationProcess(paymentReconciliationMap.values().stream().collect(Collectors.toList()));
    }
    @Override
    public List<ViewReconciledPaymentRecordOutPutDS> viewReconciliationHistoryByTimeRange(LocalDate startDate, LocalDate endDate) {

        validateDateRange(startDate, endDate);

        List<PaymentReconciliation> totalPaymentReconciliations = paymentReconciliationRepository.findAll();
        if (totalPaymentReconciliations == null || totalPaymentReconciliations.isEmpty()) {
            throw new IllegalArgumentException("There is no reconciled payment record in the repository.");
        }

        List<PaymentReconciliation> paymentReconciliationList = totalPaymentReconciliations.stream()
                .filter(paymentReconciliation -> !paymentReconciliation.getPaymentDate().isBefore(startDate)
                        && !paymentReconciliation.getPaymentDate().isAfter(endDate)).collect(Collectors.toList());

        if (paymentReconciliationList == null || paymentReconciliationList.isEmpty()){
            throw new IllegalArgumentException("there is no Reconciled Payment Record with payment date is in the specified Range.");
        }
        // Store all payment reconciliations in the map for easy access
        for (PaymentReconciliation paymentReconciliation : paymentReconciliationList) {
            paymentReconciliationMaintainingService.getPaymentReconciliationMap().put(paymentReconciliation.getPaymentId(), paymentReconciliation);
        }

        Map<String, PaymentReconciliation> paymentReconciliationMap = paymentReconciliationMaintainingService.getPaymentReconciliationMap();
        if (paymentReconciliationMap == null || paymentReconciliationMap.isEmpty()) {
            throw new IllegalArgumentException("There is no Payment Reconciliation recorded before.");
        }

        // Filter the map to get only records within the specified date range
        List<PaymentReconciliation> paymentReconciliationListWithSameDateRange = paymentReconciliationMap.values().stream()
                .filter(pr -> !pr.getPaymentDate().isBefore(startDate) && !pr.getPaymentDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (paymentReconciliationListWithSameDateRange == null || paymentReconciliationListWithSameDateRange.isEmpty()){
            throw new IllegalArgumentException("There is no Reconciled Payment Record with payment date in the specified Range.");
        }

        return reconcilationProcess(paymentReconciliationListWithSameDateRange);
    }
    @Override
    public List<ViewReconciledPaymentRecordOutPutDS>viewReconciledPaymentRecord_WithSameSupplier(String supplierId){
        validateString(supplierId,"SupplierId");

        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        if (paymentReconciliationList == null || paymentReconciliationList.isEmpty()){
            throw new IllegalArgumentException("there is no Reconciled Payment Record in the repository.");
        }
        List<PaymentReconciliation> paymentReconciliationListWithSameSupplier = paymentReconciliationList.stream()
                .filter(paymentReconciliation -> paymentReconciliation.getInvoice().getSupplier().getSupplierId().equals(supplierId))
                .collect(Collectors.toList());

        if (paymentReconciliationListWithSameSupplier == null || paymentReconciliationListWithSameSupplier.isEmpty()){
            throw new IllegalArgumentException("there is no Payment Reconciliation recorded before.");
        }
        // Store all payment reconciliations in the map for easy access
        for (PaymentReconciliation paymentReconciliation : paymentReconciliationListWithSameSupplier) {
            paymentReconciliationMaintainingService.getPaymentReconciliationMap().put(paymentReconciliation.getPaymentId(), paymentReconciliation);
        }
        Map<String, PaymentReconciliation> paymentReconciliationMap = paymentReconciliationMaintainingService.getPaymentReconciliationMap();
        if (paymentReconciliationMap == null || paymentReconciliationMap.isEmpty()) {
            throw new IllegalArgumentException("There is no Payment Reconciliation recorded before.");
        }
        // Filter the map to get only records with the same supplier
        List<PaymentReconciliation> paymentReconciliationsWithSameSupplier = paymentReconciliationMap.values().stream()
                .filter(pr -> pr.getInvoice().getSupplier().getSupplierId().equals(supplierId))
                .collect(Collectors.toList());

        if (paymentReconciliationsWithSameSupplier == null || paymentReconciliationsWithSameSupplier.isEmpty()){
            throw new IllegalArgumentException("There is no Reconciled Payment Record with the specified supplier ID.");
        }

        return reconcilationProcess(paymentReconciliationsWithSameSupplier);
    }

    @Override
    public List<ViewReconciledPaymentRecordOutPutDS>viewReconciledPaymentRecordByStatus(ReconciliationStatus status){
        // Validate the status input
        validateNotNull(status,"Reconciliation Status");

        // Get all payment reconciliations from the repository
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        if (paymentReconciliationList == null || paymentReconciliationList.isEmpty()){
            throw new IllegalArgumentException("there is no Reconciled Payment Record in the repository.");
        }
        // Filter the list to get only payment reconciliations with the specified status in the repository.
        List<PaymentReconciliation> paymentReconciliationListWithSameStatus = paymentReconciliationList.stream()
                .filter(paymentReconciliation -> paymentReconciliation.getReconciliationStatus().equals(status))
                .collect(Collectors.toList());

        // Check if there are any payment reconciliations with the specified status
        if (paymentReconciliationListWithSameStatus == null || paymentReconciliationListWithSameStatus.isEmpty()) {
            throw new IllegalArgumentException("There is no Payment Reconciliation recorded before.");
        }

        // Store all payment reconciliations in the map for easy access
        for (PaymentReconciliation paymentReconciliation : paymentReconciliationListWithSameStatus) {
            paymentReconciliationMaintainingService.getPaymentReconciliationMap().put(paymentReconciliation.getPaymentId(), paymentReconciliation);
        }

        // Get the map of payment reconciliations in the memory.
        Map<String, PaymentReconciliation> paymentReconciliationMap = paymentReconciliationMaintainingService.getPaymentReconciliationMap();
        if (paymentReconciliationMap == null || paymentReconciliationMap.isEmpty()) {
            throw new IllegalArgumentException("There is no Payment Reconciliation recorded before.");
        }

        // Filter the map to get only records with the same status.
        List<PaymentReconciliation> paymentReconciliationsWithSameStatus = paymentReconciliationMaintainingService.getReconciledRecordsByStatus(status);
        if (paymentReconciliationsWithSameStatus == null || paymentReconciliationsWithSameStatus.isEmpty()){
            throw new IllegalArgumentException("there is no PaymentReconciliation record with the specified Reconciliation Status.");
        }

        return reconcilationProcess(paymentReconciliationsWithSameStatus);
    }

    private List<ViewReconciledPaymentRecordOutPutDS> reconcilationProcess(List<PaymentReconciliation> paymentReconciliations) {
        if (paymentReconciliations == null || paymentReconciliations.isEmpty()) {
            throw new IllegalArgumentException("There is no reconciled payment record.");
        }
        // Store all payment reconciliations in the map for easy access
        List<ViewReconciledPaymentRecordOutPutDS> reconciledRecord = new ArrayList<>();
        for (PaymentReconciliation reconciliation : paymentReconciliations) {
            String paymentId = reconciliation.getPaymentId();

            DeliveryReceipt deliveryReceipt = reconciliation.getDeliveryReceipt();
            Invoice invoice = reconciliation.getInvoice();
            PurchaseOrder purchaseOrder = reconciliation.getPurchaseOrder();
            BigDecimal expectedAmountToPaid = paymentReconciliationMaintainingService.calculateExpectedAmount(deliveryReceipt, invoice);

            ViewReconciledPaymentRecordOutPutDS outPutDs = new ViewReconciledPaymentRecordOutPutDS(
                    paymentId, invoice.getInvoiceId(), purchaseOrder.getOrderId(),
                    purchaseOrder.getSupplier().getSupplierId(), deliveryReceipt.getReceiptId(),
                    expectedAmountToPaid, reconciliation.getActualPaidAmount(),
                    reconciliation.getDiscrepancyAmount(), reconciliation.getPaymentDate(),
                    reconciliation.getReconciliationDate(), reconciliation.getReconciliationStatus());

            reconciledRecord.add(outPutDs);
        }
        viewReconciledPaymentRecordOutputBoundary.presentReconciledPaymentRecordHistory(reconciledRecord);

        return reconciledRecord;
    }
    public void validateDate(LocalDate date,String fildName){
        if (date == null){
            throw new IllegalArgumentException(fildName+ "can't be null.");
        }
    }
    public void validateDateRange(LocalDate startDate, LocalDate endDate) {
        validateDate(startDate, "Start date");

        validateDate(endDate,"End Date");

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before Start date.");
        }
    }
}
