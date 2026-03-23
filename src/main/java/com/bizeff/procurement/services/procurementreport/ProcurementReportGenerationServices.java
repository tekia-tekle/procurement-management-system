package com.bizeff.procurement.services.procurementreport;

import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.*;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;
import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;
import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;
import com.bizeff.procurement.models.procurementreport.*;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.purchaserequisition.User;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.invoicepaymentreconciliation.ContractPaymentTermsEnforcementService.validateDateOrder;
import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.validatePositiveValue;
import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.*;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.*;

public class ProcurementReportGenerationServices {
    private List<ProcurementReport> reports = new ArrayList<>();
    private List<PurchaseRequisition> requisitions = new ArrayList<>();
    private List<Supplier> suppliers = new ArrayList<>();
    private List<SupplierPerformance> supplierPerformances = new ArrayList<>();;
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();
    private List<Contract> contracts = new ArrayList<>();
    private List<DeliveryReceipt> deliveryReceipts = new ArrayList<>();
    private List<PaymentReconciliation> paymentReconciliations = new ArrayList<>();
    private List<Invoice>invoiceList = new ArrayList<>();

    /** Adders **/
    public PurchaseRequisition addPurchaseRequisition(PurchaseRequisition requisition) {
        validatePurchaseRequisition(requisition);
        requisitions.add(requisition);
        return requisition;
    }
    public Supplier addSupplier(Supplier supplier) {
        validateSupplier(supplier);
        suppliers.add(supplier);
        return supplier;
    }
    public SupplierPerformance addSupplierPerformance(SupplierPerformance supplierPerformance){
        validateSupplierPerformance(supplierPerformance);
        supplierPerformances.add(supplierPerformance);
        return supplierPerformance;
    }
    public PurchaseOrder addPurchaseOrder(PurchaseOrder purchaseOrder) {
        validatePurchaseOrder(purchaseOrder);
        purchaseOrders.add(purchaseOrder);
        return purchaseOrder;
    }
    public Contract addContract(Contract contract) {
        validateContract(contract);
        contracts.add(contract);
        return contract;
    }
    public Invoice addInvoice(Invoice invoice){
        validateInvoice(invoice);
        invoiceList.add(invoice);
        return invoice;
    }
    public DeliveryReceipt addDeliveryReceipt(DeliveryReceipt deliveryReceipt) {
        validateDeliveryReceipt(deliveryReceipt);
        deliveryReceipts.add(deliveryReceipt);
        return deliveryReceipt;
    }
    public PaymentReconciliation addPaymentReconciliation(PaymentReconciliation paymentReconciliation) {
        validatePaymentReconciliation(paymentReconciliation);
        paymentReconciliations.add(paymentReconciliation);
        return paymentReconciliation;
    }

