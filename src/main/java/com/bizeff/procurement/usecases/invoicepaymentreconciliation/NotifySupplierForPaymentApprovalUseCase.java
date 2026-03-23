package com.bizeff.procurement.usecases.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.inputboundary.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalOutPutDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.PaymentReconciliationRepository;
import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;
import com.bizeff.procurement.services.invoicepaymentreconciliation.PaymentReconciliationMaintainingService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NotifySupplierForPaymentApprovalUseCase implements NotifySupplierForPaymentApprovalInputBoundary {

    private static Logger logger = Logger.getLogger(NotifySupplierForPaymentApprovalUseCase.class.getName());

    private PaymentReconciliationRepository paymentReconciliationRepository;
    private NotifySupplierForPaymentApprovalOutputBoundary notifySupplierForPaymentApprovalOutputBoundary;
    private PaymentReconciliationMaintainingService paymentReconciliationMaintainingService;

    public NotifySupplierForPaymentApprovalUseCase(
            PaymentReconciliationMaintainingService paymentReconciliationMaintainingService,
            PaymentReconciliationRepository paymentReconciliationRepository,
            NotifySupplierForPaymentApprovalOutputBoundary notifySupplierForPaymentApprovalOutputBoundary)
    {
        this.paymentReconciliationRepository = paymentReconciliationRepository;
        this.notifySupplierForPaymentApprovalOutputBoundary = notifySupplierForPaymentApprovalOutputBoundary;
        this.paymentReconciliationMaintainingService = paymentReconciliationMaintainingService;
    }

    @Override
    public List<NotifySupplierForPaymentApprovalOutPutDS> notifySupplier(LocalDate thresholdDate) {
        if (thresholdDate == null || thresholdDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expected payment date must not be null or in the past.");
        }

        List<PaymentReconciliation> totalPaymentReconciliations = paymentReconciliationRepository.findAll();
        if (totalPaymentReconciliations == null || totalPaymentReconciliations.isEmpty()){
            throw new IllegalArgumentException("there is no payment recorded before.");
        }

        // Step 1: Filter eligible records from repository

        List<PaymentReconciliation> notifiedRecords = totalPaymentReconciliations.stream()
                .filter(record -> record.getPaymentDate() != null && !record.getPaymentDate().isAfter(thresholdDate))
                .sorted(Comparator.comparing(PaymentReconciliation::getPaymentDate))
                .collect(Collectors.toList());

        if (notifiedRecords == null || notifiedRecords.isEmpty()) {
            throw new IllegalArgumentException("There is no Payment Reconciliation recorded that is notified with in the specified threshold date.");
        }
        for (PaymentReconciliation record : notifiedRecords) {
            paymentReconciliationMaintainingService.getPaymentReconciliationMap().put(record.getPaymentId(), record);
        }

        Map<String, PaymentReconciliation> paymentReconciliationMap = paymentReconciliationMaintainingService.getPaymentReconciliationMap();

        if (paymentReconciliationMap == null || paymentReconciliationMap.isEmpty()) {
            throw new IllegalArgumentException("There is no Payment Reconciliation recorded before.");
        }

        // Filter eligible records based on threshold date from the maintaining service
        List<PaymentReconciliation> notifyingPaymentRecordList = paymentReconciliationMaintainingService.getPaymentReconciliationMap()
                .values().stream()
                .filter(record -> !record.getPaymentDate().isAfter(thresholdDate))
                .sorted(Comparator.comparing(PaymentReconciliation::getPaymentDate))
                .collect(Collectors.toList());

        if (notifyingPaymentRecordList == null || notifyingPaymentRecordList.isEmpty()) {
            throw new IllegalArgumentException("There is no Payment Reconciliation recorded that is notified with in the specified threshold date.");
        }

        List<NotifySupplierForPaymentApprovalOutPutDS> notifications = new ArrayList<>();
        for (PaymentReconciliation record : notifyingPaymentRecordList) {
            String paymentId = record.getPaymentId();
            int daysUntilPayment = (int) ChronoUnit.DAYS.between(LocalDate.now(), record.getPaymentDate());
            String message;

            switch (record.getReconciliationStatus()) {
                case UNDERPAID:
                    message = String.format("Payment done by Payment_ID %s is partially paid. Discrepancy: %s. Payment expected in %d day(s).",
                            paymentId, record.getDiscrepancyAmount(), daysUntilPayment);
                    break;
                case OVERPAID:
                    message = String.format("Payment done by Payment_ID  %s is overpaid. Discrepancy: %s. Please confirm receipt. Payment expected in %d day(s).",
                            paymentId, record.getDiscrepancyAmount(), daysUntilPayment);
                    break;
                case MATCHED:
                default:
                    message = String.format("Payment done by Payment_ID %s has been approved for payment. Paid Amount: %s. Payment expected in %d day(s).",
                            paymentId, record.getActualPaidAmount(), daysUntilPayment);
                    break;
            }

            NotifySupplierForPaymentApprovalOutPutDS outputDS = new NotifySupplierForPaymentApprovalOutPutDS(paymentId, record.getDiscrepancyAmount(), daysUntilPayment,true, message);
            notifications.add(outputDS);
            notifySupplierForPaymentApprovalOutputBoundary.presentNotification(notifications);

        }

        // Step 4: Sort final output and notify
        List<NotifySupplierForPaymentApprovalOutPutDS> sortedNotifications = notifications.stream()
                .sorted(Comparator.comparing(NotifySupplierForPaymentApprovalOutPutDS::getRemainingDate))
                .collect(Collectors.toList());

        logger.info(sortedNotifications.size() + " payment approval(s) notified to suppliers.");

        return sortedNotifications;
    }
}