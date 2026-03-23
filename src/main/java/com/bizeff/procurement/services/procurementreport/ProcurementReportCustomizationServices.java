package com.bizeff.procurement.services.procurementreport;

import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.*;
import com.bizeff.procurement.models.invoicepaymentreconciliation.*;
import com.bizeff.procurement.models.procurementreport.ProcurementReport;
import com.bizeff.procurement.models.procurementreport.ReportTemplate;
import com.bizeff.procurement.models.procurementreport.TemplateBasedReport;
import com.bizeff.procurement.models.purchaseorder.OrderedItem;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.purchaserequisition.RequestedItem;
import com.bizeff.procurement.models.purchaserequisition.User;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQualitativePerformanceMetrics;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQuantitativePerformanceMetrics;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.procurementreport.FieldValidatorServices.validateFields;
import static com.bizeff.procurement.services.procurementreport.ProcurementReportGenerationServices.*;
import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.validateNotEmptyList;
import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.validatePurchaseOrder;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.*;
public class ProcurementReportCustomizationServices {
    private List<ProcurementReport> procurementReports = new ArrayList<>();
    private Map<String, ReportTemplate> reportTemplates = new HashMap<>();
    private List<PurchaseRequisition>requisitions = new ArrayList<>();
    private List<TemplateBasedReport>templateBasedReports = new ArrayList<>();
    private List<Supplier>suppliers = new ArrayList<>();
    private List<PurchaseOrder>orders = new ArrayList<>();
    private List<Contract>contracts = new ArrayList<>();
    private List<Invoice>invoices = new ArrayList<>();
    private List<DeliveryReceipt>deliveryReceipts = new ArrayList<>();
    private List<PaymentReconciliation>invoicePayments = new ArrayList<>();
    private List<SupplierPerformance>supplierPerformances = new ArrayList<>();
    public ProcurementReport addProcurementReport(ProcurementReport procurementReport){
       validateProcurementReport(procurementReport);
        procurementReports.add(procurementReport);
        return procurementReport;
    }
    public PurchaseRequisition addPurchaseRequisition(PurchaseRequisition purchaseRequisition){
        validatePurchaseRequisition(purchaseRequisition);
        requisitions.add(purchaseRequisition);
        return purchaseRequisition;
    }
    public Supplier addSupplier(Supplier supplier){
        validateSupplier(supplier);
        suppliers.add(supplier);
        return supplier;
    }
    public PurchaseOrder addPurchaseOrder(PurchaseOrder purchaseOrder){
        validatePurchaseOrder(purchaseOrder);
        orders.add(purchaseOrder);
        return purchaseOrder;
    }
    public Contract addContract(Contract contract){
        validateContract(contract);
        contracts.add(contract);
        return contract;
    }
    public Invoice addInvoice(Invoice invoice){
        validateInvoice(invoice);
        invoices.add(invoice);
        return invoice;
    }
    public DeliveryReceipt addDeliveryReceipt(DeliveryReceipt deliveryReceipt){
        validateDeliveryReceipt(deliveryReceipt);
        deliveryReceipts.add(deliveryReceipt);
        return deliveryReceipt;
    }
    public PaymentReconciliation addPaymentReconciliation(PaymentReconciliation paymentReconciliation){
        validatePaymentReconciliation(paymentReconciliation);
        invoicePayments.add(paymentReconciliation);
        return paymentReconciliation;
    }
    public SupplierPerformance addSupplierPerformance(SupplierPerformance supplierPerformance){
        validateSupplierPerformance(supplierPerformance);
        supplierPerformances.add(supplierPerformance);
        return supplierPerformance;
    }
    public ReportTemplate addReportTemplate(String templateId,
                                            String templateName,
                                            String templateDescription,
                                            List<String> selectedFields,
                                            LocalDate createdAt,
                                            User createdBy){
    validateString(templateId,"templateId");
    validateString(templateName,"templateName");
    validateString(templateDescription,"templateDescription");
    validateDate(createdAt,"createdAt");
    validateNotNull(createdBy," user");
    validateNotEmptyList(selectedFields,"selectedFields");
    boolean templateExists = reportTemplates.values().stream().anyMatch(reportTemplate1 -> reportTemplate1.getSelectedFields().equals(selectedFields));
    if (templateExists){
        throw new IllegalArgumentException("Template with selected fields " + selectedFields + " already exists");
    }
    ReportTemplate reportTemplate = new ReportTemplate(templateId, templateName, templateDescription, selectedFields,createdAt,createdBy);
    if (reportTemplates.containsKey(reportTemplate.getTemplateId()) ){
        throw new IllegalArgumentException("Template with id " + reportTemplate.getTemplateId() + " already exists");
    }
    reportTemplates.put(reportTemplate.getTemplateId(),reportTemplate);
    return reportTemplate;
    }
    public TemplateBasedReport buildCustomizedReport(String templateId,List<ProcurementActivity> procurementActivities){
        validateString(templateId,"templateId");
        validateNotEmptyList(procurementActivities,"procurementActivities");
        Map<String,Object> customizedReport = new LinkedHashMap<>();
        ReportTemplate reportTemplate = getReportTemplateById(templateId);

        for (ProcurementActivity procurementActivity : procurementActivities) {
            validateNotEmptyList(reportTemplate.getSelectedFields(),"selectedFields");
            Set<String> allowedFields = FieldValidatorServices.getFields(procurementActivity);
            // 1. Get allowed fields for this activity

            // 2. Keep only the ones that belong to this activity
            List<String> activityFields = reportTemplate.getSelectedFields().stream()
                    .filter(allowedFields::contains)
                    .toList();

            if (activityFields.isEmpty()) {
                throw new IllegalArgumentException(
                        "No valid fields selected for activity: " + procurementActivity
                );
            }

            validateFields(procurementActivity,activityFields);
            switch (procurementActivity)
            {
                case PURCHASE_REQUISITION -> {
                    Map<String,Object> customizedPurchaseRequisitionReportTemplate = customizedPurchaseRequisitionReportTemplate(requisitions,activityFields);
                    customizedReport.putAll(customizedPurchaseRequisitionReportTemplate);
                }
                case SUPPLIER_MANAGEMENT -> {
                    Map<String,Object> customizedSupplierReportTemplate = customizedSupplierReportTemplate(suppliers,activityFields);
                    customizedReport.putAll(customizedSupplierReportTemplate);
                }
                case PURCHASE_ORDER -> {
                    Map<String,Object> customizedPurchaseOrderReportTemplate = customizedPurchaseOrderReportTemplate(orders,activityFields);
                    customizedReport.putAll(customizedPurchaseOrderReportTemplate);
                }
                case CONTRACT_MANAGEMENT -> {
                    Map<String,Object> customizedContractReportTemplate = customizedContractReportTemplate(contracts,activityFields);
                    customizedReport.putAll(customizedContractReportTemplate);
                }
                case INVOICE_MANAGEMENT -> {

                    Map<String,Object> customizedInvoiceReportTemplate = customizedInvoiceReportTemplate(invoices,activityFields);
                    customizedReport.putAll(customizedInvoiceReportTemplate);
                }
                case DELIVERY_RECEIPT -> {

                    Map<String,Object> customizedDeliveryReceiptReportTemplate = customizedDeliveryReceiptReportTemplate(deliveryReceipts,activityFields);
                    customizedReport.putAll(customizedDeliveryReceiptReportTemplate);
                }
                case PAYMENT_RECONCILIATION -> {
                    Map<String,Object> customizedPaymentReconciliationReportTemplate = customizedPaymentReconciliationReportTemplate(invoicePayments,activityFields);
                    customizedReport.putAll(customizedPaymentReconciliationReportTemplate);
                }
                case SUPPLIER_PERFORMANCE -> {
                    Map<String,Object> customizedSupplierPerformanceReportTemplate = customizedSupplierPerformanceReportTemplate(supplierPerformances,activityFields);
                    customizedReport.putAll(customizedSupplierPerformanceReportTemplate);
                }
            }
        }
        TemplateBasedReport templateBasedReport = new TemplateBasedReport(reportTemplate,procurementActivities,customizedReport);
        return templateBasedReport;
    }
    public Map<String,Object> customizedPurchaseRequisitionReportTemplate(List<PurchaseRequisition>requisitions,List<String>fields){
        validateNotEmptyList(requisitions,"requisitions");
        validateNotEmptyList(fields,"fields");
        Map<String,Object> customizedReport = new HashMap<>();

        for (String field:fields) {

            Long totalRequisitions = requisitions.stream().count();
            if (field.equals("totalRequisitions")) {
                customizedReport.put("totalRequisitions", totalRequisitions);
            }
            BigDecimal totalSpendingAmount = requisitions.stream().map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (field.equals("totalSpendingAmount")) {
                customizedReport.put("totalSpendingAmount", totalSpendingAmount.setScale(2, RoundingMode.HALF_UP));
            }
            //requisitions by priority
            Map<PriorityLevel, Long> requisitionsByPriority = requisitions.stream().collect(Collectors.groupingBy(PurchaseRequisition::getPriorityLevel, Collectors.counting()));
            if (field.equals("requisitionsByPriority")) {
                customizedReport.put("requisitionsByPriority", requisitionsByPriority);
            }
            //requisitions by department
            Map<String, Long> requisitionsByDepartment = requisitions.stream().collect(Collectors.groupingBy(pr->pr.getDepartment().getDepartmentId(), Collectors.counting()));
            if (field.equals("requisitionsByDepartment")) {
                customizedReport.put("requisitionsByDepartment", requisitionsByDepartment);
            }
            //requisitions by status
            Map<RequisitionStatus, Long> requisitionsByStatus = requisitions.stream().collect(Collectors.groupingBy(PurchaseRequisition::getRequisitionStatus, Collectors.counting()));
            if (field.equals("requisitionsByStatus")) {
                customizedReport.put("requisitionsByStatus", requisitionsByStatus);
            }

            //Requisition details
            List<PurchaseRequisition> requisitionDetails = requisitions.stream().collect(Collectors.toList());
            if (field.equals("requisitionDetails")) {
                customizedReport.put("requisitionDetails", requisitionDetails);
            }
            List<RequestedItem> requisitionItems = requisitions.stream().flatMap(requisition -> requisition.getItems().stream()).collect(Collectors.toList());
            if (field.equals("requisitionItems")) {
                customizedReport.put("requisitionItems", requisitionItems);
            }
        }
        return customizedReport;
    }
    public Map<String,Object> customizedSupplierReportTemplate(List<Supplier> suppliers,List<String> fields){
        validateNotEmptyList(suppliers,"suppliers");
        validateNotEmptyList(fields,"fields");
        Map<String,Object> customizedReport = new HashMap<>();
        for (String field:fields){
            Long totalSupplier = suppliers.stream().count();
            if (field.equals("totalSupplier")){
                customizedReport.put("totalSupplier",totalSupplier);
            }
            Long activeSupplier = suppliers.stream().filter(Supplier::isActive).count();
            if (field.equals("activeSupplier")){
                customizedReport.put("activeSupplier",activeSupplier);
            }
            Map<String, Long> suppliersByCategory = suppliers.stream().collect(Collectors.groupingBy(Supplier::getSupplierCategory, Collectors.counting()));
            if (field.equals("suppliersByCategory")){
                customizedReport.put("suppliersByCategory",suppliersByCategory);
            }
            List<Supplier> supplierDetails = suppliers.stream().collect(Collectors.toList());
            if (field.equals("supplierDetails")){
                customizedReport.put("supplierDetails",supplierDetails);
            }
            List<Inventory> existedItems = suppliers.stream().flatMap(supplier -> supplier.getExistedItems().stream()).collect(Collectors.toList());
            if (field.equals("existedItems")){
                customizedReport.put("existedItems",existedItems);
            }
        }
        return customizedReport;
    }
    public Map<String,Object> customizedPurchaseOrderReportTemplate(List<PurchaseOrder> orders,List<String> fields){
        validateNotEmptyList(orders,"orders");
        validateNotEmptyList(fields,"fields");
        Map<String,Object> customizedReport = new HashMap<>();
        for (String field:fields){
            long totalOrders = orders.stream().count();
            if (field.equals("totalOrders")){
                customizedReport.put("totalOrders",totalOrders);
            }

            BigDecimal totalSpendingAmount = orders.stream().map(PurchaseOrder::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (field.equals("totalSpendingAmount")){
                customizedReport.put("totalSpendingAmount",totalSpendingAmount);
            }

            Map<PurchaseOrderStatus,Long> ordersByStatus = orders.stream().collect(Collectors.groupingBy(PurchaseOrder::getPurchaseOrderStatus,Collectors.counting()));
            if (field.equals("ordersByStatus")){
                customizedReport.put("ordersByStatus",ordersByStatus);
            }

            Map<String,Long> ordersByDepartment = orders.stream().collect(Collectors.groupingBy(pr->pr.getDepartment().getDepartmentId(),Collectors.counting()));
            if (field.equals("ordersByDepartment")){
                customizedReport.put("ordersByDepartment",ordersByDepartment);
            }

            Map<String,BigDecimal> totalSpendingBySupplier = orders.stream().collect(Collectors.groupingBy(pr->pr.getSupplier().getSupplierId(),Collectors.reducing(BigDecimal.ZERO, PurchaseOrder::getTotalCost, BigDecimal::add)));
            if (field.equals("totalSpendingBySupplier")){
                customizedReport.put("totalSpendingBySupplier",totalSpendingBySupplier);
            }

            Map<String,Long> ordersBySupplier = orders.stream().collect(Collectors.groupingBy(pr->pr.getSupplier().getSupplierId(),Collectors.counting()));
            if (field.equals("ordersBySupplier")){
                customizedReport.put("ordersBySupplier",ordersBySupplier);
            }

            Map<String,BigDecimal> totalSpendingByDepartment = orders.stream().collect(Collectors.groupingBy(pr->pr.getDepartment().getDepartmentId(),Collectors.reducing(BigDecimal.ZERO, PurchaseOrder::getTotalCost, BigDecimal::add)));
            if (field.equals("totalSpendingByDepartment")){
                customizedReport.put("totalSpendingByDepartment",totalSpendingByDepartment);
            }
            List<PurchaseOrder>orderDetails = orders.stream().collect(Collectors.toList());
            if (field.equals("orderDetails")){
                customizedReport.put("orderDetails",orderDetails);
            }
            List<OrderedItem> orderItems = orders.stream().flatMap(order -> order.getOrderedItems().stream()).collect(Collectors.toList());
            if (field.equals("orderItems")){
                customizedReport.put("orderItems",orderItems);
            }
        }
        return customizedReport;
    }
    public Map<String,Object> customizedContractReportTemplate(List<Contract> contracts,List<String> fields){
        validateNotEmptyList(contracts,"contracts");
        validateNotEmptyList(fields,"fields");
        Map<String,Object> customizedReport = new HashMap<>();
        for (String field:fields){
            long totalContracts = contracts.stream().count();
            if (field.equals("totalContracts")){
                customizedReport.put("totalContracts",totalContracts);
            }

            BigDecimal totalContractCosts = contracts.stream().map(Contract::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (field.equals("totalContractCosts")){
                customizedReport.put("totalContractCosts",totalContractCosts);
            }

            Map<ContractStatus,Long> contractsByStatus = contracts.stream().collect(Collectors.groupingBy(Contract::getStatus,Collectors.counting()));
            if (field.equals("contractsByStatus")){
                customizedReport.put("contractsByStatus",contractsByStatus);
            }

            Map<String,BigDecimal> totalSpendingBySupplier = contracts.stream().collect(Collectors.groupingBy(co->co.getSupplier().getSupplierId(),Collectors.reducing(BigDecimal.ZERO, Contract::getTotalCost, BigDecimal::add)));
            if (field.equals("totalSpendingBySupplier")){
                customizedReport.put("totalSpendingBySupplier",totalSpendingBySupplier);
            }

            Map<String,Long> contractsBySupplier = contracts.stream().collect(Collectors.groupingBy(co->co.getSupplier().getSupplierId(),Collectors.counting()));
            if (field.equals("contractsBySupplier")){
                customizedReport.put("contractsBySupplier",contractsBySupplier);
            }
            List<Contract>contractDetails = contracts.stream().collect(Collectors.toList());
            if (field.equals("contractDetails")){
                customizedReport.put("contractDetails",contractDetails);
            }
        }
        return customizedReport;
    }
    public Map<String, Object> customizedInvoiceReportTemplate(List<Invoice>invoices, List<String >fields){
        validateNotEmptyList(invoices,"invoices");
        validateNotEmptyList(fields,"fields");
        Map<String, Object> customizedReport = new HashMap<>();

        for (String field:fields){
            long totalInvoices = invoices.stream().count();
            if (field.equals("totalInvoices")){
                customizedReport.put("totalInvoices",totalInvoices);
            }
            BigDecimal totalInvoiceAmount = invoices.stream().map(Invoice::getTotalCosts).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (field.equals("totalInvoiceAmount")){
                customizedReport.put("totalInvoiceAmount",totalInvoiceAmount);
            }
            Map<String,BigDecimal> totalInvoiceAmountBySupplier = invoices.stream().collect(Collectors.groupingBy(inv->inv.getSupplier().getSupplierId(),Collectors.reducing(BigDecimal.ZERO, Invoice::getTotalCosts, BigDecimal::add)));
            if (field.equals("totalInvoiceAmountBySupplier")){
                customizedReport.put("totalInvoiceAmountBySupplier",totalInvoiceAmountBySupplier);
            }
            Map<String,Long> invoicesBySupplier = invoices.stream().collect(Collectors.groupingBy(inv->inv.getSupplier().getSupplierId(),Collectors.counting()));
            if (field.equals("invoicesBySupplier")){
                customizedReport.put("invoicesBySupplier",invoicesBySupplier);
            }
            List<Invoice> invoiceDetails = invoices.stream().collect(Collectors.toList());
            if (field.equals("invoiceDetails")){
                customizedReport.put("invoiceDetails",invoiceDetails);
            }
            List<InvoicedItem> invoiceItems = invoices.stream().flatMap(invoice -> invoice.getInvoiceItems().stream()).collect(Collectors.toList());
            if (field.equals("invoiceItems")){
                customizedReport.put("invoiceItems",invoiceItems);
            }
        }
        return customizedReport;
    }
    public Map<String, Object> customizedDeliveryReceiptReportTemplate(List<DeliveryReceipt>deliveryReceipts, List<String> fields) {
        validateNotEmptyList(deliveryReceipts,"deliveryReceipts");
        validateNotEmptyList(fields,"fields");
        Map<String, Object> customizedReport = new HashMap<>();

        for (String field : fields) {
            long totalDeliveryReceipts = deliveryReceipts.stream().count();
            if (field.equals("totalDeliveryReceipts")) {
                customizedReport.put("totalDeliveryReceipts", totalDeliveryReceipts);
            }
            BigDecimal totalDeliveryReceiptAmount = deliveryReceipts.stream().map(DeliveryReceipt::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (field.equals("totalDeliveryReceiptAmount")) {
                customizedReport.put("totalDeliveryReceiptAmount", totalDeliveryReceiptAmount);
            }
            Long totalDeliveryReceiptItems = deliveryReceipts.stream().map(DeliveryReceipt::getReceivedItems).flatMap(List::stream).count();
            if (field.equals("totalDeliveryReceiptItems")) {
                customizedReport.put("totalDeliveryReceiptItems", totalDeliveryReceiptItems);
            }
            Map<String, Long> totalDeliveryReceiptItemsBySupplier = deliveryReceipts.stream().collect(Collectors.groupingBy(dr->dr.getSupplier().getSupplierId(), Collectors.counting()));
            if (field.equals("totalDeliveryReceiptItemsBySupplier")) {
                customizedReport.put("totalDeliveryReceiptItemsBySupplier", totalDeliveryReceiptItemsBySupplier);
            }
            Map<DeliveryStatus, Long> totalDeliveryReceiptItemsByStatus = deliveryReceipts.stream().collect(Collectors.groupingBy(DeliveryReceipt::getDeliveryStatus, Collectors.counting()));
            if (field.equals("totalDeliveryReceiptItemsByStatus")) {
                customizedReport.put("totalDeliveryReceiptItemsByStatus", totalDeliveryReceiptItemsByStatus);
            }
            Map<String, BigDecimal> totalDeliveryReceiptAmountBySupplier = deliveryReceipts.stream().collect(Collectors.groupingBy(dr->dr.getSupplier().getSupplierId(), Collectors.reducing(BigDecimal.ZERO, DeliveryReceipt::getTotalCost, BigDecimal::add)));
            if (field.equals("totalDeliveryReceiptAmountBySupplier")) {
                customizedReport.put("totalDeliveryReceiptAmountBySupplier", totalDeliveryReceiptAmountBySupplier);
            }
            List<DeliveryReceipt> deliveryReceiptDetails = deliveryReceipts.stream().collect(Collectors.toList());
            if (field.equals("deliveryReceiptDetails")) {
                customizedReport.put("deliveryReceiptDetails", deliveryReceiptDetails);
            }
            List<DeliveredItem> deliveryReceiptItems = deliveryReceipts.stream().flatMap(deliveryReceipt -> deliveryReceipt.getReceivedItems().stream()).collect(Collectors.toList());
            if (field.equals("deliveryReceiptItems")) {
                customizedReport.put("deliveryReceiptItems", deliveryReceiptItems);
            }
        }
        return customizedReport;
    }
    public Map<String, Object> customizedPaymentReconciliationReportTemplate(List<PaymentReconciliation> paymentReconciliations, List<String> fields){
        validateNotEmptyList(paymentReconciliations,"paymentReconciliations");
        validateNotEmptyList(fields,"fields");
        Map<String, Object> customizedReport = new HashMap<>();

        for (String field : fields){
            Long totalInvoicePayments = paymentReconciliations.stream().count();
            if (field.equals("totalInvoicePayments")){
                customizedReport.put("totalInvoicePayments",totalInvoicePayments);
            }
            BigDecimal totalActuallyPaidAmount = paymentReconciliations.stream().map(PaymentReconciliation::getActualPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (field.equals("totalActuallyPaidAmount")){
                customizedReport.put("totalActuallyPaidAmount",totalActuallyPaidAmount);
            }
            BigDecimal totalExpectedAmount = paymentReconciliations.stream().map(PaymentReconciliation::getExpectedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (field.equals("totalExpectedAmount")){
                customizedReport.put("totalExpectedAmount",totalExpectedAmount);
            }
            BigDecimal totalDiscrepancyAmount = paymentReconciliations.stream().map(PaymentReconciliation::getDiscrepancyAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            if (field.equals("totalDiscrepancyAmount")){
                customizedReport.put("totalDiscrepancyAmount",totalDiscrepancyAmount);
            }
            Map<ReconciliationStatus, Long> totalPaymentReconciliationStatusCount = paymentReconciliations.stream().collect(Collectors.groupingBy(PaymentReconciliation::getReconciliationStatus,Collectors.counting()));
            if (field.equals("totalPaymentReconciliationStatusCount")){
                customizedReport.put("totalPaymentReconciliationStatusCount",totalPaymentReconciliationStatusCount);
            }
            List<PaymentReconciliation> paymentReconciliationDetails = paymentReconciliations.stream().collect(Collectors.toList());
            if (field.equals("paymentReconciliationDetails")){
                customizedReport.put("paymentReconciliationDetails",paymentReconciliationDetails);
            }
        }
        return customizedReport;
    }
    public Map<String, Object> customizedSupplierPerformanceReportTemplate(List<SupplierPerformance> supplierPerformances, List<String>fields){
        validateNotEmptyList(supplierPerformances,"supplierPerformances");
        validateNotEmptyList(fields,"fields");
        Map<String, Object> customizedReport = new HashMap<>();

        for (String field : fields){
            Long totalSupplierPerformances = supplierPerformances.stream().count();
            if (field.equals("totalSupplierPerformances")){
                customizedReport.put("totalSupplierPerformances",totalSupplierPerformances);
            }
            Map<SupplierPerformanceRate, Long> suppliersByCurrentPerformanceRate = supplierPerformances.stream().collect(Collectors.groupingBy(SupplierPerformance::getSupplierPerformanceRate,Collectors.counting()));
            if (field.equals("suppliersByCurrentPerformanceRate")){
                customizedReport.put("suppliersByCurrentPerformanceRate",suppliersByCurrentPerformanceRate);
            }
            List<SupplierPerformance> supplierPerformanceDetails = supplierPerformances.stream().collect(Collectors.toList());
            if (field.equals("supplierPerformanceDetails")){
                customizedReport.put("supplierPerformanceDetails",supplierPerformanceDetails);
            }
            Map<String,List<SupplierPerformance>> supplierPerformanceBySupplier = supplierPerformances.stream().collect(Collectors.groupingBy(supplierPerformance -> supplierPerformance.getSupplier().getSupplierId(),Collectors.toList()));
            if (field.equals("supplierPerformanceBySupplier")){
                customizedReport.put("supplierPerformanceBySupplier",supplierPerformanceBySupplier);
            }
            Map<String,List<SupplierQualitativePerformanceMetrics>> supplierQualitativePerformanceMetricsBySupplier = supplierPerformances.stream().collect(Collectors.groupingBy(supplierPerformance -> supplierPerformance.getSupplier().getSupplierId(),Collectors.mapping(SupplierPerformance::getQualitativePerformanceMetrics,Collectors.toList())));
            if (field.equals("supplierQualitativePerformanceMetricsBySupplier")){
                customizedReport.put("supplierQualitativePerformanceMetricsBySupplier",supplierQualitativePerformanceMetricsBySupplier);
            }
            Map<String,List<SupplierQuantitativePerformanceMetrics>> supplierQuantitativePerformanceMetricsBySupplier = supplierPerformances.stream().collect(Collectors.groupingBy(supplierPerformance->supplierPerformance.getSupplier().getSupplierId(),Collectors.mapping(SupplierPerformance::getQuantitativePerformanceMetrics,Collectors.toList())));
            if (field.equals("supplierQuantitativePerformanceMetricsBySupplier")){
                customizedReport.put("supplierQuantitativePerformanceMetricsBySupplier",supplierQuantitativePerformanceMetricsBySupplier);
            }
        }
        return customizedReport;
     }
    public ProcurementReport getProcurementReportById(String reportId){
        validateString(reportId,"reportId");
        return procurementReports.stream().filter(procurementReport -> procurementReport.getReportId().equals(reportId)).findFirst().orElse(null);
    }
    public ReportTemplate getReportTemplateById(String templateId){
        validateString(templateId,"templateId");
        return reportTemplates.values().stream().filter(reportTemplate -> reportTemplate.getTemplateId().equals(templateId)).findFirst().orElseThrow(()->new IllegalArgumentException("there is no Template with the given template id:"+ templateId));
    }
    public List<ProcurementReport> getProcurementReports() {
        return procurementReports;
    }
    public Map<String, ReportTemplate> getReportTemplates() {
        return reportTemplates;
    }
    public List<PurchaseRequisition> getPurchaseRequisitions() {
        return requisitions;
    }
    public List<Supplier>getSuppliers() {
        return suppliers;
    }
    public List<PurchaseOrder> getPurchaseOrders() {
        return orders;
    }
    public List<Contract>getContracts(){
        return contracts;
    }
    public List<Invoice>getInvoices(){
        return invoices;
    }

    public List<DeliveryReceipt> getDeliveryReceipts() {
        return deliveryReceipts;
    }

    public List<PaymentReconciliation>getInvoicePayments(){
        return invoicePayments;
    }
    public List<SupplierPerformance> getSupplierPerformances() {
        return supplierPerformances;
    }
    public void validateProcurementReport(ProcurementReport procurementReport){
        validateNotNull(procurementReport, "Procurement report");
        validateString(procurementReport.getReportId(), "Report ID");
        validateString(procurementReport.getTitle(),"Report Title");
        validateString(procurementReport.getReportDescription(),"Report description");
        validateNotNull(procurementReport.getCreatedBy(),"Created By");
        validateDate(procurementReport.getCreatedAt(),"Created At");
    }
}
