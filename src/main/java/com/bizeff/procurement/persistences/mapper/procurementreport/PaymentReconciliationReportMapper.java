package com.bizeff.procurement.persistences.mapper.procurementreport;

import com.bizeff.procurement.models.procurementreport.PaymentReconciliationReport;
import com.bizeff.procurement.persistences.entity.procurementreport.PaymentReconciliationReportEntity;

public class PaymentReconciliationReportMapper {
    public static PaymentReconciliationReportEntity toEntity(PaymentReconciliationReport paymentReconciliationReport){
        PaymentReconciliationReportEntity paymentReconciliationReportEntity = new PaymentReconciliationReportEntity();
        if (paymentReconciliationReport.getId() != null){
            paymentReconciliationReportEntity.setId(paymentReconciliationReport.getId());
        }
        paymentReconciliationReportEntity.setTotalReconciliations(paymentReconciliationReport.getTotalReconciliations());
        paymentReconciliationReportEntity.setTotalPaidAmount(paymentReconciliationReport.getTotalPaidAmount());
        paymentReconciliationReportEntity.setTotalExpectedAmount(paymentReconciliationReport.getTotalExpectedAmount());
        paymentReconciliationReportEntity.setTotalDiscrepancyAmount(paymentReconciliationReport.getTotalDiscrepancyAmount());
        paymentReconciliationReportEntity.setReconciliationsByStatus(paymentReconciliationReport.getReconciliationByStatus());
        paymentReconciliationReportEntity.setReconciliationsPerSupplier(paymentReconciliationReport.getReconciliationsBySupplier());
        paymentReconciliationReportEntity.setPaidAmountsPerSupplier(paymentReconciliationReport.getTotalPaidAmountPerSupplier());
        paymentReconciliationReportEntity.setDiscrepancyAmountsPerSupplier(paymentReconciliationReport.getTotalDiscrepancyPerSupplier());

        return paymentReconciliationReportEntity;

    }
    public static PaymentReconciliationReport toModel(PaymentReconciliationReportEntity paymentReconciliationReportEntity){
        PaymentReconciliationReport paymentReconciliationReport = new PaymentReconciliationReport();
        paymentReconciliationReport.setId(paymentReconciliationReportEntity.getId());
        paymentReconciliationReport.setTotalReconciliations(paymentReconciliationReportEntity.getTotalReconciliations());
        paymentReconciliationReport.setTotalPaidAmount(paymentReconciliationReportEntity.getTotalPaidAmount());
        paymentReconciliationReport.setTotalExpectedAmount(paymentReconciliationReportEntity.getTotalExpectedAmount());
        paymentReconciliationReport.setTotalDiscrepancyAmount(paymentReconciliationReportEntity.getTotalDiscrepancyAmount());
        paymentReconciliationReport.setReconciliationByStatus(paymentReconciliationReportEntity.getReconciliationsByStatus());
        paymentReconciliationReport.setReconciliationsBySupplier(paymentReconciliationReportEntity.getReconciliationsPerSupplier());
        paymentReconciliationReport.setTotalPaidAmountPerSupplier(paymentReconciliationReportEntity.getPaidAmountsPerSupplier());
        paymentReconciliationReport.setTotalDiscrepancyPerSupplier(paymentReconciliationReportEntity.getDiscrepancyAmountsPerSupplier());
        return paymentReconciliationReport;
    }
}
