package com.bizeff.procurement.webapi.configuration;

import com.bizeff.procurement.domaininterfaces.inputboundary.procurementreport.CreateCustomizedProcurementDashboardInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.procurementreport.ExportProcurementDataInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.CreateCustomizedProcurementDashboardOutPutBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.ExportProcurementDataOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.DeliveryReceiptRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.InvoiceRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.PaymentReconciliationRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.*;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.DepartmentRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.UserRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.persistences.jparepository.procurementreport.*;
import com.bizeff.procurement.persistences.repositoryimplementation.procurementreport.*;
import com.bizeff.procurement.services.procurementreport.ProcurementReportCustomizationServices;
import com.bizeff.procurement.services.procurementreport.ProcurementReportGenerationServices;
import com.bizeff.procurement.usecases.procurementReport.CreateCustomizedProcurementDashboardUseCase;
import com.bizeff.procurement.usecases.procurementReport.ExportProcurementDataUseCase;
import com.bizeff.procurement.usecases.procurementReport.GenerateMonthlyApprovedPurchaseOrderReportUseCase;
import com.bizeff.procurement.webapi.viewmodel.procurementreport.CreateCustomizedProcurementDashboardViewModel;
import com.bizeff.procurement.webapi.viewmodel.procurementreport.ExportProcurementDataViewModel;
import com.bizeff.procurement.webapi.viewmodel.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportViewModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcurementReportConfiguration {
    @Bean
    public ProcurementReportRepository procurementReportRepository(SpringDataProcurementReportRepository springDataProcurementReportRepository){
        return new JpaProcurementReportRepository(springDataProcurementReportRepository);
    }
    @Bean
    public ReportTemplateRepository reportTemplateRepository(SpringDataReportTemplateRepository springDataReportTemplateRepository){
        return new JpaReportTemplateRepository(springDataReportTemplateRepository);
    }
    @Bean
    public TemplateBasedReportRepository templateBasedReportRepository(SpringDataTemplateBasedReportRepository springDataTemplateBasedReportRepository,SpringDataReportTemplateRepository springDataReportTemplateRepository){
        return new JpaTemplateBasedReportRepository(springDataTemplateBasedReportRepository,springDataReportTemplateRepository);
    }
    @Bean
    public ProcurementReportCustomizationServices procurementReportCustomizationServices(){
        return new ProcurementReportCustomizationServices();
    }
    @Bean
    public CreateCustomizedProcurementDashboardViewModel createCustomizedProcurementDashboardViewModel(){
        return new CreateCustomizedProcurementDashboardViewModel();
    }
    @Bean
    public CreateCustomizedProcurementDashboardInputBoundary createCustomizedProcurementDashboardInputBoundary(
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
            CreateCustomizedProcurementDashboardOutPutBoundary createCustomizedProcurementDashboardOutputBoundary
    ){
        return new CreateCustomizedProcurementDashboardUseCase(
                templateBasedReportRepository,
                reportTemplateRepository,
                supplierPerformanceRepository,paymentReconciliationRepository, deliveryReceiptRepository,invoiceRepository,
                contractRepository,purchaseOrderRepository, supplierRepository, purchaseRequisitionRepository,
                userRepository,procurementReportCustomizationServices,createCustomizedProcurementDashboardOutputBoundary);
    }
    @Bean
    public ProcurementReportGenerationServices procurementReportGenerationServices(){
        return new ProcurementReportGenerationServices();
    }
    @Bean
    public ExportProcurementDataViewModel exportProcurementDataViewModel(){
        return new ExportProcurementDataViewModel();
    }
    @Bean
    public ExportProcurementDataInputBoundary exportProcurementDataInputBoundary(
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
        return new ExportProcurementDataUseCase(
                templateBasedReportRepository,
                procurementReportRepository,reportTemplateRepository,
                purchaseRequisitionRepository,purchaseOrderRepository,supplierRepository,
                supplierPerformanceRepository,paymentReconciliationRepository,contractRepository,
                invoiceRepository,deliveryReceiptRepository,userRepository,
                procurementReportGenerationServices,procurementReportCustomizationServices,
                exportProcurementDataOutputBoundary);
    }
    @Bean
    public GenerateMonthlyApprovedPurchaseOrderReportViewModel generateMonthlyApprovedPurchaseOrderReportViewModel(){
        return new GenerateMonthlyApprovedPurchaseOrderReportViewModel();
    }
    @Bean
    public GenerateMonthlyApprovedPurchaseOrderReportInputBoundary generateMonthlyApprovedPurchaseOrderReportInputBoundary(ProcurementReportRepository procurementReportRepository,
                                                                                                                           PurchaseOrderRepository purchaseOrderRepository,
                                                                                                                           SupplierRepository supplierRepository,
                                                                                                                           PurchaseRequisitionRepository purchaseRequisitionRepository,
                                                                                                                           DepartmentRepository departmentRepository,
                                                                                                                           UserRepository userRepository,
                                                                                                                           ProcurementReportGenerationServices procurementReportGenerationServices,
                                                                                                                           GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary generateMonthlyApprovedPurchaseOrderReportOutputBoundary){
        return new GenerateMonthlyApprovedPurchaseOrderReportUseCase(procurementReportRepository,
                purchaseOrderRepository,supplierRepository,purchaseRequisitionRepository,
                departmentRepository,userRepository,procurementReportGenerationServices,
                generateMonthlyApprovedPurchaseOrderReportOutputBoundary);
    }
    @Bean
    public PurchaseRequisitionReportRepository purchaseRequisitionReportRepository(SpringDataPurchaseRequisitionReportRepository springDataPurchaseRequisitionReportRepository){
        return new JpaPurchaseRequisitionReportRepository(springDataPurchaseRequisitionReportRepository);
    }
    @Bean
    public SupplierReportRepository supplierReportRepository(SpringDataSupplierReportRepository springDataSupplierReportRepository){
        return new JpaSupplierReportRepository(springDataSupplierReportRepository);
    }
    @Bean
    public PurchaseOrderReportRepository purchaseOrderReportRepository(SpringDataPurchaseOrderReportRepository springDataPurchaseOrderReportRepository){
        return new JpaPurchaseOrderReportRepository(springDataPurchaseOrderReportRepository);
    }
    @Bean
    public SupplierPerformanceReportRepository supplierPerformanceReportRepository(SpringDataSupplierPerformanceReportRepository springDataSupplierPerformanceReportRepository){
        return new JpaSupplierPerformanceReportRepository(springDataSupplierPerformanceReportRepository);
    }
    @Bean
    public ContractReportRepository contractReportRepository(SpringDataContractReportRepository springDataContractReportRepository){
        return new JpaContractReportRepository(springDataContractReportRepository);
    }
    @Bean
    public DeliveryReceiptReportRepository deliveryReceiptReportRepository(SpringDataDeliveryReceiptReportRepository springDataDeliveryReceiptReportRepository){
        // Return an instance of DeliveryReceiptReportRepository implementation
        return new JpaDeliveryReceiptReportRepository(springDataDeliveryReceiptReportRepository);
    }
    @Bean
    public InvoiceReportRepository invoiceReportRepository(SpringDataInvoiceReportRepository springDataInvoiceReportRepository){
        return new JpaInvoiceReportRepository(springDataInvoiceReportRepository);
    }
    @Bean
    public PaymentReconciliationReportRepository paymentReconciliationReportRepository(SpringDataPaymentReconciliationReportRepository springDataPaymentReconciliationReportRepository){
        return new JpaPaymentReconciliationReportRepository(springDataPaymentReconciliationReportRepository);

    }
}
