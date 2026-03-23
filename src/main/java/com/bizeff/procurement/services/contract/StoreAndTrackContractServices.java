package com.bizeff.procurement.services.contract;

import com.bizeff.procurement.models.budget.Budget;
import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.contracts.ContractFile;
import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.models.enums.DeliveryTerms;
import com.bizeff.procurement.models.enums.PaymentTerms;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StoreAndTrackContractServices {
    private Map<String, Contract> contractMap;
    private Map<String, Supplier> supplierMap;
    private Map<String, PurchaseOrder> purchaseOrderMap;
    public StoreAndTrackContractServices(){
        this.contractMap = new HashMap<>();
        this.supplierMap = new HashMap<>();
        this.purchaseOrderMap = new HashMap<>();
    }

    // Create a new contract.
    public Contract createContract( String contractTitle,
                                    Supplier supplier,
                                    LocalDate startDate,
                                    LocalDate endDate,
                                    PaymentTerms paymentTerms,
                                    DeliveryTerms deliveryTerms,
                                    BigDecimal totalCost,
                                    boolean isRenewable,
                                    List<PurchaseOrder> purchaseOrders,
                                    List<ContractFile> attachments,
                                    LocalDate createdDate)
    {
        validateString(contractTitle, "Contract Title");

        validateNotNull(paymentTerms, "Payment Terms");
        validateNotNull(deliveryTerms, "Delivery Terms");

        validateDate(startDate, "Start Date");
        validateDateNotInPast(endDate, "End Date");
        validateDate(createdDate,"created Date ");

        validateChronologicalDates(createdDate, startDate, "Created Date", "Start Date");
        validateChronologicalDates(createdDate, endDate, "Created Date", "End Date");
        validateChronologicalDates(startDate, endDate, "Start Date", "End Date");

        validatePositiveValue(totalCost,"Total Cost");
        validateListNotEmpty(purchaseOrders,"purchase orders");
        validateAttachments(attachments);

        if (supplierMap.isEmpty()){
            throw new IllegalArgumentException("we can't create contract, since there is no supplier existed before.");
        }
        validateNotNull(supplier,"Supplier");
        getSupplierById(supplier.getSupplierId());



        validatePositiveValue(totalCost, "Total Cost");
        if (purchaseOrderMap.isEmpty()){
            throw new IllegalArgumentException("we can't create any contract, since there is no purchase ordered before.");
        }
        validateListNotEmpty(purchaseOrders,"List of Purchase Order");
        BigDecimal totalBudget = BigDecimal.ZERO;
        for (PurchaseOrder purchaseOrder: purchaseOrders){

            getPurchaseOrderById(purchaseOrder.getOrderId());
            String supplierId = purchaseOrder.getSupplier().getSupplierId();
            if (!supplier.getSupplierId().equals(supplierId)) {
                throw new IllegalArgumentException("The supplier in the purchase order does not match the contract supplier.");
            }

            isValidContractDates(purchaseOrder.getOrderDate(),startDate,purchaseOrder.getDeliveryDate(),endDate);
            Department department = purchaseOrder.getDepartment();
            if (department == null){
                throw new IllegalArgumentException("the department in the purchase order can't be null.");
            }
            Budget budget = department.getBudget();
            if (budget == null){
                throw new IllegalArgumentException("the budget in the department can't be null.");
            }
            totalBudget = totalBudget.add(budget.getTotalBudget());
        }
        Contract contract = new Contract(contractTitle, supplier, startDate, endDate, paymentTerms, deliveryTerms, totalCost, isRenewable, purchaseOrders, attachments, createdDate);
        if (totalCost.compareTo(totalBudget) > 0){
            contract.setStatus(ContractStatus.INACTIVE);
        }
        else {
            contract.setStatus(ContractStatus.ACTIVE);
        }
        if (contract.isRenewable()) {
            validateDate(contract.getRenewalDate(), "Renewal Date");
            if (contract.getRenewalDate().isBefore(contract.getCreatedDate())) {
                throw new IllegalArgumentException("Renewal date cannot be before the contract start date.");
            }
        }
        contractMap.put(contract.getContractId(),contract);

        return contract;
    }
    /** Update an existing contract.*/
    public Contract updateContract(String contractId,
                                   String newContractTitle,
                                   String newSupplierId,
                                   LocalDate updatedStartDate,
                                   LocalDate updatedEndDate,
                                   PaymentTerms newPaymentTerms,
                                   DeliveryTerms newDeliveryTerms,
                                   BigDecimal newTotalCost,
                                   List<PurchaseOrder> newPurchaseOrders,
                                   List<ContractFile> newAttachments,
                                   LocalDate lastRenewedDate){

        Contract contract = getContractById(contractId);
        if (!contract.isRenewable()){
            throw new IllegalArgumentException("we can't update the contract since it is not renewable contract.");
        }

        validateString(newContractTitle, "Contract Title");
        Supplier newSupplier = getSupplierById(newSupplierId);

        validateNotNull(newPaymentTerms, "Payment Terms");
        validateNotNull(newDeliveryTerms, "Delivery Terms");

        validateDate(updatedStartDate, "Start Date");
        validateDateNotInPast(updatedEndDate, "End Date");
        validateDate(lastRenewedDate,"Renewal Date ");

        validateChronologicalDates(updatedStartDate, updatedEndDate, "Start Date", "End Date");
        validateChronologicalDates(lastRenewedDate, updatedEndDate, "Renewal Date", "End Date");
        validateChronologicalDates(contract.getCreatedDate(), lastRenewedDate, "created Date", "Renewal Date");

        validatePositiveValue(newTotalCost,"Total Cost");
        validateListNotEmpty(newPurchaseOrders,"purchase orders");
        validateListNotEmpty(newAttachments,"attachments");

        for (PurchaseOrder purchaseOrder:newPurchaseOrders){
            getPurchaseOrderById(purchaseOrder.getOrderId());
            isValidContractDates(purchaseOrder.getOrderDate(),updatedStartDate, purchaseOrder.getDeliveryDate(),updatedEndDate);
        }

        contract.setContractTitle(newContractTitle);
        contract.setSupplier(newSupplier);
        contract.setStartDate(updatedStartDate);
        contract.setEndDate(updatedEndDate);
        contract.setPaymentTerms(newPaymentTerms);
        contract.setDeliveryTerms(newDeliveryTerms);
        contract.setTotalCost(newTotalCost);
        contract.setPurchaseOrders(newPurchaseOrders);
        contract.setAttachments(newAttachments);
        contract.setRenewalDate(lastRenewedDate);

        return contract;
    }

    /** Method to retrieve a contract by ID. */
    public Contract getContractById(String contractId) {
        if (contractId == null || contractId.trim().isEmpty()) {
            throw new NullPointerException("Contract ID can't be null or empty.");
        }
        return Optional.ofNullable(contractMap.get(contractId))
                .orElseThrow(()->new NoSuchElementException("there is no Contract in the contract map with key "+ contractId));
    }
    /** getting active contract.*/
    public List<Contract> getActiveContract() {
        return contractMap.values().stream()
                .filter(contract -> contract.getStatus().equals(ContractStatus.ACTIVE)).toList();
    }
    /** Get all contract for a specific supplier. */
    public List<Contract> getContractBySupplier(String supplierId){
        validateString(supplierId,"Supplier Id");
        
        return contractMap.values().stream().
                filter(contract -> contract.getSupplier().getSupplierId().equals(supplierId)).collect(Collectors.toList());
    }

    public void deleteContract(String contractId) {
        if (contractId == null || contractId.trim().isEmpty()){
            throw new NullPointerException("contract id can't be null.");
        }
        if (!contractMap.containsKey(contractId)) {
            throw new IllegalArgumentException("Contract not found with ID: " + contractId);
        }
        contractMap.remove(contractId);
    }
    public Contract terminateContract(String contractId) {
        if (contractId == null || contractId.trim().isEmpty()){
            throw new NullPointerException("contract id can't be null.");
        }
        if (!contractMap.containsKey(contractId)) {
            throw new IllegalArgumentException("Contract not found with ID: " + contractId);
        }
        Contract contract = getContractById(contractId);
        contract.setStatus(ContractStatus.TERMINATED);
        return contract;
    }
    public Map<String, Contract> getContract() {
        return contractMap;
    }
    public Supplier getSupplierById(String supplierId){
        validateString(supplierId, "Supplier Id");
        return Optional.ofNullable(supplierMap.get(supplierId)).orElseThrow(()->new NoSuchElementException("there is no supplier in the supplier map with key "+ supplierId));
    }
    public PurchaseOrder getPurchaseOrderById(String orderId){
        validateString(orderId,"Order Id");
        return Optional.ofNullable(purchaseOrderMap.get(orderId)).orElseThrow(()->new NoSuchElementException("there is no purchase order in the purchase order map with the key "+ orderId));
    }
    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be null or empty.");
        }
    }

    private void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null.");
        }
    }

    private void validateDate(LocalDate date, String fieldName) {
        if (date == null) {
            throw new IllegalArgumentException(fieldName + " must not be null.");
        }

    }
    private void validateDateNotInPast(LocalDate date, String fieldName){
        validateDate(date,fieldName);
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(fieldName + " must not be in the past.");
        }
    }
    private void validateChronologicalDates(LocalDate start, LocalDate end, String startName, String endName) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new IllegalArgumentException(startName + " must not be after " + endName + ".");
        }
    }

    private void validatePositiveValue(BigDecimal value, String fieldName) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(fieldName + " must be a positive value.");
        }
    }

    private void validateListNotEmpty(List<?> list, String fieldName) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be null or empty.");
        }
    }


    public void validateAttachments(List<ContractFile> attachments) {
        if (attachments == null) {
            throw new IllegalArgumentException("Attachments list cannot be null.");
        }

        if (attachments.isEmpty()) {
            throw new IllegalArgumentException("Attachments list cannot be empty. At least one attachment is required.");
        }
        for (ContractFile file: attachments){

            // Validate file type
            if (file.getFileType() == null || file.getFileType().trim().isEmpty()) {
                throw new IllegalArgumentException("File type cannot be null or empty.");
            }
            if (!isValidFileType(file.getFileType())) {
                throw new IllegalArgumentException("Invalid file type. Only .pdf and .docx files are allowed.");
            }
            if (file.getFileUrl() == null || file.getFileUrl().trim().isEmpty()){
                throw new IllegalArgumentException("the file directory can't be null or empty.");
            }
            // Validate upload timestamp
            if (file.getUploadDate() == null) {
                throw new IllegalArgumentException("Upload timestamp cannot be null.");
            }
            if (file.getUploadDate().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Upload timestamp cannot be in the future.");
            }
        }
    }
    public boolean isValidContractDates(LocalDate orderDate,
                                        LocalDate startDate,
                                        LocalDate deliveryDate,
                                        LocalDate endDate)
    {
        return orderDate.isBefore(startDate) &&
                startDate.isBefore(deliveryDate) &&
                deliveryDate.isBefore(endDate);
    }

    private static boolean isValidFileType(String fileType) {
        return fileType.trim().equalsIgnoreCase("pdf") || fileType.trim().equalsIgnoreCase("docx");
    }
    public Map<String, Supplier> getSupplierMap() {
        return supplierMap;
    }

    public Map<String, PurchaseOrder> getPurchaseOrderMap() {
        return purchaseOrderMap;
    }
}