    /** Generating TimeBasedReport. which means report for spe*/
    public ProcurementReport generateTimeBasedProcurementReport(User reporter,
                                                                List<ProcurementActivity> activityTypes,
                                                                LocalDate startDate,
                                                                LocalDate endDate,
                                                                LocalDate createdAt,
                                                                String title,
                                                                String reportDescription){
        validateNotNull(reporter,"Reporter");
        if(activityTypes.isEmpty() || activityTypes == null){
            throw new IllegalArgumentException("Activity types cannot be empty or null");
        }
        validateDateOrder(startDate,endDate);
        validateDate(createdAt,"Reporting Date");
        validateString(title,"Title");
        validateString(reportDescription,"Report Description");

        ProcurementReport procurementReport = new ProcurementReport(title,reportDescription,reporter,createdAt);
        for (ProcurementActivity activity:activityTypes){

            switch (activity){
                case PURCHASE_REQUISITION -> {
                    List<PurchaseRequisition>totalRequisition = getRequisitions().stream()
                            .filter(purchaseRequisition -> !purchaseRequisition.getRequisitionDate().isAfter(endDate)
                                    && !purchaseRequisition.getRequisitionDate().isBefore(startDate))
                            .collect(Collectors.toList());
                    PurchaseRequisitionReport purchaseRequisitionReport = generatePurchaseRequisitionReport(totalRequisition);

                    procurementReport.setRequisitionReport(purchaseRequisitionReport);
                }
                case SUPPLIER_MANAGEMENT -> {
                    List<Supplier> totalSuppliers = suppliers.stream().
                            filter(supplier -> !supplier.getRegistrationDate().isAfter(endDate)
                                    && !supplier.getRegistrationDate().isBefore(startDate))
                            .collect(Collectors.toList());

                    procurementReport.setSupplierReport(generateSupplierReport(totalSuppliers));
                }
                case PURCHASE_ORDER -> {
                    List<PurchaseOrder>totalPurchaseOrders = purchaseOrders.stream()
                            .filter(purchaseOrder -> !purchaseOrder.getOrderDate().isAfter(endDate)
                            && !purchaseOrder.getOrderDate().isBefore(startDate))
                            .collect(Collectors.toList());
                    procurementReport.setPurchaseOrderReport(generatePurchaseOrderReport(totalPurchaseOrders));
                }
                case CONTRACT_MANAGEMENT -> {
                    List<Contract> contractList = contracts.stream()
                            .filter(contract -> !contract.getStartDate().isBefore(startDate)
                            && !contract.getEndDate().isAfter(endDate))
                            .collect(Collectors.toList());

                    procurementReport.setContractReport(generateContractReport(contractList));
                }
                case INVOICE_MANAGEMENT -> {
                    List<Invoice>invoices = invoiceList.stream()
                            .filter(invoice -> !invoice.getInvoiceDate().isBefore(startDate)
                            && !invoice.getInvoiceDate().isAfter(endDate))
                            .collect(Collectors.toList());

                    procurementReport.setInvoiceReport(generateInvoiceReport(invoices));
                }
                case DELIVERY_RECEIPT -> {
                    List<DeliveryReceipt>deliveryReceiptList = deliveryReceipts.stream()
                            .filter(deliveryReceipt -> !deliveryReceipt.getDeliveryDate().isBefore(startDate)
                            && !deliveryReceipt.getDeliveryDate().isAfter(endDate))
                            .collect(Collectors.toList());

                    procurementReport.setDeliveryReceiptReport(generateDeliveryReceiptReport(deliveryReceiptList));
                }
                case SUPPLIER_PERFORMANCE -> {
                    List<SupplierPerformance> supplierPerformanceList = supplierPerformances.stream()
                            .filter(supplierPerformance -> !supplierPerformance.getEvaluationDate().isBefore(startDate) &&
                                    !supplierPerformance.getEvaluationDate().isAfter(endDate))
                            .collect(Collectors.toList());
                    procurementReport.setSupplierPerformanceReport(generateSupplierPerformanceReport(supplierPerformanceList));
                }
                case PAYMENT_RECONCILIATION -> {
                    List<PaymentReconciliation> paymentReconciliationList = paymentReconciliations.stream()
                            .filter(paymentReconciliation -> !paymentReconciliation.getPaymentDate().isBefore(startDate)
                            && !paymentReconciliation.getPaymentDate().isAfter(endDate))
                            .collect(Collectors.toList());

                    procurementReport.setPaymentReconciliationReport(generatePaymentReconciliationReport(paymentReconciliationList));
                }
            }
        }
        return procurementReport;
    }

    /** Report Generation for Purchase Requisition. */
    public PurchaseRequisitionReport generatePurchaseRequisitionReport(List<PurchaseRequisition>requisitions){
        if (requisitions == null || requisitions.isEmpty()){
            throw new IllegalArgumentException("there is no requisition that existed, so we can't report any thing.");
        }
        PurchaseRequisitionReport requisitionReport = new PurchaseRequisitionReport();

        requisitionReport.setTotalRequisitions(requisitions.stream().count());

        requisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(r -> r.getItems().size()).sum());

