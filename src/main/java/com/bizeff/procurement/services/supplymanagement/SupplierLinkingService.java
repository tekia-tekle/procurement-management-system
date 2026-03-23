package com.bizeff.procurement.services.supplymanagement;


import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.util.*;

public class SupplierLinkingService {
    private Map<String, Supplier> supplierMap;
    private Map<String, List<PurchaseOrder>> purchaseOrdersByVendor;
    private Map<String, List<Contract>> contractsByVendor;

    public SupplierLinkingService() {
        this.supplierMap = new HashMap<>();
        this.purchaseOrdersByVendor = new HashMap<>();
        this.contractsByVendor = new HashMap<>();
    }

    /** Links a supplier to a purchase order */
    public void associatePurchaseOrder(String  supplierId, PurchaseOrder purchaseOrder) {
        if (purchaseOrder == null){
            throw new IllegalArgumentException("we can't associate, since the purchase order is null");
        }
        validateSupplierId(supplierId);
        List<PurchaseOrder> existingPurchaseOrders = purchaseOrdersByVendor.getOrDefault(supplierId, new ArrayList<>());

        boolean purchaseOrderExists = existingPurchaseOrders.stream().anyMatch(order -> order.getOrderId().equals(purchaseOrder.getOrderId()));
        if (!purchaseOrderExists){
            throw new IllegalArgumentException("Purchase Order association failed, there is no purchase order with the supplier ");
        }
        getPurchaseOrdersBySupplier(supplierId);
    }

    /** Links a supplier to a contract */
    public void associateContract(String supplierId, Contract contract) {
        if (contract == null){
            throw new IllegalArgumentException("we can't associate, since the contract is null.");
        }
        validateSupplierId(supplierId);

        // Get existing contracts for the supplier
        List<Contract> existingContracts = contractsByVendor.getOrDefault(supplierId, new ArrayList<>());

        // Check if the contract is already listed for the supplier
        boolean contractExists = existingContracts.stream()
                .anyMatch(existingContract -> existingContract.getContractId().equals(contract.getContractId()));

        if (!contractExists) {
            throw new IllegalArgumentException("Contract association failed: Contract does not exist for this supplier.");
        }

        // If the contract is found, associate it
        getContractsBySupplier(supplierId);
    }


    /** Retrieves purchase orders linked to a supplier */
    public List<PurchaseOrder> getPurchaseOrdersBySupplier(String supplierId) {
        validateSupplierId(supplierId);
        return purchaseOrdersByVendor.getOrDefault(supplierId, List.of());
    }

    /** Retrieves contracts linked to a supplier */
    public List<Contract> getContractsBySupplier(String  supplierId) {
        validateSupplierId(supplierId);
        return contractsByVendor.getOrDefault(supplierId, List.of());
    }
    /**Get Supplier by SupplierId*/
    public Supplier getSupplierById(String supplierId){
        return Optional.ofNullable(supplierMap.get(supplierId)).orElseThrow(()-> new NoSuchElementException("there is no supplier created before with the supplier Id: "+ supplierId));
    }

    public Map<String, Supplier> getSupplierMap() {
        return supplierMap;
    }

    public Map<String, List<Contract>> getContractsByVendor() {
        return contractsByVendor;
    }

    public Map<String, List<PurchaseOrder>> getPurchaseOrdersByVendor() {
        return purchaseOrdersByVendor;
    }

    /** Generate a supplier report including purchase orders and contract details */
    public String generateSupplierReport(String supplierId) {
        validateSupplierId(supplierId);
        Supplier supplier = supplierMap.get(supplierId);
        List<PurchaseOrder> orders = getPurchaseOrdersBySupplier(supplierId);
        List<Contract> contracts = getContractsBySupplier(supplierId);

        return String.format("Vendor Report for %s (%s)\n- Total Purchase Orders: %d\n- Active Contracts: %d",
                supplier.getSupplierName(), supplier.getSupplierCategory(), orders.size(), contracts.size());
    }

    /**Validate the SupplierId*/
    public void validateSupplierId(String supplierId){
        if (supplierId == null || supplierId.trim().isEmpty()){
            throw new IllegalArgumentException("supplierId is required field can't be null or Empty.");
        }
    }
}
