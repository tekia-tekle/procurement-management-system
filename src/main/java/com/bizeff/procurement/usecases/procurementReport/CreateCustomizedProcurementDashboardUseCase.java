package com.bizeff.procurement.usecases.procurementReport;

import com.bizeff.procurement.domaininterfaces.inputboundary.procurementreport.CreateCustomizedProcurementDashboardInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.CreateCustomizedProcurementDashboardInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.ReporterContactDetail;
import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.CreateCustomizedProcurementDashboardOutPutBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.CreateCustomizedProcurementDashboardOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.DeliveryReceiptRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.InvoiceRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.PaymentReconciliationRepository;
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
import com.bizeff.procurement.models.procurementreport.ReportTemplate;
import com.bizeff.procurement.models.procurementreport.TemplateBasedReport;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.purchaserequisition.User;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.services.procurementreport.ProcurementReportCustomizationServices;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.validateNotEmptyList;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateString;

public class CreateCustomizedProcurementDashboardUseCase implements CreateCustomizedProcurementDashboardInputBoundary{
    private TemplateBasedReportRepository templateBasedReportRepository;
    private ReportTemplateRepository reportTemplateRepository;
    private SupplierPerformanceRepository supplierPerformanceRepository;
    private PaymentReconciliationRepository paymentReconciliationRepository;
    private DeliveryReceiptRepository deliveryReceiptRepository;
    private InvoiceRepository invoiceRepository;
    private ContractRepository contractRepository;
    private PurchaseOrderRepository purchaseOrderRepository;
    private SupplierRepository supplierRepository;
    private PurchaseRequisitionRepository purchaseRequisitionRepository;
    private UserRepository userRepository;
    private ProcurementReportCustomizationServices procurementReportCustomizationServices;
    private CreateCustomizedProcurementDashboardOutPutBoundary createCustomizedProcurementDashboardOutPutBoundary;
    public CreateCustomizedProcurementDashboardUseCase(
            TemplateBasedReportRepository templateBasedReportRepository,
            ReportTemplateRepository reportTemplateRepository,
            SupplierPerformanceRepository supplierPerformanceRepository,
            PaymentReconciliationRepository paymentReconciliationRepository,
            DeliveryReceiptRepository deliveryReceiptRepository,
            InvoiceRepository invoiceRepository,
            ContractRepository contractRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            SupplierRepository supplierRepository,
            PurchaseRequisitionRepository purchaseRequisitionRepository,
            UserRepository userRepository,
            ProcurementReportCustomizationServices procurementReportCustomizationServices,
            CreateCustomizedProcurementDashboardOutPutBoundary createCustomizedProcurementDashboardOutPutBoundary){
        this.templateBasedReportRepository = templateBasedReportRepository;
        this.reportTemplateRepository = reportTemplateRepository;
        this.supplierPerformanceRepository = supplierPerformanceRepository;
        this.paymentReconciliationRepository = paymentReconciliationRepository;
        this.deliveryReceiptRepository = deliveryReceiptRepository;
        this.invoiceRepository = invoiceRepository;
        this.contractRepository = contractRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.purchaseRequisitionRepository = purchaseRequisitionRepository;
        this.userRepository = userRepository;
        this.procurementReportCustomizationServices = procurementReportCustomizationServices;
        this.createCustomizedProcurementDashboardOutPutBoundary = createCustomizedProcurementDashboardOutPutBoundary;
    }