        requisitionReport.setTotalSpendingAmount(requisitions.stream()
                        .map(PurchaseRequisition::getTotalEstimatedCosts)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        Map<PriorityLevel, Integer> requisitionsByPriorityLevel = new HashMap<>(); // this  group requisitions by the priority level.

        for (PurchaseRequisition requisition : requisitions) {
            PriorityLevel priorityLevel = requisition.getPriorityLevel();
            requisitionsByPriorityLevel.put(priorityLevel,
                    requisitionsByPriorityLevel.getOrDefault(priorityLevel, 0) + 1);
        }
        requisitionReport.setRequisitionsByPriority(requisitionsByPriorityLevel);

        // Grouping logic for requisitions by status, department, user,
        Map<RequisitionStatus, Integer> requisitionsByStatus = new HashMap<>(); // this store requisitions by Status.count the requisitions that have same requisition Status.
        for (PurchaseRequisition req:requisitions){
            RequisitionStatus status = req.getRequisitionStatus();
            requisitionsByStatus.put(status, requisitionsByStatus.getOrDefault(requisitionsByStatus,0)+1);
        }
        requisitionReport.setRequisitionStatusMap(requisitionsByStatus);

        /** Requisition By Departments. */

        Map<String, Integer> requisitionByDepartment = new HashMap<>();
        for (PurchaseRequisition requisition:requisitions){
            Department department = requisition.getDepartment();
            String depatmentId = department.getDepartmentId();
            requisitionByDepartment.put(depatmentId,requisitionByDepartment.getOrDefault(depatmentId, 0)+1);
        }
        requisitionReport.setRequisitionsByDepartment(requisitionByDepartment);

        Map<String,BigDecimal> requisitionTotalSpendingPerDepartment = new HashMap<>();
        for (PurchaseRequisition requisition: requisitions){
            Department department = requisition.getDepartment();
            String depatmentId = department.getDepartmentId();
            requisitionTotalSpendingPerDepartment.put(depatmentId, requisitionTotalSpendingPerDepartment.getOrDefault(depatmentId, BigDecimal.ZERO).add(requisition.getTotalEstimatedCosts()));
        }
        requisitionReport.setTotalSpendingPerDepartment(requisitionTotalSpendingPerDepartment);

        return requisitionReport;
    }

    /** Report Generation for Supplier. */
    public SupplierReport generateSupplierReport(List<Supplier> suppliers){
        if (suppliers ==  null || suppliers.isEmpty()){
            throw new IllegalArgumentException("there is no supplier that existed before, so that we can't report any thing since there is no registered supplier.");
        }
        SupplierReport report = new SupplierReport();

        report.setTotalSuppliers(suppliers.stream().count());

        report.setActiveSuppliers(suppliers.stream().filter(Supplier::isActive).count());

        Map<String, Integer> suppliersByCategory = new HashMap<>(); // group and count the suppliers with the same categories.
        for (Supplier supplier : suppliers){
            String category = supplier.getSupplierCategory();
            suppliersByCategory.put(category, suppliersByCategory.getOrDefault(category, 0)+1);
        }
        report.setSuppliersByCategory(suppliersByCategory);
        return report;
    }

    /** Purchase Order Reporting. */
    public PurchaseOrderReport generatePurchaseOrderReport(List<PurchaseOrder>purchaseOrders){
        if (purchaseOrders == null || purchaseOrders.isEmpty()){
            throw new IllegalArgumentException("there is no purchase that is ordered before so that we can't generate Purchase order Report.");
        }
        PurchaseOrderReport report = new PurchaseOrderReport();

        report.setTotalPurchaseOrders(purchaseOrders.stream().count()); //counting the total ordered purchases.

        report.setTotalPurchaseOrderCosts(purchaseOrders.stream().map(PurchaseOrder::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add)); // total spend amount for the over all purchase order.

        Map<PurchaseOrderStatus,Integer> purchaseOrderByStatus = new HashMap<>();
        for (PurchaseOrder purchaseOrder:purchaseOrders){
            PurchaseOrderStatus status = purchaseOrder.getPurchaseOrderStatus();
            purchaseOrderByStatus.put(status, purchaseOrderByStatus.getOrDefault(status, 0)+1);
        }
        report.setPurchaseOrderStatusMap(purchaseOrderByStatus);

        Map<String ,Integer> purchaseOrderByDepartment = new HashMap<>();
        for (PurchaseOrder purchaseOrder: purchaseOrders){
            Department department = purchaseOrder.getDepartment();
            String depatmentId = department.getDepartmentId();
            purchaseOrderByDepartment.put(depatmentId, purchaseOrderByDepartment.getOrDefault(depatmentId, 0)+1);
        }
        report.setPurchaseOrdersByDepartment(purchaseOrderByDepartment);

        Map<String, BigDecimal> purchaseOrderTotalSpentPerSupplier = new HashMap<>();
        for (PurchaseOrder purchaseOrder:purchaseOrders){
            Supplier supplier = purchaseOrder.getSupplier();
            String supplierId = supplier.getSupplierId();
            purchaseOrderTotalSpentPerSupplier.put(supplierId, purchaseOrderTotalSpentPerSupplier.getOrDefault(supplierId, BigDecimal.ZERO).add(purchaseOrder.getTotalCost()));
        }
        report.setPurchaseOrderTotalSpendPerSupplier(purchaseOrderTotalSpentPerSupplier);

        Map<String, Integer> totalPurchaseOrderPerSupplier = new HashMap<>();
        for (PurchaseOrder purchaseOrder:purchaseOrders){
            Supplier supplier = purchaseOrder.getSupplier();
            String supplierId = supplier.getSupplierId();
            totalPurchaseOrderPerSupplier.put(supplierId,totalPurchaseOrderPerSupplier.getOrDefault(supplierId, 0)+1);
        }
        report.setPurchaseOrdersForSupplier(totalPurchaseOrderPerSupplier);

        Map<String,BigDecimal>purchaseOrderTotalSpendPerDepartment = new HashMap<>();
        for (PurchaseOrder purchaseOrder:purchaseOrders){
            Department department = purchaseOrder.getDepartment();
            String departmentId = department.getDepartmentId();
            purchaseOrderTotalSpendPerDepartment.put(departmentId, purchaseOrderTotalSpendPerDepartment.getOrDefault(departmentId, BigDecimal.ZERO).add(purchaseOrder.getTotalCost()));
        }
        report.setPurchaseOrderTotalSpendPerDepartment(purchaseOrderTotalSpendPerDepartment);
        return report;
    }

