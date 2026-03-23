package com.bizeff.procurement.services.supplierperformancetracking;

import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;
import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.purchaserequisition.RequestedItem;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateString;
public class LinkingSupplierPerformanceMetricsService {
    private List<SupplierPerformance> supplierPerformanceList;
    private Map<String, PurchaseRequisition> purchaseRequisitionMap;
    private Map<String, PurchaseOrder> purchaseOrderMap;
    private Map<String, Contract> contractMap;
    private Map<String, Invoice> invoiceMap;
    private Map<String, DeliveryReceipt> deliveryReceiptMap;
    private Logger logger = Logger.getLogger(LinkingSupplierPerformanceMetricsService.class.getName());
    public Map<String, Supplier> supplierMap;

    public LinkingSupplierPerformanceMetricsService(){
        this.supplierPerformanceList = new ArrayList<>();
        this.purchaseRequisitionMap = new HashMap<>();
        this.purchaseOrderMap = new HashMap<>();
        this.contractMap = new HashMap<>();
        this.invoiceMap = new HashMap<>();
        this.deliveryReceiptMap = new HashMap<>();
        this.supplierMap = new HashMap<>();
    }
    public Supplier addSupplier(Supplier supplier){
        validateSupplier(supplier);
        boolean supplierExists = supplierMap.containsKey(supplier.getSupplierId());
        if (supplierExists) {
            throw new IllegalArgumentException("we can't add new supplier with supplier Id:"+ supplier.getSupplierId()+"since it already exists.");
        }
        supplierMap.put(supplier.getSupplierId(), supplier);
        return supplier;
    }
    public SupplierPerformance addSupplierPerformance(SupplierPerformance supplierPerformance){
        validateSupplierPerformance(supplierPerformance);
        boolean supplierPerformanceExists = supplierPerformanceList.stream().anyMatch(sp -> sp.getSupplier().getSupplierId().equals(supplierPerformance.getSupplier().getSupplierId()) &&
                sp.getEvaluationDate().equals(supplierPerformance.getEvaluationDate()) &&
                sp.getQualitativePerformanceMetrics().equals(supplierPerformance.getQualitativePerformanceMetrics()) &&
                sp.getQuantitativePerformanceMetrics().equals(supplierPerformance.getQuantitativePerformanceMetrics()));
        if (supplierPerformanceExists) {
            throw new IllegalArgumentException("we can't add new supplier performance with supplier Id:"+ supplierPerformance.getSupplier().getSupplierId()+"since it already exists.");
        }
        supplierPerformanceList.add(supplierPerformance);
        return supplierPerformance;
    }
    public PurchaseRequisition linkPurchaseRequisitionBasedOnSpecificPerformanceMetrics(String requisitionId){
        PurchaseRequisition purchaseRequisition = getPurchaseRequisitionById(requisitionId);
        if (purchaseRequisition.getSupplier() != null) {
            List<RequestedItem> items = purchaseRequisition.getItems();
            List<Supplier> supplierList = suppliersWithSameInventoryItems(items);
            boolean supplierExists = supplierList.stream().anyMatch(s -> s.getSupplierId().equals(purchaseRequisition.getSupplier().getSupplierId()));
            if (!supplierExists){
                throw new IllegalArgumentException("supplier doesn't exist");
            }
            Supplier topSupplier = topSupplier(supplierList, ProcurementActivity.PURCHASE_REQUISITION);
            if (!purchaseRequisition.getSupplier().getSupplierId().equals(topSupplier.getSupplierId())) {
                purchaseRequisition.setRequisitionStatus(RequisitionStatus.REJECTED);
                logger.info("purchase requisition rejected since the supplier does not have high purchase requisition related performance metrics.");
                return purchaseRequisition;
            }
            else {
                purchaseRequisition.setRequisitionStatus(RequisitionStatus.APPROVED);
                logger.info("purchase requisition approved since the supplier has high purchase requisition related performance metrics.");
                return purchaseRequisition;
            }
        }
        logger.info("this requisition doesn't select the Supplier at this time and select the right supplier when ordering.");
        return purchaseRequisition;
    }
    public PurchaseOrder linkPurchaseOrderBasedOnSpecificPerformanceMetrics(String orderId) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(orderId);
        List<PurchaseRequisition> requisitionList = purchaseOrder.getRequisitionList();
        Supplier supplier = purchaseOrder.getSupplier();

