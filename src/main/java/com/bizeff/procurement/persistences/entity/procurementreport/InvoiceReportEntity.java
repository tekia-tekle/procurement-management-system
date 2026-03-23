package com.bizeff.procurement.persistences.entity.procurementreport;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "invoice_reports")
public class InvoiceReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalInvoices;
    private BigDecimal totalInvoiceAmount;

    @ElementCollection
    @CollectionTable(name = "invoice_amounts_per_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "total_amount")
    private Map<String, BigDecimal> invoiceAmountsPerSupplier;

    @ElementCollection
    @CollectionTable(name = "invoices_per_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "invoice_count")
    private Map<String, Integer> invoicesPerSupplier;


    @OneToOne(mappedBy = "invoiceReport",cascade = CascadeType.ALL)
    private ProcurementReportEntity procurementReportEntity;
    public InvoiceReportEntity() {
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTotalInvoices() {
        return totalInvoices;
    }
    public void setTotalInvoices(Long totalInvoices) {
        this.totalInvoices = totalInvoices;
    }
    public BigDecimal getTotalInvoiceAmount() {
        return totalInvoiceAmount;
    }
    public void setTotalInvoiceAmount(BigDecimal totalInvoiceAmount) {
        this.totalInvoiceAmount = totalInvoiceAmount;
    }
    public Map<String, BigDecimal> getInvoiceAmountsPerSupplier() {
        return invoiceAmountsPerSupplier;
    }
    public void setInvoiceAmountsPerSupplier(Map<String, BigDecimal> invoiceAmountsPerSupplier) {
        this.invoiceAmountsPerSupplier = invoiceAmountsPerSupplier;
    }
    public Map<String, Integer> getInvoicesPerSupplier() {
        return invoicesPerSupplier;
    }
    public void setInvoicesPerSupplier(Map<String, Integer> invoicesPerSupplier) {
        this.invoicesPerSupplier = invoicesPerSupplier;
    }
    public ProcurementReportEntity getProcurementReportEntity() {
        return procurementReportEntity;
    }
    public void setProcurementReportEntity(ProcurementReportEntity procurementReportEntity) {
        this.procurementReportEntity = procurementReportEntity;
    }
    @Override
    public String toString() {
        return "InvoiceReportEntity{" +
                "id=" + id +
                ", totalInvoices=" + totalInvoices +
                ", totalInvoiceAmount=" + totalInvoiceAmount +
                ", invoiceAmountsPerSupplier=" + invoiceAmountsPerSupplier +
                ", invoicesPerSupplier=" + invoicesPerSupplier +
                '}';
    }
}
