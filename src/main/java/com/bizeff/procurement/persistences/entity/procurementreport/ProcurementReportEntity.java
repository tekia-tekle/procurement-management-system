package com.bizeff.procurement.persistences.entity.procurementreport;

import com.bizeff.procurement.persistences.entity.purchaserequisition.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "procurement_reports")
public class ProcurementReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "report_id", nullable = false, unique = true)
    private String reportId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "report_description", nullable = false)
    private String reportDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity createdBy;
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;


    // Optional sub-reports
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requisition_report_id")
    private PurchaseRequisitionReportEntity requisitionReport;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_report_id")
    private SupplierReportEntity supplierReport;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_order_report_id")
    private PurchaseOrderReportEntity purchaseOrderReport;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_report_id")
    private ContractReportEntity contractReport;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_report_id")
    private InvoiceReportEntity invoiceReport;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_receipt_report_id")
    private DeliveryReceiptReportEntity deliveryReceiptReport;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_reconciliation_report_id")
    private PaymentReconciliationReportEntity paymentReconciliationReport;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_performance_report_id")
    private SupplierPerformanceReportEntity supplierPerformanceReport;
    public ProcurementReportEntity() {}
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
    public UserEntity getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    public PurchaseRequisitionReportEntity getRequisitionReport() {
        return requisitionReport;
    }
    public void setRequisitionReport(PurchaseRequisitionReportEntity requisitionReport) {
        this.requisitionReport = requisitionReport;
    }
    public SupplierReportEntity getSupplierReport() {
        return supplierReport;
    }
    public void setSupplierReport(SupplierReportEntity supplierReport) {
        this.supplierReport = supplierReport;
    }
    public PurchaseOrderReportEntity getPurchaseOrderReport() {
        return purchaseOrderReport;
    }
    public void setPurchaseOrderReport(PurchaseOrderReportEntity purchaseOrderReport) {
        this.purchaseOrderReport = purchaseOrderReport;
    }
    public ContractReportEntity getContractReport() {
        return contractReport;
    }
    public void setContractReport(ContractReportEntity contractReport) {
        this.contractReport = contractReport;
    }
    public InvoiceReportEntity getInvoiceReport() {
        return invoiceReport;
    }
    public void setInvoiceReport(InvoiceReportEntity invoiceReport) {
        this.invoiceReport = invoiceReport;
    }
    public DeliveryReceiptReportEntity getDeliveryReceiptReport() {
        return deliveryReceiptReport;
    }
    public void setDeliveryReceiptReport(DeliveryReceiptReportEntity deliveryReceiptReport) {
        this.deliveryReceiptReport = deliveryReceiptReport;
    }
    public PaymentReconciliationReportEntity getPaymentReconciliationReport() {
        return paymentReconciliationReport;
    }
    public void setPaymentReconciliationReport(PaymentReconciliationReportEntity paymentReconciliationReport) {
        this.paymentReconciliationReport = paymentReconciliationReport;
    }
    public SupplierPerformanceReportEntity getSupplierPerformanceReport() {
        return supplierPerformanceReport;
    }
    public void setSupplierPerformanceReport(SupplierPerformanceReportEntity supplierPerformanceReport) {
        this.supplierPerformanceReport = supplierPerformanceReport;
    }
}