        List<RequestedItem> requestedItems = requisitionList.stream().map(PurchaseRequisition::getItems).flatMap(List::stream).collect(Collectors.toList());
        List<Supplier> supplierList = suppliersWithSameInventoryItems(requestedItems);
        boolean existedSupplier = supplierList.stream().anyMatch(s -> s.getSupplierId().equals(supplier.getSupplierId()));
        if (!existedSupplier){
            throw new IllegalArgumentException("supplier doesn't exist");
        }

        Supplier topSupplier = topSupplier(supplierList,ProcurementActivity.PURCHASE_ORDER);

        if (!supplier.getSupplierId().equals(topSupplier.getSupplierId())) {
            logger.warning("select the right supplier,since there is supplier with high performance than:" + supplier.getSupplierName());
            purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.TERMINATED);
            logger.info("purchase order terminated since the supplier does not have high Purchase order related performance.");
        }
        else {
            purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
            logger.info("purchase order approved since the supplier has high Purchase order related performance metrics.");
        }
        return purchaseOrder;
    }
    public Contract linkContractBasedOnSpecificPerformanceMetrics(String contractId){
        Contract contract = getContractById(contractId);
        List<PurchaseOrder> purchaseOrderList = contract.getPurchaseOrders();
        Supplier supplier = contract.getSupplier();

        List<PurchaseRequisition> requisitionList = purchaseOrderList.stream().map(PurchaseOrder::getRequisitionList).flatMap(List::stream).collect(Collectors.toList());
        List<RequestedItem> requestedItems  = requisitionList.stream().map(PurchaseRequisition::getItems).flatMap(List::stream).collect(Collectors.toList());
        List<Supplier> supplierList = suppliersWithSameInventoryItems(requestedItems);
        boolean existedSupplier = supplierList.stream().anyMatch(s -> s.getSupplierId().equals(supplier.getSupplierId()));

        if (!existedSupplier){
            throw new IllegalArgumentException("supplier doesn't exist");
        }
        Supplier topSupplier = topSupplier(supplierList,ProcurementActivity.CONTRACT_MANAGEMENT);
        if (!supplier.getSupplierId().equals(topSupplier.getSupplierId())) {
            contract.setStatus(ContractStatus.TERMINATED);
            logger.info("contract terminated since the supplier does not have high contract related performance metrics.");
        }
        else {
            contract.setStatus(ContractStatus.ACTIVE);
            logger.info("contract approved since the supplier have high contract related performance metrics.");
        }
        return contract;
    }

    public PurchaseRequisition linkPurchaseRequisitionBasedOnOverallPerformance(String requisitionId){
        PurchaseRequisition purchaseRequisition = getPurchaseRequisitionById(requisitionId);
        if (purchaseRequisition.getSupplier() != null) {
            List<RequestedItem> items = purchaseRequisition.getItems();
            List<Supplier> supplierList = suppliersWithSameInventoryItems(items);
            boolean supplierExists = supplierList.stream().anyMatch(s -> s.getSupplierId().equals(purchaseRequisition.getSupplier().getSupplierId()));
            if (!supplierExists){
                throw new IllegalArgumentException("supplier doesn't exist");
            }
            Supplier topSupplier = topSupplier(supplierList);
            if (!purchaseRequisition.getSupplier().getSupplierId().equals(topSupplier.getSupplierId())) {
                logger.warning("select the right supplier,since there is supplier with high performance than:" + purchaseRequisition.getSupplier().getSupplierName());
                purchaseRequisition.setRequisitionStatus(RequisitionStatus.REJECTED);
            }
            else {
                purchaseRequisition.setRequisitionStatus(RequisitionStatus.APPROVED);
            }
        }
        else {
            logger.info("this requisition doesn't select the Supplier at this time and select the right supplier when ordering.");
            purchaseRequisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        }
        return purchaseRequisition;
    }
    public PurchaseOrder linkPurchaseOrderBasedOnOverallPerformance(String orderId) {
        PurchaseOrder purchaseOrder = getPurchaseOrderById(orderId);
        List<PurchaseRequisition> requisitionList = purchaseOrder.getRequisitionList();
        Supplier supplier = purchaseOrder.getSupplier();

        List<RequestedItem> requestedItems = requisitionList.stream().map(PurchaseRequisition::getItems).flatMap(List::stream).collect(Collectors.toList());
        List<Supplier> supplierList = suppliersWithSameInventoryItems(requestedItems);
        boolean existedSupplier = supplierList.stream().anyMatch(s -> s.getSupplierId().equals(supplier.getSupplierId()));
        if (!existedSupplier){
            throw new IllegalArgumentException("supplier doesn't exist");
        }

        Supplier topSupplier = topSupplier(supplierList);

        if (!supplier.getSupplierId().equals(topSupplier.getSupplierId())) {
            logger.warning("select the right supplier,since there is supplier with high performance than:" + supplier.getSupplierName());
            purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.TERMINATED);
            logger.info("purchase order terminated since the supplier is not the top supplier");
        }
        else {
            purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        }
        return purchaseOrder;
    }
    public Contract linkContractBasedOnOverallPerformance(String contractId){
        Contract contract = getContractById(contractId);
        List<PurchaseOrder> purchaseOrderList = contract.getPurchaseOrders();
        Supplier supplier = contract.getSupplier();
        List<PurchaseRequisition> requisitionList = purchaseOrderList.stream().map(PurchaseOrder::getRequisitionList).flatMap(List::stream).collect(Collectors.toList());
        List<RequestedItem> requestedItems  = requisitionList.stream().map(PurchaseRequisition::getItems).flatMap(List::stream).collect(Collectors.toList());
        List<Supplier> supplierList = suppliersWithSameInventoryItems(requestedItems);
        boolean existedSupplier = supplierList.stream().anyMatch(s -> s.getSupplierId().equals(supplier.getSupplierId()));
        if (!existedSupplier){
            throw new IllegalArgumentException("supplier doesn't exist");
        }
        Supplier topSupplier = topSupplier(supplierList);
        if (!supplier.getSupplierId().equals(topSupplier.getSupplierId())) {
            contract.setStatus(ContractStatus.TERMINATED);
            logger.info("contract terminated since the supplier is not the top supplier");
        }
        else {
            contract.setStatus(ContractStatus.ACTIVE);
        }
        return contract;
    }

    public List<Supplier> suppliersWithSameInventoryItems(List<RequestedItem> items){
        List<Supplier> supplierList = supplierMap.values().stream().filter(supplier -> supplier.getExistedItems().stream()
                        .anyMatch(item -> items.stream().anyMatch(requestedItem -> requestedItem.getInventory().getItemId().equals(item.getItemId()))))
                .collect(Collectors.toList());
        if (supplierList == null || supplierList.isEmpty()){
            throw new IllegalArgumentException("there is no supplier tha contain the items to be requested.");
        }
        return supplierList;
    }

    public Supplier topSupplier(List<Supplier> supplierList){
        Map<String, Double> supplierPerformanceMap = new HashMap<>();
        for (Supplier supplier : supplierList) {
            boolean existedPerformance = supplierPerformanceList.stream()
                    .anyMatch(supplierPerformance -> supplierPerformance.getSupplier().getSupplierId().equals(supplier.getSupplierId()));
            if (!existedPerformance){
                throw new IllegalArgumentException("supplier performance doesn't exist");
            }
            List<SupplierPerformance> supplierPerformances = supplierPerformanceList.stream()
                    .filter(supplierPerformance -> supplierPerformance.getSupplier().getSupplierId().equals(supplier.getSupplierId()))
                    .collect(Collectors.toList());

            double averagePerformanceScore = supplierPerformances.stream().mapToDouble(this::calculateSupplierPerformanceScore).average().orElse(0);
            supplierPerformanceMap.put(supplier.getSupplierId(), averagePerformanceScore);
        }
        //find key for the max value
        String topSupplierId = supplierPerformanceMap.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get().getKey();

        Supplier topSupplier = supplierList.stream().filter(supplier -> supplier.getSupplierId().equals(topSupplierId)).findFirst().orElseThrow();

        return topSupplier;
    }
    public Supplier topSupplier(List<Supplier> supplierList,ProcurementActivity procurementActivities){
        Map<String, Double> supplierPerformanceMap = new HashMap<>();
        for (Supplier supplier : supplierList) {
            boolean existedPerformance = supplierPerformanceList.stream()
                    .anyMatch(supplierPerformance -> supplierPerformance.getSupplier().getSupplierId().equals(supplier.getSupplierId()));
            if (!existedPerformance){
                throw new IllegalArgumentException("the supplier is not evaluated before.");
            }
            List<SupplierPerformance> supplierPerformances = supplierPerformanceList.stream()
                    .filter(supplierPerformance -> supplierPerformance.getSupplier().getSupplierId().equals(supplier.getSupplierId()))
                    .collect(Collectors.toList());
            double averagePerformanceScore = 0;
            if (procurementActivities ==ProcurementActivity.PURCHASE_REQUISITION){
                averagePerformanceScore = supplierPerformances.stream().mapToDouble(this::requisitionRelatedPerformanceScore).average().orElse(0);
            }
            if (procurementActivities == ProcurementActivity.PURCHASE_ORDER){
                averagePerformanceScore = supplierPerformances.stream().mapToDouble(this::purchaseOrderRelatedPerformanceScore).average().orElse(0);
            }
            if (procurementActivities == ProcurementActivity.CONTRACT_MANAGEMENT){
                averagePerformanceScore = supplierPerformances.stream().mapToDouble(this::contractRelatedPerformanceScore).average().orElse(0);
            }
            supplierPerformanceMap.put(supplier.getSupplierId(), averagePerformanceScore);
        }


        //find key for the max value
        String topSupplierId = supplierPerformanceMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        Supplier topSupplier = supplierList.stream().filter(supplier -> supplier.getSupplierId().equals(topSupplierId)).findFirst().orElseThrow();

        return topSupplier;
    }

    /** .*/
    public double calculateSupplierPerformanceScore(SupplierPerformance supplierPerformance){
        double qualitativePerformanceScore = getQualitativePerformanceScore(supplierPerformance);
        double quantitativePerformanceScore = getQuantitativePerformanceScore(supplierPerformance);
        return roundToTwoDecimalPlaces(qualitativePerformanceScore + quantitativePerformanceScore);
    }
    public double getQualitativePerformanceScore(SupplierPerformance performance){
        double qualitativePerformanceScore = 0.0;
        if (performance == null){
            throw new IllegalArgumentException("supplier performance evaluator metrics are not allocated for the supplier.");
        }
        if (performance.getQualitativePerformanceMetrics() == null){
            throw new IllegalArgumentException("we can't calculate qualitative performance score if the qualitative metrics is null");
        }
        qualitativePerformanceScore += (performance.getQualitativePerformanceMetrics().getContractAdherence() + performance.getQualitativePerformanceMetrics().getTechnicalExpertise() + performance.getQualitativePerformanceMetrics().getInnovation() + performance.getQualitativePerformanceMetrics().getQualityProducts() +
                performance.getQualitativePerformanceMetrics().getResponsiveness() + performance.getQualitativePerformanceMetrics().getCustomerSatisfaction()) / 6;
        return roundToTwoDecimalPlaces(qualitativePerformanceScore);
    }
    public double getQuantitativePerformanceScore(SupplierPerformance supplierPerformance){

        double quntitativePerformanceScore = 0.0;

        if (supplierPerformance == null){
            throw new IllegalArgumentException("supplier performance evaluator metrics are not allocated for the supplier.");
        }
        quntitativePerformanceScore += (supplierPerformance.getQuantitativePerformanceMetrics().getDeliveryRate() * 0.3) + (supplierPerformance.getQuantitativePerformanceMetrics().getAccuracyRate() * 0.2) + (supplierPerformance.getQuantitativePerformanceMetrics().getComplianceRate() * 0.2) +
                (supplierPerformance.getQuantitativePerformanceMetrics().getCostEfficiency() * 0.2) + (supplierPerformance.getQuantitativePerformanceMetrics().getCorrectionRate() * 0.1) - (supplierPerformance.getQuantitativePerformanceMetrics().getDefectsRate() * 0.2) - (supplierPerformance.getQuantitativePerformanceMetrics().getCanceledOrders() * 0.1);

        return roundToTwoDecimalPlaces(quntitativePerformanceScore);
    }

    public double requisitionRelatedPerformanceScore(SupplierPerformance supplierPerformance){
        double requisitionRelatedPerformanceScore = 0.0;
        requisitionRelatedPerformanceScore += (supplierPerformance.getQualitativePerformanceMetrics().getResponsiveness()*0.4) + (supplierPerformance.getQuantitativePerformanceMetrics().getAccuracyRate() * 0.3) + (supplierPerformance.getQuantitativePerformanceMetrics().getComplianceRate() * 0.2) + (supplierPerformance.getQualitativePerformanceMetrics().getCustomerSatisfaction()*0.1);
        return roundToTwoDecimalPlaces(requisitionRelatedPerformanceScore);
    }
    public double purchaseOrderRelatedPerformanceScore(SupplierPerformance supplierPerformance){
        double purchaseOrderRelatedPerformanceScore = 0.0;
        purchaseOrderRelatedPerformanceScore += (supplierPerformance.getQualitativePerformanceMetrics().getQualityProducts()*0.3) + (supplierPerformance.getQuantitativePerformanceMetrics().getDeliveryRate() * 0.3) - (supplierPerformance.getQuantitativePerformanceMetrics().getDefectsRate() * 0.2) -(supplierPerformance.getQuantitativePerformanceMetrics().getCanceledOrders()*0.1);
        return roundToTwoDecimalPlaces(purchaseOrderRelatedPerformanceScore);
    }

    public double contractRelatedPerformanceScore(SupplierPerformance supplierPerformance){
        double contractRelatedPerformanceScore = 0.0;
        contractRelatedPerformanceScore += (supplierPerformance.getQualitativePerformanceMetrics().getContractAdherence()*0.3) + (supplierPerformance.getQualitativePerformanceMetrics().getTechnicalExpertise()*0.3) + (supplierPerformance.getQuantitativePerformanceMetrics().getCostEfficiency() * 0.2) + (supplierPerformance.getQualitativePerformanceMetrics().getInnovation()*0.2);
        return roundToTwoDecimalPlaces(contractRelatedPerformanceScore);
    }
    public double averagePerformanceScore(String supplierId){
        validateString(supplierId,"SupplierModel Id");
        Supplier supplierModel = getSupplierById(supplierId);
        List<SupplierPerformance> supplierPerformanceList = getPerformanceBySupplierId(supplierModel.getSupplierId());

        return supplierPerformanceList.stream().mapToDouble(this::calculateSupplierPerformanceScore).average().orElse(0);

    }
    public List<SupplierPerformance> getPerformanceBySupplierId(String supplierId){
        validateString(supplierId,"SupplierModel Id");
        return supplierPerformanceList.stream().filter(supplierPerformance -> supplierPerformance.getSupplier().getSupplierId().equals(supplierId)).collect(Collectors.toList());

    }
    public Supplier getSupplierById(String supplierId){
        validateString(supplierId, "Supplier Id");
        Supplier supplier = supplierMap.get(supplierId);
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier with id " + supplierId + " does not exist");
        }
        return supplier;
    }
    public PurchaseRequisition getPurchaseRequisitionById(String requisitionId){
        validateString(requisitionId,"Requisition Id");
        PurchaseRequisition purchaseRequisition = purchaseRequisitionMap.get(requisitionId);
        if (purchaseRequisition == null) {
            throw new IllegalArgumentException("there is no purchase requisition with the given id.");
        }
        return purchaseRequisition;
    }
    public PurchaseOrder getPurchaseOrderById(String orderId){
        validateString(orderId,"Purchase Order Id");
        PurchaseOrder purchaseOrder = purchaseOrderMap.get(orderId);
        if (purchaseOrder == null) {
            throw new IllformedLocaleException("there is no purchase order with the given id.");
        }
        return purchaseOrder;
    }

    public Contract getContractById(String contractId){
        validateString(contractId,"Contract Id");
        Contract contract = contractMap.get(contractId);
        if (contract == null) {
            throw new IllegalArgumentException("there is no contract that is stored with the given id.");
        }
        return contract;
    }
    public Map<String, Supplier> getSupplierMap() {
        return supplierMap;
    }

    public Map<String, PurchaseOrder> getPurchaseOrderMap() {
        return purchaseOrderMap;
    }

    public Map<String, PurchaseRequisition> getPurchaseRequisitionMap() {
        return purchaseRequisitionMap;
    }

    public Map<String, Contract> getContractMap() {
        return contractMap;
    }

    public Map<String, Invoice> getInvoiceMap() {
        return invoiceMap;
    }

    public Map<String, DeliveryReceipt> getDeliveryReceiptMap() {
        return deliveryReceiptMap;
    }

    public List<SupplierPerformance> getSupplierPerformanceList() {
        return supplierPerformanceList;
    }

    // helper method.
    public void validateSupplierPerformance(SupplierPerformance supplierPerformance){
        validateString(supplierPerformance.getSupplier().getSupplierId(), "Supplier Id");

    }

    public void validateSupplier(Supplier supplier){

    }

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
