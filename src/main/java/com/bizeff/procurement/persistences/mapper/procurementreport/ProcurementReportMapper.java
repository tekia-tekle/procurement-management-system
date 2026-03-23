package com.bizeff.procurement.persistences.mapper.procurementreport;


import com.bizeff.procurement.models.procurementreport.ProcurementReport;
import com.bizeff.procurement.persistences.entity.procurementreport.ProcurementReportEntity;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.UserMapper;

import java.time.LocalDate;

import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateDate;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateString;

public class ProcurementReportMapper {

    // Convert from Entity to Model
    public static ProcurementReport toModel(ProcurementReportEntity reportAnalysisEntity) {
        if (reportAnalysisEntity == null) {
            return null;
        }

        ProcurementReport report = new ProcurementReport();
        report.setId(reportAnalysisEntity.getId());
        report.setReportId(reportAnalysisEntity.getReportId());
        report.setTitle(reportAnalysisEntity.getTitle());
        report.setReportDescription(reportAnalysisEntity.getReportDescription());
        report.setCreatedBy(UserMapper.toDomain(reportAnalysisEntity.getCreatedBy()));
        report.setCreatedAt(reportAnalysisEntity.getCreatedAt() != null ? reportAnalysisEntity.getCreatedAt() : LocalDate.now());
        if (reportAnalysisEntity.getRequisitionReport() != null){
            report.setRequisitionReport(PurchaseRequisitionReportMapper.toModel(reportAnalysisEntity.getRequisitionReport()));
        }
        if (reportAnalysisEntity.getSupplierReport() != null){
            report.setSupplierReport(SupplierReportMapper.toModel(reportAnalysisEntity.getSupplierReport()));
        }
        if (reportAnalysisEntity.getPurchaseOrderReport() != null){
            report.setPurchaseOrderReport(PurchaseOrderReportMapper.toModel(reportAnalysisEntity.getPurchaseOrderReport()));
        }
        if (reportAnalysisEntity.getInvoiceReport() != null){
            report.setInvoiceReport(InvoiceReportMapper.toModel(reportAnalysisEntity.getInvoiceReport()));
        }
        if (reportAnalysisEntity.getDeliveryReceiptReport() != null){
            report.setDeliveryReceiptReport(DeliveryReceiptReportMapper.toModel(reportAnalysisEntity.getDeliveryReceiptReport()));
        }
        if (reportAnalysisEntity.getContractReport() != null){
            report.setContractReport(ContractReportMapper.toModel(reportAnalysisEntity.getContractReport()));
        }
        if (reportAnalysisEntity.getPaymentReconciliationReport() != null){
            report.setPaymentReconciliationReport(PaymentReconciliationReportMapper.toModel(reportAnalysisEntity.getPaymentReconciliationReport()));
        }
        if (reportAnalysisEntity.getSupplierPerformanceReport() != null){
            report.setSupplierPerformanceReport(SupplierPerformanceReportMapper.toModel(reportAnalysisEntity.getSupplierPerformanceReport()));
        }
        return report;
    }

    // Convert from Model to Entity
    public static ProcurementReportEntity toEntity(ProcurementReport report) {
        if (report == null) {
            return null;
        }
        ProcurementReportEntity entity = new ProcurementReportEntity();
        if (report.getId() != null) {
            entity.setId(report.getId());
        }
        entity.setReportId(report.getReportId());
        entity.setTitle(report.getTitle());
        entity.setReportDescription(report.getReportDescription());
        entity.setCreatedBy(UserMapper.toEntity(report.getCreatedBy()));
        entity.setCreatedAt(report.getCreatedAt());
        if (report.getRequisitionReport() != null){
            entity.setRequisitionReport(PurchaseRequisitionReportMapper.toEntity(report.getRequisitionReport()));
        }
        if (report.getSupplierReport() != null){
            entity.setSupplierReport(SupplierReportMapper.toEntity(report.getSupplierReport()));
        }
        if (report.getPurchaseOrderReport() != null){
            entity.setPurchaseOrderReport(PurchaseOrderReportMapper.toEntity(report.getPurchaseOrderReport()));
        }
        if (report.getInvoiceReport() != null){
            entity.setInvoiceReport(InvoiceReportMapper.toEntity(report.getInvoiceReport()));
        }
        if (report.getDeliveryReceiptReport() != null){
            entity.setDeliveryReceiptReport(DeliveryReceiptReportMapper.toEntity(report.getDeliveryReceiptReport()));
        }
        if (report.getContractReport() != null){
            entity.setContractReport(ContractReportMapper.toEntity(report.getContractReport()));
        }
        if (report.getPaymentReconciliationReport() != null){
            entity.setPaymentReconciliationReport(PaymentReconciliationReportMapper.toEntity(report.getPaymentReconciliationReport()));
        }
        if (report.getSupplierPerformanceReport() != null){
            entity.setSupplierPerformanceReport(SupplierPerformanceReportMapper.toEntity(report.getSupplierPerformanceReport()));
        }

        return entity;
    }
    static void validateReport(ProcurementReport report) {
        if (report == null) {
            throw new NullPointerException("Report can't be null or empty.");
        }
        validateString(report.getReportId(), "Report ID");
        validateString(report.getTitle(), "Report Title");
        validateString(report.getReportDescription(), "Report description");
        validateDate(report.getCreatedAt(), "Report Date");
    }
}
