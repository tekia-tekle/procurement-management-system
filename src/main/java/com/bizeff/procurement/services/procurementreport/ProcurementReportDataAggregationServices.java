package com.bizeff.procurement.services.procurementreport;

import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;
import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;
import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.invoicepaymentreconciliation.ContractPaymentTermsEnforcementService.validateDateOrder;
import static com.bizeff.procurement.services.procurementreport.ProcurementReportGenerationServices.*;
import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateDate;
import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateString;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateNotNull;

public class ProcurementReportDataAggregationServices {
    private List<Department> departmentList;
    private List<PurchaseRequisition> requisitions;
    private List<Supplier> suppliers;
    private List<PurchaseOrder> purchaseOrders;
    private List<Contract> contracts;
    private List<Invoice> invoices;
    private List<DeliveryReceipt> deliveryReceipts;
    private List<PaymentReconciliation> paymentReconciliations;
    private List<SupplierPerformance> supplierPerformanceList;
    public ProcurementReportDataAggregationServices(){
        this.departmentList = new ArrayList<>();
        this.requisitions = new ArrayList<>();
        this.suppliers = new ArrayList<>();
        this.purchaseOrders = new ArrayList<>();
        this.contracts = new ArrayList<>();
        this.invoices = new ArrayList<>();
        this.deliveryReceipts = new ArrayList<>();
        this.paymentReconciliations = new ArrayList<>();
        this.supplierPerformanceList = new ArrayList<>();
    }
    public Department addDepartment(Department department){
        validateDepartment(department);
        departmentList.add(department);
        return department;
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
        purchaseOrders.add(purchaseOrder);
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
        paymentReconciliations.add(paymentReconciliation);
        return paymentReconciliation;
    }
    public SupplierPerformance addSupplierPerformance(SupplierPerformance supplierPerformance){
        validateSupplierPerformance(supplierPerformance);
        supplierPerformanceList.add(supplierPerformance);
        return supplierPerformance;
    }