    /** this is the Report for contract. */
    public ContractReport generateContractReport(List<Contract> contractList){
        if (contractList == null || contractList.isEmpty()){
            throw new IllegalArgumentException("there is no contract to be reported.");
        }
        ContractReport contractReport = new ContractReport();

        contractReport.setTotalContracts(contractList.stream().count());
        contractReport.setTotalContractsCost(contractList.stream().map(Contract::getTotalCost).reduce(BigDecimal.ZERO,BigDecimal::add));

        Map<ContractStatus,Integer>contractByStatus = new HashMap<>();
        for (Contract contract:contractList){
            ContractStatus status = contract.getStatus();
            contractByStatus.put(status,contractByStatus.getOrDefault(status,0)+1);

        }
        contractReport.setContractsByStatus(contractByStatus);

        Map<String,BigDecimal>totalContractCostsPerSupplier = new HashMap<>();
        for (Contract contract:contractList){
            Supplier supplier = contract.getSupplier();
            String supplierId = supplier.getSupplierId();
            totalContractCostsPerSupplier.put(supplierId,totalContractCostsPerSupplier.getOrDefault(supplierId, BigDecimal.ZERO).add(contract.getTotalCost()));
        }
        contractReport.setTotalContractCostsPerSupplier(totalContractCostsPerSupplier);

        Map<String,Integer>contractsWithSupplier = new HashMap<>();
        for (Contract contract:contractList){
            Supplier supplier = contract.getSupplier();
            String supplierId = supplier.getSupplierId();
            contractsWithSupplier.put(supplierId,contractsWithSupplier.getOrDefault(supplierId,0)+1);
        }
        contractReport.setContractsWithSupplier(contractsWithSupplier);
        List<Contract> expiringContracts = new ArrayList<>();
        for (Contract contract:contractList){
            if (contract.isNotified()){
                expiringContracts.add(contract);
            }
            else break;
        }
        contractReport.setExpiringContracts(expiringContracts);

        return contractReport;
    }

