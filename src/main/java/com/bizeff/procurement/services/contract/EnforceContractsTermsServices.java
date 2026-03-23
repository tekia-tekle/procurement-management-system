package com.bizeff.procurement.services.contract;


import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.models.enums.PaymentTerms;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EnforceContractsTermsServices {
    private static Logger logger = Logger.getLogger(EnforceContractsTermsServices.class.getName());
    private Map<String, Contract> contractMap;

    public EnforceContractsTermsServices() {
        this.contractMap = new HashMap<>();
    }

    // Add a contract
    public Contract createContract(Contract contract) {
        validateContract(contract);
        contractMap.put(contract.getContractId(), contract);
        return contract;
    }

    // Retrieve a contract by ID
    public Contract getContractById(String contractId) {
        validateStringInput(contractId, "Contract ID");
        Contract contract = contractMap.get(contractId);
        if (contract == null) {
            throw new IllegalArgumentException("Contract not found with ID: " + contractId);
        }
        return contract;
    }

    // Get all active contracts
    public List<Contract> getActiveContracts() {
        List<Contract> activeContracts = contractMap.values().stream()
                .filter(contract -> contract.getStatus().equals(ContractStatus.ACTIVE))
                .collect(Collectors.toList());

        if (activeContracts.isEmpty()) {
            throw new IllegalArgumentException("No active contracts found for the given ID.");
        }
        return activeContracts;
    }

    // Enforce contract terms during transactions
    public Contract enforceTerms(String contractId, BigDecimal transactionCosts, LocalDate deliveryDate, PaymentTerms paymentTerm) {
        validateStringInput(contractId, "Contract ID");
        validateTransactionValue(transactionCosts);

        Contract contract = getContractById(contractId);

        // Check if the contract is active
        boolean isActiveContract = getActiveContracts().stream()
                .anyMatch(c -> c.getContractId().equals(contract.getContractId()));

        if (!isActiveContract) {
            throw new IllegalArgumentException("Cannot enforce an inactive contract.");
        }

        // Check transaction value
        if (transactionCosts.compareTo(contract.getTotalCost()) > 0) {
            contract.setStatus(ContractStatus.INACTIVE);
            logger.warning("Transaction rejected: transaction Costs exceeds contract's total costs. Contract marked as INACTIVE.");
        }
        else {
            // Check delivery date range
            if (deliveryDate.isBefore(contract.getStartDate()) || deliveryDate.isAfter(contract.getEndDate())) {
                throw new IllegalArgumentException("Delivery date is outside the contract validity period.");
            }

            // Validate payment terms
            if (!contract.getPaymentTerms().equals(paymentTerm)) {
                throw new IllegalArgumentException("Payment terms do not match the contract's payment terms.");
            }
            logger.warning("Transaction approved: Terms enforced successfully for contract ID:"+ contractId);
        }

        return contract;
    }


    // Terminate a contract
    public void terminateContract(String contractId) {
        validateStringInput(contractId, "Contract ID");

        Contract contract = getContractById(contractId);
        contract.setStatus(ContractStatus.TERMINATED);
        logger.info("Contract with ID: " + contract.getContractId() + " has been terminated.");
    }

    // Retrieve all contracts
    public Map<String, Contract> getAllContracts() {
        return contractMap;
    }

    // Validate contract details
    private void validateContract(Contract contract) {
        if (contract == null) {
            throw new IllegalArgumentException("Contract cannot be null.");
        }
        validateStringInput(contract.getContractId(), "Contract ID");

        if (contractMap.containsKey(contract.getContractId())) {
            throw new IllegalArgumentException("Duplicate contract ID is not allowed: " + contract.getContractId());
        }
        if (contract.getSupplier() == null) {
            throw new IllegalArgumentException("SupplierModel is required and cannot be null.");
        }
        validateStringInput(contract.getContractTitle(), "Contract Title");

        if (contract.getStartDate() == null || contract.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Contract start date cannot be null or in the past.");
        }
        if (contract.getEndDate().isBefore(contract.getStartDate())) {
            throw new IllegalArgumentException("Contract end date cannot be before the start date.");
        }
        if (contract.getTotalCost().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total value cannot be negative.");
        }
        if (contract.getPurchaseOrders() == null || contract.getPurchaseOrders().isEmpty()) {
            throw new IllegalArgumentException("Purchase order cannot be null or empty. It must contain at least one order.");
        }
    }

    // Utility method to validate string inputs
    private void validateStringInput(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
        }
    }

    // Utility method to validate transaction value
    private void validateTransactionValue(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction value must be positive.");
        }
    }
}