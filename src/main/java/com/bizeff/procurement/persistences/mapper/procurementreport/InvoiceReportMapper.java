package com.bizeff.procurement.persistences.mapper.procurementreport;

import com.bizeff.procurement.models.procurementreport.InvoiceReport;
import com.bizeff.procurement.persistences.entity.procurementreport.InvoiceReportEntity;

public class InvoiceReportMapper {
    public static InvoiceReportEntity toEntity(InvoiceReport invoiceReport){
        InvoiceReportEntity invoiceReportEntity = new InvoiceReportEntity();
        if (invoiceReport.getId() != null){
            invoiceReportEntity.setId(invoiceReport.getId());
        }
        if (invoiceReport.getTotalInvoices() != 0){
            invoiceReportEntity.setTotalInvoices(invoiceReport.getTotalInvoices());
        }
        invoiceReportEntity.setTotalInvoiceAmount(invoiceReport.getTotalInvoicedAmount());
        invoiceReportEntity.setInvoiceAmountsPerSupplier(invoiceReport.getTotalInvoiceCostsPerSupplier());
        invoiceReportEntity.setInvoicesPerSupplier(invoiceReport.getInvoicesFromSupplier());


        return invoiceReportEntity;
    }
    public static InvoiceReport toModel(InvoiceReportEntity invoiceReportEntity){
        InvoiceReport invoiceReport = new InvoiceReport();
        invoiceReport.setId(invoiceReportEntity.getId());
        invoiceReport.setTotalInvoices(invoiceReportEntity.getTotalInvoices());
        invoiceReport.setTotalInvoicedAmount(invoiceReportEntity.getTotalInvoiceAmount());
        invoiceReport.setTotalInvoiceCostsPerSupplier(invoiceReportEntity.getInvoiceAmountsPerSupplier());
        invoiceReport.setInvoicesFromSupplier(invoiceReportEntity.getInvoicesPerSupplier());

        return invoiceReport;
    }
}