    /** this is the Report for Invoice. */
    public InvoiceReport generateInvoiceReport(List<Invoice> invoiceList){
        if (invoiceList == null || invoiceList.isEmpty()){
            throw new IllegalArgumentException("there is no invoice to be reported.");
        }

        InvoiceReport invoiceReport = new InvoiceReport();

        invoiceReport.setTotalInvoices(invoiceList.stream().count());
        invoiceReport.setTotalInvoicedAmount(invoiceList.stream().map(Invoice::getTotalCosts).reduce(BigDecimal.ZERO,BigDecimal::add));

        Map<String,BigDecimal> totalInvoicedCostPerSupplier = new HashMap<>();
        for (Invoice invoice:invoiceList){
            Supplier supplier = invoice.getSupplier();
            String supplierId = supplier.getSupplierId();
            totalInvoicedCostPerSupplier.put(supplierId,totalInvoicedCostPerSupplier.getOrDefault(supplierId,BigDecimal.ZERO).add(invoice.getTotalCosts()));
        }
        invoiceReport.setTotalInvoiceCostsPerSupplier(totalInvoicedCostPerSupplier);

        Map<String,Integer> invoicesFromSupplier = new HashMap<>();
        for (Invoice invoice:invoiceList){
            Supplier supplier =  invoice.getSupplier();
            String supplierId = supplier.getSupplierId();
            invoicesFromSupplier.put(supplierId,invoicesFromSupplier.getOrDefault(supplierId,0)+1);
        }
        invoiceReport.setInvoicesFromSupplier(invoicesFromSupplier);

        return invoiceReport;
    }

    /** this is the report for Delivery Receipts. */
    public DeliveryReceiptReport generateDeliveryReceiptReport(List<DeliveryReceipt>deliveryReceiptList){
        if (deliveryReceiptList == null || deliveryReceiptList.isEmpty()){
            throw new IllegalArgumentException("there is no delivery receipt to be reported.");
        }
        DeliveryReceiptReport deliveryReceiptReport = new DeliveryReceiptReport();

        deliveryReceiptReport.setTotalDeliveryReceipts(deliveryReceiptList.stream().count());
        deliveryReceiptReport.setTotalDeliveredItems(deliveryReceiptList.stream().mapToLong(deliveryReceipt -> deliveryReceipt.getReceivedItems().size()).sum());

        deliveryReceiptReport.setTotalCostForDeliveredItem(deliveryReceiptList.stream().map(DeliveryReceipt::getTotalCost).reduce(BigDecimal.ZERO,BigDecimal::add));

        Map<DeliveryStatus,Integer> deliveryReceiptsByStatus = new HashMap<>();
        for (DeliveryReceipt deliveryReceipt:deliveryReceiptList){
            DeliveryStatus status = deliveryReceipt.getDeliveryStatus();
            deliveryReceiptsByStatus.put(status,deliveryReceiptsByStatus.getOrDefault(status,0)+1);

        }
        deliveryReceiptReport.setDeliveryReceiptsByStatus(deliveryReceiptsByStatus);

        Map<String,BigDecimal> totalCostsForDeliveredItemPerSupplier = new HashMap<>();
        for (DeliveryReceipt deliveryReceipt:deliveryReceiptList){
            Supplier supplier = deliveryReceipt.getSupplier();
            String supplierId = supplier.getSupplierId();
            totalCostsForDeliveredItemPerSupplier.put(supplierId, totalCostsForDeliveredItemPerSupplier.getOrDefault(supplierId,BigDecimal.ZERO).add(deliveryReceipt.getTotalCost()));
        }
        deliveryReceiptReport.setTotalCostsForDeliveredItemPerSupplier(totalCostsForDeliveredItemPerSupplier);

        Map<String,Integer> deliveryReceiptsFromSupplier = new HashMap<>();
        for (DeliveryReceipt deliveryReceipt:deliveryReceiptList){
            Supplier supplier = deliveryReceipt.getSupplier();
            String supplierId = supplier.getSupplierId();
            deliveryReceiptsFromSupplier.put(supplierId,deliveryReceiptsFromSupplier.getOrDefault(supplierId,0)+1);
        }
        deliveryReceiptReport.setDeliveryReceiptsFromSupplier(deliveryReceiptsFromSupplier);

        return deliveryReceiptReport;
    }

