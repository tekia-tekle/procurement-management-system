package com.bizeff.procurement.models.procurementreport;

import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.models.purchaserequisition.User;

import java.time.LocalDate;
import java.util.Map;

public class ProcurementReport {
    private Long id;
    private String reportId;
    private String title;
    private String reportDescription;
    private User createdBy;
    private LocalDate createdAt;


    // procurement activity based reports
    private PurchaseRequisitionReport requisitionReport;
    private SupplierReport supplierReport;
    private PurchaseOrderReport purchaseOrderReport;
    private ContractReport contractReport;
    private InvoiceReport invoiceReport;
    private DeliveryReceiptReport deliveryReceiptReport;
    private PaymentReconciliationReport paymentReconciliationReport;
    private SupplierPerformanceReport supplierPerformanceReport;
    public ProcurementReport(){
    }
    public ProcurementReport(
                             String title,
                             String reportDescription,
                             User createdBy,
                             LocalDate createdAt)
    {
        this.reportId = IdGenerator.generateId("RPT");
        this.title = title;
        this.reportDescription = reportDescription;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getReportId() {
        return reportId;
    }
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getReportDescription() {
        return reportDescription;
    }
    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }
    public User getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    public PurchaseRequisitionReport getRequisitionReport() {
        return requisitionReport;
    }
    public void setRequisitionReport(PurchaseRequisitionReport requisitionReport) {
        this.requisitionReport = requisitionReport;
    }
    public PurchaseOrderReport getPurchaseOrderReport() {
        return purchaseOrderReport;
    }
    public void setPurchaseOrderReport(PurchaseOrderReport purchaseOrderReport) {
        this.purchaseOrderReport = purchaseOrderReport;
    }
    public SupplierReport getSupplierReport() {
        return supplierReport;
    }
    public void setSupplierReport(SupplierReport supplierReport) {
        this.supplierReport = supplierReport;
    }
    public ContractReport getContractReport() {
        return contractReport;
    }
    public void setContractReport(ContractReport contractReport) {
        this.contractReport = contractReport;
    }
    public InvoiceReport getInvoiceReport() {
        return invoiceReport;
    }
    public void setInvoiceReport(InvoiceReport invoiceReport) {
        this.invoiceReport = invoiceReport;
    }
    public DeliveryReceiptReport getDeliveryReceiptReport() {
        return deliveryReceiptReport;
    }
    public void setDeliveryReceiptReport(DeliveryReceiptReport deliveryReceiptReport) {
        this.deliveryReceiptReport = deliveryReceiptReport;
    }
    public PaymentReconciliationReport getPaymentReconciliationReport() {
        return paymentReconciliationReport;
    }
    public void setPaymentReconciliationReport(PaymentReconciliationReport paymentReconciliationReport) {
        this.paymentReconciliationReport = paymentReconciliationReport;
    }
    public SupplierPerformanceReport getSupplierPerformanceReport() {
        return supplierPerformanceReport;
    }
    public void setSupplierPerformanceReport(SupplierPerformanceReport supplierPerformanceReport) {
        this.supplierPerformanceReport = supplierPerformanceReport;
    }

    //toString
    @Override
    public String toString() {
        return "ProcurementReport{" +
                "id=" + id +
                ", reportId='" + reportId + '\'' +
                ", title='" + title + '\'' +
                ", reportDescription='" + reportDescription + '\'' +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", requisitionReport=" + requisitionReport +
                ", supplierReport=" + supplierReport +
                ", purchaseOrderReport=" + purchaseOrderReport +
                ", contractReport=" + contractReport +
                ", invoiceReport=" + invoiceReport +
                ", deliveryReceiptReport=" + deliveryReceiptReport +
                ", paymentReconciliationReport=" + paymentReconciliationReport +
                ", supplierPerformanceReport=" + supplierPerformanceReport +
                '}';
    }
}
