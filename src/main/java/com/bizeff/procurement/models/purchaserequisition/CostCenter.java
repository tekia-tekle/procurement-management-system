package com.bizeff.procurement.models.purchaserequisition;

import com.bizeff.procurement.models.IdGenerator;

import java.math.BigDecimal;

public class CostCenter {
    private Long id;
    private String costCenterId;
    private String name;
    private String description;
    private BigDecimal allocatedBudget = BigDecimal.ZERO;
    private BigDecimal usedBudget = BigDecimal.ZERO;
    private BigDecimal remainingBudget = BigDecimal.ZERO;
    private Department department;

    public CostCenter(){}
    public CostCenter(String name, String description) {
        this.costCenterId = IdGenerator.generateId("CC");
        this.name = name;
        this.description = description;
    }

    //     Allocate budget from Department
    public void allocateBudget(BigDecimal amount) {
        this.allocatedBudget = this.allocatedBudget.add(amount);
        this.remainingBudget = this.remainingBudget.add(amount);
    }

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

    public BigDecimal getAllocatedBudget() {
        return allocatedBudget;
    }

    public BigDecimal getUsedBudget() {
        return usedBudget;
    }

    public BigDecimal getRemainingBudget() {
        return remainingBudget;
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

    public void setAllocatedBudget(BigDecimal allocatedBudget) {
        this.allocatedBudget = allocatedBudget;
    }

    public void setUsedBudget(BigDecimal usedBudget) {
        this.usedBudget = usedBudget;
    }

    public void setRemainingBudget(BigDecimal remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "CostCenter{" +
                "id=" + id +
                ", costCenterId='" + costCenterId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", allocatedBudget=" + allocatedBudget +
                ", usedBudget=" + usedBudget +
                ", remainingBudget=" + remainingBudget +
                '}';
    }
}
