package com.bizeff.procurement.persistences.entity.procurementreport;

import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.persistences.entity.contracts.ContractEntity;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "contract_reports")
public class ContractReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalContracts;
    private BigDecimal totalContractsCost;

    @ElementCollection
    @CollectionTable(name = "contracts_by_status", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "status")
    @Column(name = "count")
    private Map<ContractStatus, Integer> contractsByStatus; // Using String for status for simplicity
    @ElementCollection
    @CollectionTable(name = "total_contract_costs_per_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "total_cost")
    private Map<String, BigDecimal> totalContractCostsPerSupplier;

    @ElementCollection
    @CollectionTable(name = "contracts_with_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "contract_count")
    private Map<String, Integer> contractsWithSupplier;

    @ElementCollection
    @CollectionTable(name = "purchase_orders_per_contract", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "contract_id")
    @Column(name = "purchase_order_list")
    private List<PurchaseOrderEntity> purchaseOrdersPerContract; // Storing purchase order IDs as strings for simplicity

    @OneToMany
    private List<ContractEntity> expiringContracts;
    @OneToOne(mappedBy = "contractReport",cascade = CascadeType.ALL)
    private ProcurementReportEntity procurementReportEntity;

    public ContractReportEntity() {}
    // Getters and Setters
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
    public List<PurchaseOrderEntity> getPurchaseOrdersPerContract() {
        return purchaseOrdersPerContract;
    }
    public void setPurchaseOrdersPerContract(List<PurchaseOrderEntity> purchaseOrdersPerContract) {
        this.purchaseOrdersPerContract = purchaseOrdersPerContract;
    }
    public List<ContractEntity> getExpiringContracts() {
        return expiringContracts;
    }
    public void setExpiringContracts(List<ContractEntity> expiringContracts) {
        this.expiringContracts = expiringContracts;
    }
    public ProcurementReportEntity getProcurementReportEntity() {
        return procurementReportEntity;
    }
    public void setProcurementReportEntity(ProcurementReportEntity procurementReportEntity) {
        this.procurementReportEntity = procurementReportEntity;
    }
    @Override
    public String toString() {
        return "ContractReportEntity{" +
                "id=" + id +
                ", totalContracts=" + totalContracts +
                ", totalContractsCost=" + totalContractsCost +
                ", contractsByStatus=" + contractsByStatus +
                ", totalContractCostsPerSupplier=" + totalContractCostsPerSupplier +
                ", contractsWithSupplier=" + contractsWithSupplier +
                ", purchaseOrdersPerContract=" + purchaseOrdersPerContract +
                ", expiringContracts=" + expiringContracts +
                '}';
    }

}
