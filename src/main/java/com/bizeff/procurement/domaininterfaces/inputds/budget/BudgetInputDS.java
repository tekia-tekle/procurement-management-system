package com.bizeff.procurement.domaininterfaces.inputds.budget;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BudgetInputDS {
    private String budgetId;
    private BigDecimal totalBudget;
    private LocalDate startDate;
    private LocalDate endDate;
    private String currency;

    public BudgetInputDS(String budgetId,
                         BigDecimal totalBudget,
                         LocalDate startDate,
                         LocalDate endDate,
                         String currency)
    {
        this.budgetId = budgetId;
        this.totalBudget = totalBudget;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currency = currency;
    }

    public String getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(String budgetId) {
        this.budgetId = budgetId;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
