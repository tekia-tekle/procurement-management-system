package com.bizeff.procurement.persistences.jparepository.purchaserequisition;

import com.bizeff.procurement.models.budget.BudgetStatus;
import com.bizeff.procurement.persistences.entity.budget.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface SpringDataBudgetRepository extends JpaRepository<BudgetEntity,Long> {
    Optional<BudgetEntity>findByBudgetId(String budgetId);
    void deleteByBudgetId(String budgetId);
    List<BudgetEntity> findByBudgetStatus(BudgetStatus status);
}