    // expected spending for items when requesting
    public BigDecimal expectedspendingWhenRequestingItems(String requisitionId){
        PurchaseRequisition requisition = getPurchaseRequisitionById(requisitionId);
        return requisition.getTotalEstimatedCosts().setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal expectedSpendingWhenRequestingItemsByDepartment(String departmentId){
        validateString(departmentId,"Department Id");
        List<PurchaseRequisition> requisitionList = requisitions.stream()
                .filter(requisition -> requisition.getDepartment().getDepartmentId().equals(departmentId))
                .collect(Collectors.toList());
        if (requisitionList == null || requisitionList.isEmpty()){
            throw new IllegalArgumentException("there is no requisition with this department.");
        }
        BigDecimal totalSpending = BigDecimal.ZERO;
        totalSpending = requisitionList.stream().map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal expectedSpendingWhenRequestingItemsByTimeRange(LocalDate startDate,LocalDate endDate){
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);
        List<PurchaseRequisition> requisitionList = requisitions.stream()
                .filter(requisition -> !requisition.getRequisitionDate().isBefore(startDate) && !requisition.getRequisitionDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (requisitionList == null || requisitionList.isEmpty()){
            throw new IllegalArgumentException("there is no requisition with this date range.");
        }
        BigDecimal totalSpending = requisitionList.stream().map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    // expected total spending when ordering items.
    public BigDecimal aggregateTotalSpendingWhenOrderingItems(LocalDate startDate, LocalDate endDate){
        validateDate(startDate,"Start Date");
        validateDate(startDate,"End Date");
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        List<PurchaseOrder> filteredOrders = purchaseOrders.stream()
                .filter(order -> !order.getOrderDate().isBefore(startDate) && !order.getOrderDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (filteredOrders == null || filteredOrders.isEmpty()){
            throw new IllegalArgumentException("there is no purchase order with this date range.");
        }

        BigDecimal totalSpending = filteredOrders.stream().map(PurchaseOrder::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal aggregateTotalSpendingWhenOrderingItemsByDepartment(String departmentId, LocalDate startDate, LocalDate endDate){
        validateString(departmentId,"Department Id");
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);
        List<PurchaseOrder>purchaseOrderList = purchaseOrders.stream()
                .filter(purchaseOrder -> purchaseOrder.getDepartment().equals(getDepartmentById(departmentId)))
                .filter(purchaseOrder -> !purchaseOrder.getOrderDate().isBefore(startDate) && !purchaseOrder.getOrderDate().isAfter(endDate))
                .collect(Collectors.toList());
        if (purchaseOrderList == null || purchaseOrderList.isEmpty()){
            throw new IllegalArgumentException("there is no purchase order with this department and date range.");
        }
        BigDecimal totalSpending = purchaseOrderList.stream().map(PurchaseOrder::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal aggregateTotalSpendingWhenOrderingItemsBySupplier(String supplierId, LocalDate startDate, LocalDate endDate){
        validateString(supplierId,"Supplier Id");
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);
        List<PurchaseOrder>purchaseOrderList = purchaseOrders.stream()
                .filter(purchaseOrder -> purchaseOrder.getSupplier().equals(getSupplierById(supplierId)))
                .filter(purchaseOrder -> !purchaseOrder.getOrderDate().isBefore(startDate) && !purchaseOrder.getOrderDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (purchaseOrderList == null || purchaseOrderList.isEmpty()){
            throw new IllegalArgumentException("there is no purchase order with this supplier and date range.");
        }
        BigDecimal totalSpending = purchaseOrderList.stream().map(PurchaseOrder::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal aggregateTotalSpendingWhenOrderingItemsByDepartmentAndSupplier(String departmentId, String supplierId, LocalDate startDate, LocalDate endDate){
        validateString(departmentId,"Department Id");
        validateString(supplierId,"Supplier Id");
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);
        List<PurchaseOrder>purchaseOrderList = purchaseOrders.stream()
                .filter(purchaseOrder -> purchaseOrder.getDepartment().equals(getDepartmentById(departmentId)))
                .filter(purchaseOrder -> purchaseOrder.getSupplier().equals(getSupplierById(supplierId)))
                .filter(purchaseOrder -> !purchaseOrder.getOrderDate().isBefore(startDate) && !purchaseOrder.getOrderDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (purchaseOrderList == null || purchaseOrderList.isEmpty()){
            throw new IllegalArgumentException("there is no purchase order with this department, supplier and date range.");
        }
        BigDecimal totalSpending = purchaseOrderList.stream().map(PurchaseOrder::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    // spending when made contract.
    public BigDecimal aggregateTotalSpendingforSingleContract(String contractId){
        validateString(contractId,"Contract Id");
        Contract contract = getContractById(contractId);
        BigDecimal totalSpending = contract.getPurchaseOrders().stream().map(PurchaseOrder::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal aggregateTotalSpendingWhenPreparingContract(LocalDate startDate,LocalDate endDate){
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);

        List<Contract> contractList =  contracts.stream()
                .filter(contract -> !contract.getStartDate().isBefore(startDate) && !contract.getEndDate().isAfter(endDate))
                .collect(Collectors.toList());
        if (contractList == null || contractList.isEmpty()){
            throw new IllegalArgumentException("there is no contract with this date range.");
        }
        BigDecimal totalSpending = contractList.stream().map(Contract::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal aggregateTotalSpendingWhenPreparingContractBySupplier(String supplierId, LocalDate startDate,LocalDate endDate){
        validateString(supplierId,"Supplier Id");
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);

        List<Contract> contractList =  contracts.stream()
                .filter(contract -> contract.getSupplier().getSupplierId().equals(getSupplierById(supplierId)) &&
                        !contract.getStartDate().isBefore(startDate) && !contract.getEndDate().isAfter(endDate))
                .collect(Collectors.toList());
        if (contractList == null || contractList.isEmpty()){
            throw new IllegalArgumentException("there is no contract with this supplier and date range.");
        }
        BigDecimal totalSpending = contractList.stream().map(Contract::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);

    }

    // spending when invoice is made.
    public BigDecimal aggregateTotalSpendingforSingleInvoice(String invoiceId){
        validateString(invoiceId,"Invoice Id");
        Invoice invoice = getInvoiceById(invoiceId);
        BigDecimal totalSpending = invoice.getTotalCosts();
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal aggregateTotalSpendingWhenInvoiceIsMade(LocalDate startDate, LocalDate endDate){
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);
        List<Invoice> invoiceList = invoices.stream()
                .filter(invoice -> !invoice.getInvoiceDate().isBefore(startDate) && !invoice.getInvoiceDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (invoiceList == null || invoiceList.isEmpty()){
            throw new IllegalArgumentException("there is no invoice with this date range.");
        }
        BigDecimal totalSpending = invoiceList.stream().map(Invoice::getTotalCosts).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);

    }
    public BigDecimal aggregateTotalSpendingWhenInvoiceIsMadeBySupplier(String supplierId, LocalDate startDate, LocalDate endDate){
        validateString(supplierId,"Supplier Id");
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);
        List<Invoice> invoiceList = invoices.stream()
                .filter(invoice -> invoice.getSupplier().equals(getSupplierById(supplierId)) &&
                        !invoice.getInvoiceDate().isBefore(startDate) && !invoice.getInvoiceDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (invoiceList == null || invoiceList.isEmpty()){
            throw new IllegalArgumentException("there is no invoice with this supplier and date range.");
        }
        BigDecimal totalSpending = invoiceList.stream().map(Invoice::getTotalCosts).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    // spending when delivery Receipt made.
    public BigDecimal aggregateTotalSpendingforSingleDeliveryReceipt(String deliveryReceiptId){
        validateString(deliveryReceiptId,"Delivery Receipt Id");
        DeliveryReceipt deliveryReceipt = getDeliveryReceiptById(deliveryReceiptId);
        BigDecimal totalSpending = deliveryReceipt.getTotalCost();
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal aggregateTotalSpendingWhenDeliveryReceiptIsMadeByTimeRange(LocalDate startDate, LocalDate endDate){
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);
        List<DeliveryReceipt> deliveryReceiptList = deliveryReceipts.stream()
                .filter(deliveryReceipt -> !deliveryReceipt.getDeliveryDate().isBefore(startDate) &&
                        !deliveryReceipt.getDeliveryDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (deliveryReceiptList == null || deliveryReceiptList.isEmpty()){
            throw new IllegalArgumentException("there is no delivery receipt with this date range.");
        }
        BigDecimal totalSpending = deliveryReceiptList.stream().map(DeliveryReceipt::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal aggregateTotalSpendingWhenDeliveryReceiptIsMadeBySupplier(String supplierId, LocalDate startDate, LocalDate endDate){
        validateString(supplierId,"Supplier Id");
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);

        List<DeliveryReceipt> deliveryReceiptList = deliveryReceipts.stream()
                .filter(deliveryReceipt -> deliveryReceipt.getSupplier().getSupplierId().equals(supplierId) &&
                        !deliveryReceipt.getDeliveryDate().isBefore(startDate) &&
                        !deliveryReceipt.getDeliveryDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (deliveryReceiptList == null || deliveryReceiptList.isEmpty()){
            throw new IllegalArgumentException("there is no delivery receipt with this supplier and date range.");
        }
        BigDecimal totalSpending = deliveryReceiptList.stream().map(DeliveryReceipt::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    //total spending when payment Recinciliation made.
    public BigDecimal aggregateTotalSpendingforSpecificPaymentReconciliation(String paymentReconciliationId){
        validateString(paymentReconciliationId,"Payment Reconciliation Id");
        PaymentReconciliation paymentReconciliation = getPaymentReconciliationById(paymentReconciliationId);
        BigDecimal totalSpending = paymentReconciliation.getActualPaidAmount(); // paymentReconciliation.getExpectedPaidAmount(); which is best fitt??

        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal aggregateTotalSpendingWhenPaymentReconciliationIsMade(LocalDate startDate, LocalDate endDate){
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliations.stream()
                .filter(paymentReconciliation -> !paymentReconciliation.getReconciliationDate().isBefore(startDate) &&
                        !paymentReconciliation.getReconciliationDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (paymentReconciliationList == null || paymentReconciliationList.isEmpty()){
            throw new IllegalArgumentException("there is no payment reconciliation with this date range.");
        }
        BigDecimal totalSpending = paymentReconciliationList.stream().map(PaymentReconciliation::getActualPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public BigDecimal aggregateTotalSpendingWhenPaymentReconciliationIsMadeBySupplier(String supplierId, LocalDate startDate, LocalDate endDate){
        validateString(supplierId,"Supplier Id");
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliations.stream()
                .filter(paymentReconciliation -> paymentReconciliation.getInvoice().getSupplier().getSupplierId().equals(supplierId) &&
                        !paymentReconciliation.getReconciliationDate().isBefore(startDate) &&
                        !paymentReconciliation.getReconciliationDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (paymentReconciliationList == null || paymentReconciliationList.isEmpty()){
            throw new IllegalArgumentException("there is no payment reconciliation with this supplier and date range.");
        }
        BigDecimal totalSpending = paymentReconciliationList.stream().map(PaymentReconciliation::getActualPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalSpending.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    public Map<RequisitionStatus, Long> aggregateRequisitionStatusByDepartment(String departmentId, LocalDate startDate, LocalDate endDate){
        validateString(departmentId,"Department Id");
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        Department department = getDepartmentById(departmentId);
        List<PurchaseRequisition> requisitionList = requisitions.stream()
                .filter(purchaseRequisition -> purchaseRequisition.getDepartment().equals(department))
                .collect(Collectors.toList());
        if (requisitionList == null || requisitionList.isEmpty()){
            throw new IllegalArgumentException("there is no requisition with this department.");
        }
        Map<RequisitionStatus,Long> requisitionsWithStatus = new HashMap<>();
        for (PurchaseRequisition requisition:requisitionList){
            RequisitionStatus status = requisition.getRequisitionStatus();
            requisitionsWithStatus.put(status,requisitionsWithStatus.getOrDefault(status,0L)+1L);
        }
        return requisitionsWithStatus;
    }
    public Map<RequisitionStatus,Long>aggregateTotalRequisitionStatusByDateRange(LocalDate startDate, LocalDate endDate){
        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");
        validateDateOrder(startDate,endDate);
        List<PurchaseRequisition> requisitionList = requisitions.stream()
                .filter(purchaseRequisition -> !purchaseRequisition.getRequisitionDate().isBefore(startDate) && !purchaseRequisition.getRequisitionDate().isAfter(endDate))
                .collect(Collectors.toList());

        if (requisitionList == null || requisitionList.isEmpty()){
            throw new IllegalArgumentException("there is no requisition with this date range.");
        }
        Map<RequisitionStatus,Long> requisitionsWithStatus = new HashMap<>();
        for (PurchaseRequisition requisition:requisitionList){
            RequisitionStatus status = requisition.getRequisitionStatus();
            requisitionsWithStatus.put(status,requisitionsWithStatus.getOrDefault(status,0L)+1L);
        }
        return requisitionsWithStatus;
    }
    public double averageSupplierPerformance(String supplierId){
        Supplier supplier =  getSupplierById(supplierId);

        List<SupplierPerformance> supplierPerformances = supplierPerformanceList.stream()
                .filter(supplierPerformance -> supplierPerformance.getSupplier().equals(supplier))
                .collect(Collectors.toList());

        if (supplierPerformances == null || supplierPerformances.isEmpty()){
            throw new IllegalArgumentException("the supplier is not evaluated before.");
        }

        double totalPerformanceScore = 0.0;
        for (SupplierPerformance performance : supplierPerformances) {
            totalPerformanceScore += getQualitativePerformance(performance) + getQuantitativePerformance(performance);
        }
        double averagePerformanceScore = 0.0;
        averagePerformanceScore = totalPerformanceScore / supplierPerformances.size();

        return Math.round(averagePerformanceScore * 100.0) / 100.0;
    }
    public double latestSupplierPerformance(String supplierId){
        double performanceScore = 0.0;
        Supplier supplier = getSupplierById(supplierId);
        List<SupplierPerformance> supplierPerformances = supplierPerformanceList.stream()
                .filter(supplierPerformance -> supplierPerformance.getSupplier().equals(supplier))
                .collect(Collectors.toList());
        if (supplierPerformances ==null || supplierPerformances.isEmpty()){
            throw new IllegalArgumentException("the supplier is not evaluated before.");
        }

        SupplierPerformance latestSupplierPerformance = supplierPerformances.stream()
                .max(Comparator.comparing(SupplierPerformance::getEvaluationDate))
                .orElseThrow(null);

        performanceScore = getQualitativePerformance(latestSupplierPerformance) + getQuantitativePerformance(latestSupplierPerformance);
        return performanceScore;
    }
    public List<SupplierPerformance> listSupplierPerformance(String supplierId){
        Supplier supplier = getSupplierById(supplierId);
        List<SupplierPerformance> supplierPerformances = supplierPerformanceList.stream()
                .filter(supplierPerformance -> supplierPerformance.getSupplier().equals(supplier))
                .collect(Collectors.toList());

        if (supplierPerformances ==null || supplierPerformances.isEmpty()){
            throw new IllegalArgumentException("the supplier is not evaluated before.");
        }
        return supplierPerformances;
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
    public Department getDepartmentById(String departmentId){
        validateString(departmentId, "Department Id");
        return departmentList.stream().filter(department -> department.getDepartmentId().equals(departmentId))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("there is no department that is saved before wirh DepartmentId:"+ departmentId));
    }
    public Supplier getSupplierById(String supplierId){
        validateString(supplierId, "Supplier Id");
        return suppliers.stream()
                .filter(supplier -> supplier.getSupplierId().equals(supplierId))
                .findFirst().orElseThrow(()-> new IllegalArgumentException("there is no supplier that is registered by supplier Id:"+supplierId));
    }
    public PurchaseRequisition getPurchaseRequisitionById(String requisitionId){
        validateString(requisitionId, "Requisition Id");
        return requisitions.stream()
                .filter(requisition -> requisition.getRequisitionId().equals(requisitionId))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("there is no requisition that is saved before wirh RequisitionId:"+ requisitionId));
    }
    public PurchaseOrder getPurchaseOrderById(String purchaseOrderId){
        validateString(purchaseOrderId, "Purchase Order Id");
        return purchaseOrders.stream()
                .filter(purchaseOrder -> purchaseOrder.getOrderId().equals(purchaseOrderId))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("there is no purchase order that is saved before wirh PurchaseOrderId:"+ purchaseOrderId));
    }
    public Contract getContractById(String contractId){
        validateString(contractId, "Contract Id");
        return contracts.stream()
                .filter(contract -> contract.getContractId().equals(contractId))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("there is no contract that is saved before wirh ContractId:"+ contractId));
    }
    public Invoice getInvoiceById(String invoiceId){
        validateString(invoiceId, "Invoice Id");
        return invoices.stream()
                .filter(invoice -> invoice.getInvoiceId().equals(invoiceId))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("there is no invoice that is saved before wirh InvoiceId:"+ invoiceId));
    }
    public DeliveryReceipt getDeliveryReceiptById(String receiptId){
        validateString(receiptId, "receipt Id");
        return deliveryReceipts.stream()
                .filter(deliveryReceipt -> deliveryReceipt.getReceiptId().equals(receiptId))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("there is no delivery receipt that is saved before with receiptId:"+ receiptId));
    }
    public PaymentReconciliation getPaymentReconciliationById(String paymentId){
        validateString(paymentId, "PaymentId");
        return paymentReconciliations.stream()
                .filter(paymentReconciliation -> paymentReconciliation.getPaymentId().equals(paymentId))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("there is no payment reconciliation that is saved before with paymentId:"+ paymentId));

    }
    public List<Department> getDepartmentList() {
        return departmentList;
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

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public List<DeliveryReceipt> getDeliveryReceipts() {
        return deliveryReceipts;
    }

    public List<PaymentReconciliation> getPaymentReconciliations() {
        return paymentReconciliations;
    }

    public List<SupplierPerformance> getSupplierPerformanceList() {
        return supplierPerformanceList;
    }
    public void validateDepartment(Department department){
        validateNotNull(department, "Department");
        validateString(department.getDepartmentId(), "Department Id");
        validateString(department.getName(), "Department Name");
        validateString(department.getDescription(), "Department Description");
        validateNotNull(department.getBudget(), "Department Budget");
    }


}
