package com.bizeff.procurement.models.purchaserequisition;


import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.models.budget.Budget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private Long id;
    private String departmentId;
    private String name;
    private String description;
    private Budget budget; // Department has one Budget
    private List<CostCenter> costCenters = new ArrayList<>();

    public Department(){
    }
    public Department(String name,
                      String description,
                      Budget budget) {
        this.departmentId = IdGenerator.generateId("DI");
        this.name = name;
        this.description = description;
        this.budget = budget;
    }

    public void addCostCenter(CostCenter costCenter) {
        if (costCenter != null && !costCenters.contains(costCenter)) {
            this.costCenters.add(costCenter);
            costCenter.setDepartment(this);
        }
    }

    // Allocate Budget to a CostCenter
    public void allocateBudgetToCostCenter(CostCenter costCenter, BigDecimal allocationAmount) {
        if (allocationAmount.compareTo(budget.getTotalRemainingBudget()) > 0) {
            throw new IllegalArgumentException("Not enough budget to allocate.");
        }
        addCostCenter(costCenter);
        costCenter.allocateBudget(allocationAmount);
        budget.setTotalUsedBudget(allocationAmount);
        budget.reduceTotalRemainingBudget(allocationAmount);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Budget getBudget() {
        return budget;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<CostCenter> getCostCenters() {
        return costCenters;
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

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public void setCostCenters(List<CostCenter> costCenters) {
        this.costCenters = costCenters;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentId='" + departmentId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", budget=" + budget +
                ", costCenters=" + costCenters +
                '}';
    }
}
