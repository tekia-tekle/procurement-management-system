package com.bizeff.procurement.models.procurementreport;

import java.math.BigDecimal;
import java.util.Map;

public class InvoiceReport {
    private Long id;
    private Long totalInvoices;
    private BigDecimal totalInvoicedAmount;
    private Map<String, BigDecimal> totalInvoiceCostsPerSupplier;
    private Map<String, Integer> invoicesFromSupplier;
    public InvoiceReport(){}

    public InvoiceReport(Long totalInvoices,
                         BigDecimal totalInvoicedAmount,
                         Map<String, BigDecimal> totalInvoiceCostsPerSupplier,
                         Map<String, Integer> invoicesFromSupplier)
    {
        this.totalInvoices = totalInvoices;
        this.totalInvoicedAmount = totalInvoicedAmount;
        this.totalInvoiceCostsPerSupplier = totalInvoiceCostsPerSupplier;
        this.invoicesFromSupplier = invoicesFromSupplier;
    }
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
    public BigDecimal getTotalInvoicedAmount() {
        return totalInvoicedAmount;
    }
    public void setTotalInvoicedAmount(BigDecimal totalInvoicedAmount) {
        this.totalInvoicedAmount = totalInvoicedAmount;
    }
    public Map<String, BigDecimal> getTotalInvoiceCostsPerSupplier() {
        return totalInvoiceCostsPerSupplier;
    }
    public void setTotalInvoiceCostsPerSupplier(Map<String, BigDecimal> totalInvoiceCostsPerSupplier) {
        this.totalInvoiceCostsPerSupplier = totalInvoiceCostsPerSupplier;
    }
    public Map<String, Integer> getInvoicesFromSupplier() {
        return invoicesFromSupplier;
    }
    public void setInvoicesFromSupplier(Map<String, Integer> invoicesFromSupplier) {
        this.invoicesFromSupplier = invoicesFromSupplier;
    }
}
