package com.bizeff.procurement.usecases.procurementReport;

import com.bizeff.procurement.domaininterfaces.inputboundary.procurementreport.ExportProcurementDataInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.ExportProcurementDataInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.ReporterContactDetail;
import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.ExportProcurementDataOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.ExportedProcurementDataOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.DeliveryReceiptRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.InvoiceRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.PaymentReconciliationRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.ProcurementReportRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.ReportTemplateRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.TemplateBasedReportRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.UserRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;
import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;
import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;
import com.bizeff.procurement.models.procurementreport.ProcurementReport;
import com.bizeff.procurement.models.procurementreport.ReportTemplate;
import com.bizeff.procurement.models.procurementreport.TemplateBasedReport;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.purchaserequisition.User;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.services.procurementreport.ProcurementReportCustomizationServices;
import com.bizeff.procurement.services.procurementreport.ProcurementReportGenerationServices;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.invoicepaymentreconciliation.ContractPaymentTermsEnforcementService.validateDateOrder;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.*;
import static com.bizeff.procurement.usecases.contractmanagement.CreateContractUseCase.validateNotEmptyList;

public class ExportProcurementDataUseCase implements ExportProcurementDataInputBoundary {
    private TemplateBasedReportRepository templateBasedReportRepository;
    private ProcurementReportRepository procurementReportRepository;
    private ReportTemplateRepository reportTemplateRepository;
    private PurchaseRequisitionRepository purchaseRequisitionRepository;
    private PurchaseOrderRepository purchaseOrderRepository;
    private SupplierRepository supplierRepository;
    private SupplierPerformanceRepository supplierPerformanceRepository;
    private PaymentReconciliationRepository paymentReconciliationRepository;
    private ContractRepository contractRepository;
    private InvoiceRepository invoiceRepository;
    private DeliveryReceiptRepository deliveryReceiptRepository;
    private UserRepository userRepository;
    private ProcurementReportGenerationServices procurementReportGenerationServices;
    private ProcurementReportCustomizationServices procurementReportCustomizationServices;
    private ExportProcurementDataOutputBoundary exportProcurementDataOutputBoundary;
    public ExportProcurementDataUseCase(
            TemplateBasedReportRepository templateBasedReportRepository,
            ProcurementReportRepository procurementReportRepository,
            ReportTemplateRepository reportTemplateRepository,
            PurchaseRequisitionRepository purchaseRequisitionRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            SupplierRepository supplierRepository,
            SupplierPerformanceRepository supplierPerformanceRepository,
            PaymentReconciliationRepository paymentReconciliationRepository,
            ContractRepository contractRepository,
            InvoiceRepository invoiceRepository,
            DeliveryReceiptRepository deliveryReceiptRepository,
            UserRepository userRepository,
            ProcurementReportGenerationServices procurementReportGenerationServices,
            ProcurementReportCustomizationServices procurementReportCustomizationServices,
            ExportProcurementDataOutputBoundary exportProcurementDataOutputBoundary)
    {
        this.templateBasedReportRepository = templateBasedReportRepository;
        this.procurementReportRepository = procurementReportRepository;
        this.reportTemplateRepository = reportTemplateRepository;
        this.purchaseRequisitionRepository = purchaseRequisitionRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.supplierPerformanceRepository = supplierPerformanceRepository;
        this.paymentReconciliationRepository = paymentReconciliationRepository;
        this.contractRepository = contractRepository;
        this.invoiceRepository = invoiceRepository;
        this.deliveryReceiptRepository = deliveryReceiptRepository;
        this.userRepository = userRepository;
        this.procurementReportGenerationServices = procurementReportGenerationServices;
        this.procurementReportCustomizationServices = procurementReportCustomizationServices;
        this.exportProcurementDataOutputBoundary = exportProcurementDataOutputBoundary;

    }
    @Override
    public ExportedProcurementDataOutputDS exportProcurementData(ExportProcurementDataInputDS exportProcurementDataInputDS) {
        validateExportProcurementDataInputDS(exportProcurementDataInputDS);
        User user = userRepository.findByPhoneNumber(exportProcurementDataInputDS.getReporterContactDetail().getPhoneNumber())
                .orElseThrow(()-> new IllegalArgumentException("there is no user that is stored with this phone number"));

        for(ProcurementActivity activity : exportProcurementDataInputDS.getIncludedActivities()){
            switch (activity){
                case PURCHASE_REQUISITION -> {
                    List<PurchaseRequisition> purchaseRequisitionList = purchaseRequisitionRepository.findAll().stream()
                            .filter(purchaseRequisition -> !purchaseRequisition.getRequisitionDate().isAfter(exportProcurementDataInputDS.getEndDate())
                                    && !purchaseRequisition.getRequisitionDate().isBefore(exportProcurementDataInputDS.getStartDate()))
                            .collect(Collectors.toList());
                    if(purchaseRequisitionList != null){
                        procurementReportGenerationServices.getRequisitions().addAll(purchaseRequisitionList);
                        procurementReportCustomizationServices.getPurchaseRequisitions().addAll(purchaseRequisitionList);

                    }
                }
                case PURCHASE_ORDER -> {
                    List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll().stream()
                            .filter(purchaseOrder -> !purchaseOrder.getOrderDate().isAfter(exportProcurementDataInputDS.getEndDate())
                                    && !purchaseOrder.getOrderDate().isBefore(exportProcurementDataInputDS.getStartDate()))
                            .collect(Collectors.toList());
                    if(purchaseOrderList != null){
                        procurementReportGenerationServices.getPurchaseOrders().addAll(purchaseOrderList);
                        procurementReportCustomizationServices.getPurchaseOrders().addAll(purchaseOrderList);
                    }
                }
                case SUPPLIER_MANAGEMENT -> {
                    List<Supplier> supplierList = supplierRepository.findAll().stream()
                            .filter(supplier -> !supplier.getRegistrationDate().isAfter(exportProcurementDataInputDS.getEndDate())
                                    && !supplier.getRegistrationDate().isBefore(exportProcurementDataInputDS.getStartDate()))
                            .collect(Collectors.toList());

                    if(supplierList != null){
                        procurementReportGenerationServices.getSuppliers().addAll(supplierList);
                        procurementReportCustomizationServices.getSuppliers().addAll(supplierList);
                    }
                }
                case CONTRACT_MANAGEMENT -> {
                    List<Contract> contractList = contractRepository.findAll().stream()
                            .filter(contract -> !contract.getStartDate().isAfter(exportProcurementDataInputDS.getEndDate())
                                    && !contract.getStartDate().isBefore(exportProcurementDataInputDS.getStartDate()))
                            .collect(Collectors.toList());
                    if(contractList != null){
                        procurementReportGenerationServices.getContracts().addAll(contractList);
                        procurementReportCustomizationServices.getContracts().addAll(contractList);
                    }
                }
                case INVOICE_MANAGEMENT -> {
                    List<Invoice> invoiceList = invoiceRepository.findAll().stream()
                            .filter(invoice -> !invoice.getInvoiceDate().isAfter(exportProcurementDataInputDS.getEndDate())
                                    && !invoice.getInvoiceDate().isBefore(exportProcurementDataInputDS.getStartDate()))
                            .collect(Collectors.toList());
                    if(invoiceList != null){
                        procurementReportGenerationServices.getInvoiceList().addAll(invoiceList);
                        procurementReportCustomizationServices.getInvoices().addAll(invoiceList);
                    }
                }
                case DELIVERY_RECEIPT -> {
                    List<DeliveryReceipt> deliveryReceiptList = deliveryReceiptRepository.findAll().stream()
                            .filter(deliveryReceipt -> !deliveryReceipt.getDeliveryDate().isAfter(exportProcurementDataInputDS.getEndDate())
                                    && !deliveryReceipt.getDeliveryDate().isBefore(exportProcurementDataInputDS.getStartDate()))
                            .collect(Collectors.toList());
                    if(deliveryReceiptList != null){
                        procurementReportGenerationServices.getDeliveryReceipts().addAll(deliveryReceiptList);
                        procurementReportCustomizationServices.getDeliveryReceipts().addAll(deliveryReceiptList);
                    }
                }
                case PAYMENT_RECONCILIATION -> {
                    List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll().stream()
                            .filter(paymentReconciliation -> !paymentReconciliation.getPaymentDate().isAfter(exportProcurementDataInputDS.getEndDate())
                                    && !paymentReconciliation.getPaymentDate().isBefore(exportProcurementDataInputDS.getStartDate()))
                            .collect(Collectors.toList());
                    if(paymentReconciliationList != null){
                        procurementReportGenerationServices.getPaymentReconciliations().addAll(paymentReconciliationList);
                        procurementReportCustomizationServices.getInvoicePayments().addAll(paymentReconciliationList);
                    }
                }
                case SUPPLIER_PERFORMANCE -> {
                    List<SupplierPerformance> supplierPerformanceList = supplierPerformanceRepository.findAll().stream()
                            .filter(supplierPerformance -> !supplierPerformance.getEvaluationDate().isAfter(exportProcurementDataInputDS.getEndDate())
                                    && !supplierPerformance.getEvaluationDate().isBefore(exportProcurementDataInputDS.getStartDate()))
                            .collect(Collectors.toList());
                    if(supplierPerformanceList != null){
                        procurementReportGenerationServices.getSupplierPerformances().addAll(supplierPerformanceList);
                        procurementReportCustomizationServices.getSupplierPerformances().addAll(supplierPerformanceList);
                    }
                }
            }
        }
        Optional<ReportTemplate> existedReportTemplate = reportTemplateRepository.findBytemplateId(exportProcurementDataInputDS.getTemplateId());
        ReportTemplate reportTemplate;
        if(existedReportTemplate.isEmpty()){
            reportTemplate = new ReportTemplate(exportProcurementDataInputDS.getTemplateId(), "Template for"+exportProcurementDataInputDS.getIncludedActivities(), "Customized Dashboard ", exportProcurementDataInputDS.getSelectedFields(), LocalDate.now(), user);
            reportTemplateRepository.save(reportTemplate);
            procurementReportCustomizationServices.getReportTemplates().put(reportTemplate.getTemplateId(), reportTemplate);
            ProcurementReport procurementReport = procurementReportGenerationServices.generateTimeBasedProcurementReport(user,exportProcurementDataInputDS.getIncludedActivities(),exportProcurementDataInputDS.getStartDate(),exportProcurementDataInputDS.getEndDate(), LocalDate.now(),exportProcurementDataInputDS.getReportTitle(),exportProcurementDataInputDS.getReportDescription());

            ProcurementReport savedProcurementReport = procurementReportRepository.save(procurementReport);
            TemplateBasedReport templateBasedReport = procurementReportCustomizationServices.buildCustomizedReport(reportTemplate.getTemplateId(), exportProcurementDataInputDS.getIncludedActivities());
            templateBasedReportRepository.save(templateBasedReport);
            Map<String, Object> reportData = templateBasedReport.getData();

            ExportedProcurementDataOutputDS exportedProcurementDataOutputDS = new ExportedProcurementDataOutputDS(savedProcurementReport.getReportId(),exportProcurementDataInputDS.getReportTitle() , exportProcurementDataInputDS.getStartDate(), exportProcurementDataInputDS.getEndDate(), LocalDate.now(), user.getFullName(), reportData,exportProcurementDataInputDS.getExportFormat());
            exportProcurementDataOutputBoundary.presentExportedProcurementData(exportedProcurementDataOutputDS);
            return exportedProcurementDataOutputDS;

        }

        reportTemplate = existedReportTemplate.get();
        boolean matchedFields = reportTemplate.getSelectedFields().equals(exportProcurementDataInputDS.getSelectedFields());
        if (!matchedFields){
            reportTemplate.setSelectedFields(exportProcurementDataInputDS.getSelectedFields());
            reportTemplateRepository.update(reportTemplate);
        }
        procurementReportCustomizationServices.getReportTemplates().put(reportTemplate.getTemplateId(), reportTemplate);
        ProcurementReport procurementReport = procurementReportGenerationServices.generateTimeBasedProcurementReport(user,exportProcurementDataInputDS.getIncludedActivities(),exportProcurementDataInputDS.getStartDate(),exportProcurementDataInputDS.getEndDate(), LocalDate.now(),exportProcurementDataInputDS.getReportTitle(),exportProcurementDataInputDS.getReportDescription());

        ProcurementReport savedProcurementReport = procurementReportRepository.save(procurementReport);
        TemplateBasedReport templateBasedReport = procurementReportCustomizationServices.buildCustomizedReport(reportTemplate.getTemplateId(), exportProcurementDataInputDS.getIncludedActivities());
        templateBasedReportRepository.save(templateBasedReport);
        Map<String, Object> reportData = templateBasedReport.getData();

        ExportedProcurementDataOutputDS exportedProcurementDataOutputDS = new ExportedProcurementDataOutputDS(savedProcurementReport.getReportId(),exportProcurementDataInputDS.getReportTitle() , exportProcurementDataInputDS.getStartDate(), exportProcurementDataInputDS.getEndDate(), LocalDate.now(), user.getFullName(), reportData,exportProcurementDataInputDS.getExportFormat());
        exportProcurementDataOutputBoundary.presentExportedProcurementData(exportedProcurementDataOutputDS);
        return exportedProcurementDataOutputDS;
    }

