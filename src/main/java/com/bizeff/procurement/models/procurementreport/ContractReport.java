package com.bizeff.procurement.models.procurementreport;

import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.ContractStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ContractReport {
    private Long id;
    private Long totalContracts;
    private BigDecimal totalContractsCost;
    private Map<ContractStatus, Integer> contractsByStatus;
    private Map<String, BigDecimal> totalContractCostsPerSupplier;
    private Map<String, Integer> contractsWithSupplier;
    private List<Contract> expiringContracts;
    public ContractReport(){}
    public ContractReport(Long totalContracts,
                          BigDecimal totalContractsCost,
                          Map<ContractStatus, Integer> contractsByStatus,
                          Map<String, BigDecimal> totalContractCostsPerSupplier,
                          Map<String, Integer> contractsWithSupplier,
                          List<Contract> expiringContracts)
    {
        this.totalContracts = totalContracts;
        this.totalContractsCost = totalContractsCost;
        this.contractsByStatus = contractsByStatus;
        this.totalContractCostsPerSupplier = totalContractCostsPerSupplier;
        this.contractsWithSupplier = contractsWithSupplier;
        this.expiringContracts = expiringContracts;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTotalContracts() {
        return totalContracts;
    }
    public void setTotalContracts(Long totalContracts) {
        this.totalContracts = totalContracts;
    }
    public BigDecimal getTotalContractsCost() {
        return totalContractsCost;
    }
    public void setTotalContractsCost(BigDecimal totalContractsCost) {
        this.totalContractsCost = totalContractsCost;
    }
    public Map<ContractStatus, Integer> getContractsByStatus() {
        return contractsByStatus;
    }
    public void setContractsByStatus(Map<ContractStatus, Integer> contractsByStatus) {
        this.contractsByStatus = contractsByStatus;
    }
    public Map<String, BigDecimal> getTotalContractCostsPerSupplier() {
        return totalContractCostsPerSupplier;
    }
    public void setTotalContractCostsPerSupplier(Map<String, BigDecimal> totalContractCostsPerSupplier) {
        this.totalContractCostsPerSupplier = totalContractCostsPerSupplier;
    }
    public Map<String, Integer> getContractsWithSupplier() {
        return contractsWithSupplier;
    }
    public void setContractsWithSupplier(Map<String, Integer> contractsWithSupplier) {
        this.contractsWithSupplier = contractsWithSupplier;
    }
    public List<Contract> getExpiringContracts() {
        return expiringContracts;
    }
    public void setExpiringContracts(List<Contract> expiringContracts) {
        this.expiringContracts = expiringContracts;
    }
}
