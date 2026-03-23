package com.bizeff.procurement.persistences.entity.budget;

import com.bizeff.procurement.models.budget.BudgetStatus;
import com.bizeff.procurement.persistences.entity.purchaserequisition.DepartmentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "budget")
public class BudgetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "budget_id", nullable = false,unique = true)
    @NotNull
    @NotBlank
    private String budgetId;

    @PositiveOrZero
    @Column(name = "total_budget", nullable = false, precision = 19)
    private BigDecimal totalBudget = BigDecimal.ZERO;

    @PositiveOrZero
    @Column(name = "total_used_budget", nullable = false, precision = 19)
    private BigDecimal totalUsedBudget = BigDecimal.ZERO;

    @Column(name = "total_remaining_budget",nullable = false, precision = 19)
    private BigDecimal totalRemainingBudget;

    @Column(name = "currency", nullable = false, length = 10)
    @NotNull
    @NotBlank
    private String currency;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "budget_status", nullable = false)
    private BudgetStatus budgetStatus;
    @OneToOne(mappedBy = "budget", cascade = CascadeType.ALL,optional = true)
    private DepartmentEntity department;

    /** Default constructor for JPA */
    public BudgetEntity() {}


    /** Getters & Setters **/
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getTotalUsedBudget() {
        return totalUsedBudget;
    }

    public void setTotalUsedBudget(BigDecimal totalUsedBudget) {
        this.totalUsedBudget = totalUsedBudget;
    }

    public BigDecimal getTotalRemainingBudget() {
        return totalRemainingBudget;
    }

    public void setTotalRemainingBudget(BigDecimal totalRemainingBudget) {
        this.totalRemainingBudget = totalRemainingBudget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public BudgetStatus getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(BudgetStatus budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }
    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null) {
            return true; // Let @NotNull handle null cases
        }
        return endDate.isAfter(startDate);
    }

    @Override
    public String toString() {
        return "BudgetEntity{" +
                "id=" + id +
                ", budgetId='" + budgetId + '\'' +
                ", totalBudget=" + totalBudget +
                ", totalUsedBudget=" + totalUsedBudget +
                ", totalRemainingBudget=" + totalRemainingBudget +
                ", currency='" + currency + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", budgetStatus=" + budgetStatus +
                ", department=" + department +
                '}';
    }
}