    public void validateExportProcurementDataInputDS(ExportProcurementDataInputDS exportProcurementDataInputDS){
        if (exportProcurementDataInputDS == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        validateReporterContactDetail(exportProcurementDataInputDS.getReporterContactDetail());
        validateString(exportProcurementDataInputDS.getTemplateId(), "Template ID");
        validateString(exportProcurementDataInputDS.getReportTitle(), "Report Title");
        validateString(exportProcurementDataInputDS.getReportDescription(), "Report Description");
        validateDate(exportProcurementDataInputDS.getStartDate(), "Start Date");
        validateDate(exportProcurementDataInputDS.getEndDate(), "End Date");
        validateDateOrder(exportProcurementDataInputDS.getStartDate(), exportProcurementDataInputDS.getEndDate());
        validateNotEmptyList(exportProcurementDataInputDS.getIncludedActivities(), "Included Activities");
        validateNotEmptyList(exportProcurementDataInputDS.getSelectedFields(), "selected Fields");
        validateNotNull(exportProcurementDataInputDS.getExportFormat(), "Export Format");
    }
    public void validateReporterContactDetail(ReporterContactDetail reporterContactDetail){
        if(reporterContactDetail == null){
            throw new IllegalArgumentException("Contact detail cannot be null");
        }
        validateString(reporterContactDetail.getFullName(), "Full Name");
        validateString(reporterContactDetail.getEmail(), "Email");
        validateString(reporterContactDetail.getPhoneNumber(), "Phone Number");
        validateString(reporterContactDetail.getRole(), "Role");
    }
}
