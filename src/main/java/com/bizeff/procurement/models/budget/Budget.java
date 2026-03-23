package com.bizeff.procurement.models.budget;

import com.bizeff.procurement.models.IdGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Budget {
    private Long id;
    private String budgetId;
    private BigDecimal totalBudget;//total Budget.
    private BigDecimal totalUsedBudget = BigDecimal.ZERO;
    private BigDecimal totalRemainingBudget;
    private String currency;
    private LocalDate startDate;
    private LocalDate endDate;
    private BudgetStatus budgetStatus;


    /** Constructor */
    public Budget(){
        this.budgetStatus = BudgetStatus.INACTIVE;
    }
    public Budget(
                  BigDecimal totalBudget,
                  LocalDate startDate,
                  LocalDate endDate,
                   String currency)
    {

        this.budgetId = IdGenerator.generateId("BG");
        this.totalBudget = totalBudget;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currency = currency;
        this.budgetStatus = BudgetStatus.INACTIVE;
        this.totalRemainingBudget = totalBudget;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Getters and Setters **/

    public String getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(String budgetId) {
        this.budgetId = budgetId;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public BigDecimal getTotalUsedBudget() {
        return totalUsedBudget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BudgetStatus getBudgetStatus() {
        return budgetStatus;
    }

    public String getCurrency() {
        return currency;
    }

    // Update budget usage
    public void reduceTotalRemainingBudget(BigDecimal amount) {
        if (amount.compareTo(totalRemainingBudget) > 0) {
            throw new IllegalArgumentException("Insufficient total remaining budget.");
        }
        totalRemainingBudget = totalRemainingBudget.subtract(amount);
    }

    public BigDecimal getTotalRemainingBudget() {
        if (totalRemainingBudget == null){
            if (totalUsedBudget.compareTo(BigDecimal.ZERO) != 0.0){
                this.totalRemainingBudget = totalBudget.subtract(totalBudget);
            }
            else this.totalRemainingBudget = totalBudget;
        }
        return totalRemainingBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public void setTotalUsedBudget(BigDecimal totalUsedBudget) {
        this.totalUsedBudget = totalUsedBudget;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setBudgetStatus(BudgetStatus budgetStatus) {
        this.budgetStatus = budgetStatus;
    }
    public void updateBudgetStatus() {
        LocalDate today = LocalDate.now();

        if (today.isBefore(startDate) && totalRemainingBudget.compareTo(totalBudget) == 0) {
            budgetStatus = BudgetStatus.INACTIVE;
        } else if (!today.isBefore(startDate) && !today.isAfter(endDate)) {
            budgetStatus = BudgetStatus.ACTIVE;
        } else if (today.isAfter(endDate) && totalRemainingBudget.compareTo(BigDecimal.ZERO) == 0) {
            budgetStatus = BudgetStatus.EXPIRED;
        } else if (today.isAfter(endDate) && totalRemainingBudget.compareTo(BigDecimal.ZERO) > 0) {
            budgetStatus = BudgetStatus.CLOSED;
        }
    }
public void setTotalRemainingBudget(BigDecimal amount){
        this.totalRemainingBudget = amount;
}

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", budgetId='" + budgetId + '\'' +
                ", totalBudget=" + totalBudget +
                ", totalUsedBudget=" + totalUsedBudget +
                ", totalRemainingBudget=" + totalRemainingBudget +
                ", currency='" + currency + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", budgetStatus=" + budgetStatus +
                '}';
    }
}