    /** this is the report for payment reconciliation. */
    public PaymentReconciliationReport generatePaymentReconciliationReport(List<PaymentReconciliation> paymentReconciliationList){

        if (paymentReconciliationList == null || paymentReconciliationList.isEmpty()){
            throw new IllegalArgumentException("there is no payment reconciliation to be reported. ");
        }
        PaymentReconciliationReport reconciliationReport = new PaymentReconciliationReport();

        reconciliationReport.setTotalReconciliations(paymentReconciliationList.stream().count());
        reconciliationReport.setTotalPaidAmount(paymentReconciliationList.stream().map(PaymentReconciliation::getActualPaidAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
        reconciliationReport.setTotalExpectedAmount(paymentReconciliationList.stream().map(PaymentReconciliation::getExpectedAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
        reconciliationReport.setTotalDiscrepancyAmount(paymentReconciliationList.stream().map(PaymentReconciliation::getDiscrepancyAmount).reduce(BigDecimal.ZERO,BigDecimal::add));

        Map<ReconciliationStatus,Integer> reconciliationByStatus = new HashMap<>();
        for (PaymentReconciliation paymentReconciliation: paymentReconciliationList){
            ReconciliationStatus status =  paymentReconciliation.getReconciliationStatus();
            reconciliationByStatus.put(status,reconciliationByStatus.getOrDefault(status,0)+1);
        }
        reconciliationReport.setReconciliationByStatus(reconciliationByStatus);

        Map<String,Integer> reconciliationBySupplier = new HashMap<>();
        for (PaymentReconciliation paymentReconciliation:paymentReconciliationList){
            Invoice invoice = paymentReconciliation.getInvoice();
            Supplier supplier = invoice.getSupplier();
            String supplierId = supplier.getSupplierId();
            reconciliationBySupplier.put(supplierId,reconciliationBySupplier.getOrDefault(supplierId,0)+1);
        }
        reconciliationReport.setReconciliationsBySupplier(reconciliationBySupplier);

        Map<String,BigDecimal> totalDiscrepancyPerSupplier = new HashMap<>();
        for (PaymentReconciliation paymentReconciliation:paymentReconciliationList){
            Invoice invoice = paymentReconciliation.getInvoice();
            Supplier supplier = invoice.getSupplier();
            String supplierId = supplier.getSupplierId();
            totalDiscrepancyPerSupplier.put(supplierId,totalDiscrepancyPerSupplier.getOrDefault(supplierId,BigDecimal.ZERO).add(paymentReconciliation.getDiscrepancyAmount()));
        }
        reconciliationReport.setTotalDiscrepancyPerSupplier(totalDiscrepancyPerSupplier);

        return reconciliationReport;
    }

    /** this is the Report for SupplierPerformance. */
    public SupplierPerformanceReport generateSupplierPerformanceReport(List<SupplierPerformance> supplierPerformanceList) {
        if (supplierPerformanceList == null || supplierPerformanceList.isEmpty()) {
            throw new IllegalArgumentException("There is no supplierPerformance to be reported.");
        }

        SupplierPerformanceReport performanceReport = new SupplierPerformanceReport();

        // Group performances by Supplier once to reuse
        Map<String, List<SupplierPerformance>> performancesPerSupplier = new HashMap<>();
        for (SupplierPerformance performance : supplierPerformanceList) {
            performancesPerSupplier
                    .computeIfAbsent(performance.getSupplier().getSupplierId(), k -> new ArrayList<>())
                    .add(performance);
        }

        // Prepare latest performance and average score maps
        Map<String , SupplierPerformance> latestPerformancePerSupplier = new HashMap<>();
        Map<String, Double> supplierWithAveragePerformanceRate = new HashMap<>();

        for (Map.Entry<String, List<SupplierPerformance>> entry : performancesPerSupplier.entrySet()) {
            String supplierId = entry.getKey();
            List<SupplierPerformance> performances = entry.getValue();

            // Get latest performance
            SupplierPerformance latest = performances.stream()
                    .max(Comparator.comparing(SupplierPerformance::getEvaluationDate))
                    .orElseThrow(() -> new IllegalStateException("Unexpected empty performance list"));

            latestPerformancePerSupplier.put(supplierId, latest);

            // Calculate average performance score
            double avgQualitative = performances.stream().mapToDouble(this::getQualitativePerformance).average().orElse(0);
            double avgQuantitative = performances.stream().mapToDouble(this::getQuantitativePerformance).average().orElse(0);
            double averageScore = avgQualitative + avgQuantitative;

            supplierWithAveragePerformanceRate.put(supplierId, Math.round(averageScore * 100.0) / 100.0);
        }

        performanceReport.setLatestPerformancePerSupplier(latestPerformancePerSupplier);
        performanceReport.setSupplierWithAveragePerformanceScore(supplierWithAveragePerformanceRate);

        // Group by latest performance rate (not global max!)
        Map<SupplierPerformanceRate, Integer> suppliersByCurrentPerformanceRate = new HashMap<>();
        for (SupplierPerformance latest : latestPerformancePerSupplier.values()) {
            SupplierPerformanceRate rate = latest.getSupplierPerformanceRate();
            suppliersByCurrentPerformanceRate.put(rate, suppliersByCurrentPerformanceRate.getOrDefault(rate, 0) + 1);
        }

        performanceReport.setSuppliersByCurrentPerformanceRate(suppliersByCurrentPerformanceRate);

        return performanceReport;
    }

    private double getQualitativePerformance(SupplierPerformance performance){
        double qualitativePerformanceScore = 0.0;
        qualitativePerformanceScore += (performance.getQualitativePerformanceMetrics().getContractAdherence() + performance.getQualitativePerformanceMetrics().getTechnicalExpertise() + performance.getQualitativePerformanceMetrics().getInnovation() + performance.getQualitativePerformanceMetrics().getQualityProducts() +
                performance.getQualitativePerformanceMetrics().getResponsiveness() + performance.getQualitativePerformanceMetrics().getCustomerSatisfaction()) / 6;
        return Math.round(qualitativePerformanceScore * 100.0) / 100.0;
    }
    private double getQuantitativePerformance(SupplierPerformance performance){
        double quntitativePerformanceScore = 0.0;
        quntitativePerformanceScore += (performance.getQuantitativePerformanceMetrics().getDeliveryRate() * 0.3) + (performance.getQuantitativePerformanceMetrics().getAccuracyRate() * 0.2) + (performance.getQuantitativePerformanceMetrics().getComplianceRate() * 0.2) +
                (performance.getQuantitativePerformanceMetrics().getCostEfficiency() * 0.2) + (performance.getQuantitativePerformanceMetrics().getCorrectionRate() * 0.1) - (performance.getQuantitativePerformanceMetrics().getDefectsRate() * 0.2) - (performance.getQuantitativePerformanceMetrics().getCanceledOrders() * 0.1);
        return Math.round(quntitativePerformanceScore * 100.0)/100.0;
    }
    public List<ProcurementReport> getReports() {
        return reports;
    }

    public List<PurchaseRequisition> getRequisitions() {
        return requisitions;
    }
    public List<Supplier> getSuppliers() {
        return suppliers;
    }
    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }
    public List<Contract> getContracts() {
        return contracts;
    }
    public List<DeliveryReceipt> getDeliveryReceipts() {
        return deliveryReceipts;
    }
    public List<PaymentReconciliation> getPaymentReconciliations() {
        return paymentReconciliations;
    }
    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }
    public List<SupplierPerformance> getSupplierPerformances() {
        return supplierPerformances;
    }

    // helper methods
    public static void validatePurchaseRequisition(PurchaseRequisition requisition){
        if (requisition == null){
            throw new IllegalArgumentException("Purchase requisition cannot be null");
        }
        validateString(requisition.getRequisitionId(),"Requisition Id");
        validateDate(requisition.getRequisitionDate(),"Requisition Date");
        validateNotNull(requisition.getDepartment(),"Department");
        validateNotNull(requisition.getCostCenter(), "Cost Center");
        validateNotNull(requisition.getRequestedBy(),"Requested By");
        validateNotEmptyList(requisition.getItems(),"Requisition Items");
        validateDate(requisition.getExpectedDeliveryDate(),"Expected Delivery Date");
        validateDateOrder(requisition.getRequisitionDate(),requisition.getExpectedDeliveryDate());
    }
    public static void validateSupplierPerformance(SupplierPerformance supplierPerformance){
        validateNotNull(supplierPerformance,"Supplier Performance");
        validateSupplier(supplierPerformance.getSupplier());
        validateDate(supplierPerformance.getEvaluationDate(),"Evaluation Date");
        if (!supplierPerformance.getSupplier().isActive()){
            throw new IllegalArgumentException("we can't add any supplierModel performanceEvaluatorMetrics for not active supplierModel");
        }
        if (supplierPerformance.getQuantitativePerformanceMetrics() == null && supplierPerformance.getQualitativePerformanceMetrics() == null){
            throw new IllegalArgumentException("the supplierModel must have at least one not null performance indicator.");
        }
    }
    public static void validateContract(Contract contract){
        Objects.requireNonNull(contract);
        validateString(contract.getContractId(),"Contract Id");
        validateString(contract.getContractTitle(),"Contract Title");
        validateSupplier(contract.getSupplier());
        validateDate(contract.getStartDate(),"Contract Start Date");
        validateDate(contract.getEndDate(),"Contract End Date");
        validateBigDecimal(contract.getTotalCost(),"Contract Total Cost");
        validateNotEmptyList(contract.getPurchaseOrders(),"Purchase Orders");
        validateDateOrder(contract.getStartDate(),contract.getEndDate());
    }
    public static void validateSupplier(Supplier supplier) {
        validateNotNull(supplier, "Supplier ");
        validateString(supplier.getSupplierName(), "Supplier name");
        validateString(supplier.getSupplierCategory(), "Supplier Category");
        validateString(supplier.getTaxIdentificationNumber(), "Tax Identification Number");
        validateString(supplier.getRegistrationNumber(), "Registration Number");
        validateSupplierContactDetail(supplier.getSupplierContactDetail());
        validateItems(supplier.getExistedItems());
        validateSupplierPaymentMethod(supplier.getSupplierPaymentMethods());
        validateDate(supplier.getRegistrationDate(),"Registration Date");
    }
    public static void validatePurchaseOrder(PurchaseOrder purchaseOrder) {
        validateNotNull(purchaseOrder,"Purchase Order");
        validateString(purchaseOrder.getOrderId(), "Order ID");
        validateNotNull(purchaseOrder.getDepartment(), "Department");
        validateNotNull(purchaseOrder.getRequisitionList(), "Purchase Requisition");
        validateNotEmptyList(purchaseOrder.getRequisitionList(),"Purchase Requisition");
        validateDateNotInPast(purchaseOrder.getOrderDate(), "Order Date");
        validateNotNull(purchaseOrder.getSupplier(), "Supplier");
        validateString(purchaseOrder.getShippingMethod(), "Shipping Method");
        validateDate(purchaseOrder.getDeliveryDate(), "Delivery Date");
        validatePositiveValue(purchaseOrder.getTotalCost(), "Total Cost");
        BigDecimal totalRequisitionCost = purchaseOrder.getRequisitionList().stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (purchaseOrder.getTotalCost().compareTo(totalRequisitionCost) < 0){
            throw new IllegalArgumentException("cost for Order can't be less than cost for requisition.");
        }
    }
    public static void validateInvoice(Invoice invoice){
        validateString(invoice.getInvoiceId(),"Invoice Id");
        validateDate(invoice.getInvoiceDate(), " Invoice Date");
        validateNotNull(invoice.getSupplier(),"Supplier");
        validatePurchaseOrder(invoice.getPurchaseOrder());
        validateDate(invoice.getDueDate(),"Due Date");
        validateDate(invoice.getPaymentDate(), "Payment Date");
        validatePositiveValue(invoice.getTotalCosts(), "Invoice Total Costs");
        validateNotNull(invoice.getCreatedBy(),"User");

    }
    public static void validateDeliveryReceipt(DeliveryReceipt deliveryReceipt){
        validateString(deliveryReceipt.getReceiptId(),"Receipts Id");
        validateSupplier(deliveryReceipt.getSupplier());
        validatePurchaseOrder(deliveryReceipt.getPurchaseOrder());
        validateNotEmptyList(deliveryReceipt.getReceivedItems(),"Received Items");
        validatePositiveValue(deliveryReceipt.getTotalCost(),"deliveryReceipts TotalCosts");
        validateNotNull(deliveryReceipt.getReceivedBy(), "User");
        validateDate(deliveryReceipt.getDeliveryDate(), "Delivered Date.");
    }
    public static void validatePaymentReconciliation(PaymentReconciliation paymentReconciliation){
        validateInvoice(paymentReconciliation.getInvoice());
        validatePurchaseOrder(paymentReconciliation.getPurchaseOrder());
        validateDeliveryReceipt(paymentReconciliation.getDeliveryReceipt());
        validateString(paymentReconciliation.getCurrency(),"Currency");
        validateDate(paymentReconciliation.getReconciliationDate(), "Reconciliation Date");
        validatePositiveValue(paymentReconciliation.getActualPaidAmount(), "Actual Paid Amount");
    }
}