    @Override
    public CreateCustomizedProcurementDashboardOutputDS createCustomizedProcurementDashboard(CreateCustomizedProcurementDashboardInputDS inputDS){
        validateInputDS(inputDS);
        User user = userRepository.findByPhoneNumber(inputDS.getReporterContactDetail().getPhoneNumber()).orElseThrow(()-> new IllegalArgumentException("there is no user that is stored with this phone number : "+ inputDS.getReporterContactDetail().getPhoneNumber()));

        for(ProcurementActivity procurementActivity : inputDS.getProcurementActivities()) {
            switch (procurementActivity){
                case PURCHASE_REQUISITION -> {
                    List<PurchaseRequisition> purchaseRequisitions = purchaseRequisitionRepository.findAll();
                    if (purchaseRequisitions == null || purchaseRequisitions.isEmpty()){
                        throw new IllegalArgumentException("there are no purchase requisitions stored");
                    }
                    procurementReportCustomizationServices.getPurchaseRequisitions().addAll(purchaseRequisitions);
                }
                case SUPPLIER_MANAGEMENT -> {
                    List<Supplier> suppliers = supplierRepository.findAll();
                    if (suppliers == null || suppliers.isEmpty()){
                        throw new IllegalArgumentException("there are no suppliers stored");
                    }
                    procurementReportCustomizationServices.getSuppliers().addAll(suppliers);
                }
                case PURCHASE_ORDER -> {
                    List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
                    if (purchaseOrders == null || purchaseOrders.isEmpty()){
                        throw new IllegalArgumentException("there are no purchase orders stored");
                    }
                    procurementReportCustomizationServices.getPurchaseOrders().addAll(purchaseOrders);
                }
                case CONTRACT_MANAGEMENT -> {
                    List<Contract> contracts = contractRepository.findAll();
                    if (contracts == null || contracts.isEmpty()){
                        throw new IllegalArgumentException("there are no contracts stored");
                    }
                    procurementReportCustomizationServices.getContracts().addAll(contracts);
                }
                case INVOICE_MANAGEMENT -> {
                    List<Invoice> invoices = invoiceRepository.findAll();
                    if (invoices == null || invoices.isEmpty()){
                        throw new IllegalArgumentException("there are no invoices stored");
                    }
                    procurementReportCustomizationServices.getInvoices().addAll(invoices);
                }
                case DELIVERY_RECEIPT -> {
                    List<DeliveryReceipt> deliveryReceipts = deliveryReceiptRepository.findAll();
                    if (deliveryReceipts == null || deliveryReceipts.isEmpty()){
                        throw new IllegalArgumentException("there are no delivery receipts stored");
                    }
                    procurementReportCustomizationServices.getDeliveryReceipts().addAll(deliveryReceipts);
                }
                case PAYMENT_RECONCILIATION -> {
                    List<PaymentReconciliation> paymentReconciliations = paymentReconciliationRepository.findAll();
                    if (paymentReconciliations == null || paymentReconciliations.isEmpty()){
                        throw new IllegalArgumentException("there are no payment reconciliations stored");
                    }
                    procurementReportCustomizationServices.getInvoicePayments().addAll(paymentReconciliations);
                }
                case SUPPLIER_PERFORMANCE -> {
                    List<SupplierPerformance> supplierPerformances = supplierPerformanceRepository.findAll();
                    if (supplierPerformances == null || supplierPerformances.isEmpty()){
                        throw new IllegalArgumentException("there are no supplier performances stored");
                    }
                    procurementReportCustomizationServices.getSupplierPerformances().addAll(supplierPerformances);
                }
            }
        }
        Optional<ReportTemplate> reportTemplate = reportTemplateRepository.findBytemplateId(inputDS.getTemplateId());
        if(!reportTemplate.isPresent()){
            ReportTemplate newReportTemplate = procurementReportCustomizationServices.addReportTemplate(inputDS.getTemplateId(), inputDS.getTemplateName(), "Customized Dashboard ", inputDS.getSelectedFields(), LocalDate.now(), user);
            reportTemplateRepository.save(newReportTemplate);
            procurementReportCustomizationServices.getReportTemplates().put(newReportTemplate.getTemplateId(),newReportTemplate);
            TemplateBasedReport templateBasedReport = procurementReportCustomizationServices.buildCustomizedReport(newReportTemplate.getTemplateId(), inputDS.getProcurementActivities());
            templateBasedReportRepository.save(templateBasedReport);
            Map<String, Object> reportData = templateBasedReport.getData();
            createCustomizedProcurementDashboardOutPutBoundary.presentCustomizedProcurementDashboard(new CreateCustomizedProcurementDashboardOutputDS(newReportTemplate.getTemplateId(), inputDS.getTemplateName(),templateBasedReport.getProcurementActivities(),reportData,user, LocalDate.now()));
            return new CreateCustomizedProcurementDashboardOutputDS(newReportTemplate.getTemplateId(),inputDS.getTemplateName(),templateBasedReport.getProcurementActivities(), reportData,user, LocalDate.now());
        }
        ReportTemplate reportTemplate1 = reportTemplate.get();
        // check if the input fields are the same as the report template fields
        boolean matchedFields = reportTemplate1.getSelectedFields().equals(inputDS.getSelectedFields());
        if (!matchedFields){
            reportTemplate1.setSelectedFields(inputDS.getSelectedFields());
            reportTemplateRepository.update(reportTemplate1);
        }
        procurementReportCustomizationServices.getReportTemplates().put(reportTemplate1.getTemplateId(),reportTemplate1);
        TemplateBasedReport templateBasedReport = procurementReportCustomizationServices.buildCustomizedReport(reportTemplate1.getTemplateId(), inputDS.getProcurementActivities());
        templateBasedReportRepository.save(templateBasedReport);
        Map<String, Object> reportData = templateBasedReport.getData();

        CreateCustomizedProcurementDashboardOutputDS outputDS = new CreateCustomizedProcurementDashboardOutputDS(reportTemplate1.getTemplateId(),inputDS.getTemplateName(),templateBasedReport.getProcurementActivities(), reportData, user, LocalDate.now());

        createCustomizedProcurementDashboardOutPutBoundary.presentCustomizedProcurementDashboard(outputDS);

        return outputDS;
    }
    public void validateInputDS(CreateCustomizedProcurementDashboardInputDS inputDS){
        if (inputDS == null){
            throw new IllegalArgumentException("inputDS cannot be null");
        }
        validateReporterContactDetail(inputDS.getReporterContactDetail());
        validateString(inputDS.getTemplateId(), "Template Id");
        validateString(inputDS.getTemplateName(), "Template Name");
        validateNotEmptyList(inputDS.getProcurementActivities(), "Procurement Activities");
        validateNotEmptyList(inputDS.getSelectedFields(), "Selected Fields");
    }
    public void validateReporterContactDetail(ReporterContactDetail contactDetail){
        if (contactDetail == null){
            throw new IllegalArgumentException("Reporter Contact Detail cannot be null");
        }
        validateString(contactDetail.getFullName(), "Reporter Full Name");
        validateString(contactDetail.getEmail(), "Reporter Email");
        validateString(contactDetail.getPhoneNumber(), "Reporter Phone Number");
        validateString(contactDetail.getRole(), "Reporter role");
    }
}
