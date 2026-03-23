package com.bizeff.procurement.services.contract;


import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.ContractStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AlertingContractsServices {
    private static Logger logger = Logger.getLogger(AlertingContractsServices.class.getName());
    private Map<String, Contract> contractMap;

    public AlertingContractsServices() {
        this.contractMap = new HashMap<>();
    }

    // Add a contract to the contract map.
    public Contract createContract(Contract contract) {
        validateContracts(contract);
        contractMap.put(contract.getContractId(), contract);
        return contract;
    }

    // Get contracts expiring within a specified number of days
    public List<Contract> getExpiringContracts(int daysBeforeExpiration) {
        if (daysBeforeExpiration <= 0) {
            throw new IllegalArgumentException("Days before expiration must be greater than 0.");
        }
        if (contractMap == null || contractMap.isEmpty()) {
            throw new IllegalArgumentException("the Contract map is null or empty.so that we haven't any expiring contracts.");
        }
        LocalDate today = LocalDate.now();
        LocalDate expirationThreshold = today.plusDays(daysBeforeExpiration);

        return contractMap.values().stream()
                .filter(contract -> contract.getEndDate().isBefore(expirationThreshold)
                        && contract.getEndDate().isAfter(today)
                        && contract.getStatus() == ContractStatus.ACTIVE) // Include only active contracts
                .sorted(Comparator.comparing(Contract::getEndDate)) // Sort by end date
                .collect(Collectors.toList());
    }

    public List<Contract> alertingContracts(int daysBeforeExpiration){

        List<Contract> expiringContracts = getExpiringContracts(daysBeforeExpiration);

        if (expiringContracts.isEmpty()) {
            throw new IllegalArgumentException("No contracts to be alerted.");
        }

        else {
            for (Contract contract : expiringContracts) {
                logger.warning("ALERT: Contract '" + contract.getContractTitle() + "' (ID: " + contract.getContractId() +
                        ") is expiring on " + contract.getEndDate() + ". Please take action.");
                contract.setNotified(true);
            }
        }
        return expiringContracts;
    }
    public boolean markForRenewal(String contractId, int daysBeforeExpiration) {
        if (contractId == null || contractId.trim().isEmpty()){
            throw new IllegalArgumentException("contract id can't be null or empty.but now the contract id is either of empty or null.");
        }
        if (daysBeforeExpiration <= 0) {
            throw new IllegalArgumentException("Days before expiration must be greater than 0.");
        }
        Contract contract = getContractById(contractId);
        List<Contract> expiringContracts = alertingContracts(daysBeforeExpiration);

        boolean isAlerted = expiringContracts.stream()
                .anyMatch(expiringContract -> expiringContract.getContractId().equals(contract.getContractId()));

        if (!isAlerted) {
            throw new IllegalArgumentException("The contract with ID " + contractId + " is not expiring within the given range of time.");
        }

        // Mark the contract for renewal
        logger.info("Contract '" + contract.getContractTitle() + "' (ID: " + contract.getContractId() + ") is marked for renewal.");
        return true;
    }

    public void initiateNegotiations(String contractId, int daysBeforeExpiration, LocalDate newEndDate) {
        if (contractId == null || contractId.trim().isEmpty()) {
            throw new IllegalArgumentException("Contract ID cannot be null or empty.");
        }
        if (daysBeforeExpiration  < 0 ) {
            throw new IllegalArgumentException("Expiration date must be a future date.");
        }
        if (newEndDate == null || newEndDate.isBefore(LocalDate.now().plusDays(daysBeforeExpiration))) {
            throw new IllegalArgumentException("End date must be after the expiration date.");
        }

        Contract contract = getContractById(contractId);

        logger.info("Initiating negotiations for contract '" + contract.getContractTitle() +
                "' (ID: " + contractId + ") with expiration date: " + daysBeforeExpiration +
                " and end date: " + newEndDate);

        contract.setStatus(ContractStatus.PENDING);
        contract.setEndDate(newEndDate);
    }

    public List<Contract> getContractsBySupplier(String supplierId) {
        return contractMap.values().stream()
                .filter(contract -> contract.getSupplier().getSupplierId().equals(supplierId))
                .collect(Collectors.toList());
    }

    public List<Contract> getActiveContracts() {
        return contractMap.values().stream()
                .filter(contract -> contract.getStatus() == ContractStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    public Contract getContractById(String contractId) {
        if (contractId == null || contractId.trim().isEmpty()) {
            throw new IllegalArgumentException("Contract ID cannot be null or empty.");
        }
        Contract contract = contractMap.get(contractId);
        if (contract == null) {
            throw new IllegalArgumentException("No contract found with ID: " + contractId);
        }
        return contract;
    }

    public Map<String, Contract> getAllContracts() {
        return contractMap;
    }

    public void validateContracts(Contract contract) {
        if (contract == null) {
            throw new NullPointerException("Contract is null.");
        }
        if (contract.getContractId() == null || contract.getContractId().trim().isEmpty()) {
            throw new IllegalArgumentException("Contract ID cannot be null or empty.");
        }
        if (contractMap.containsKey(contract.getContractId())) {
            throw new IllegalArgumentException("Duplicate contract ID is not allowed: " + contract.getContractId());
        }
        if (contract.getSupplier() == null) {
            throw new NullPointerException("SupplierModel is required and cannot be null.");
        }
        if (contract.getContractTitle() == null || contract.getContractTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Contract title must be specified.");
        }
        if (contract.getStartDate() == null || contract.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Contract start date cannot be in the past.");
        }
        if (contract.getEndDate().isBefore(contract.getStartDate())) {
            throw new IllegalArgumentException("Contract end date cannot be before the start date.");
        }
        if ((contract.getTotalCost().compareTo(BigDecimal.ZERO)) < 0) {
            throw new IllegalArgumentException("Total value cannot be negative.");
        }
        if (contract.getPurchaseOrders() == null || contract.getPurchaseOrders().isEmpty()) {
            throw new IllegalArgumentException("Purchase order cannot be null or empty.");
        }
    }
}