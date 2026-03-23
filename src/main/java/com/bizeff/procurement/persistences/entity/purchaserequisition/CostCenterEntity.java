package com.bizeff.procurement.persistences.entity.purchaserequisition;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cost_center")
public class CostCenterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Technical ID for database operations

    @Column(name = "cost_center_id", nullable = false, unique = true)
    private String costCenterId; // Business ID (CC-xxx)

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "allocated_budget", precision = 19, scale = 2)
    private BigDecimal allocatedBudget = BigDecimal.ZERO;

    @Column(name = "used_budget", precision = 19, scale = 2)
    private BigDecimal usedBudget = BigDecimal.ZERO;

    @Column(name = "remaining_budget", precision = 19, scale = 2)
    private BigDecimal remainingBudget = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "department_id",nullable = false)
    private DepartmentEntity department;

    @OneToMany(mappedBy = "costCenterEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseRequisitionEntity> purchaseRequisitionEntities = new ArrayList<>();

    // Constructors
    public CostCenterEntity() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(String costCenterId) {
        this.costCenterId = costCenterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAllocatedBudget() {
        return allocatedBudget;
    }

    public void setAllocatedBudget(BigDecimal allocatedBudget) {
        this.allocatedBudget = allocatedBudget;
    }

    public BigDecimal getUsedBudget() {
        return usedBudget;
    }

    public void setUsedBudget(BigDecimal usedBudget) {
        this.usedBudget = usedBudget;
    }

    public BigDecimal getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(BigDecimal remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        if (this.department != null) {
            this.department.getCostCenters().remove(this);
        }

        // Set new department
        this.department = department;

        // Add to new department's list
        if (department != null && !department.getCostCenters().contains(this)) {
            department.getCostCenters().add(this);
        }
    }

    public List<PurchaseRequisitionEntity> getPurchaseRequisitionEntities() {
        return purchaseRequisitionEntities;
    }

    public void setPurchaseRequisitionEntities(List<PurchaseRequisitionEntity> purchaseRequisitionEntities) {
        this.purchaseRequisitionEntities = purchaseRequisitionEntities;
    }

    @Override
    public String toString() {
        return "CostCenterEntity{" +
                "id=" + id +
                ", costCenterId='" + costCenterId + '\'' +
                ", name='" + name + '\'' +
                ", allocatedBudget=" + allocatedBudget +
                ", usedBudget=" + usedBudget +
                ", remainingBudget=" + remainingBudget +
                '}';
    }
}