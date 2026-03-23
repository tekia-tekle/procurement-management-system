package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.budgetandcostcontrol;

import com.bizeff.procurement.models.budget.Budget;
import com.bizeff.procurement.models.budget.BudgetStatus;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository {
    Budget save(Budget budget);

    Budget update(Budget budget);

    List<Budget> findAll();
    Optional<Budget>findByBudgetId(String budgetId);
    void deleteByBudgetId(String budgetId);
    List<Budget> findByStatus(BudgetStatus status);
    void deleteAll();
}
