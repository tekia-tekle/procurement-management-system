package com.bizeff.procurement.persistences.mapper.purchaserequisition;


import com.bizeff.procurement.models.budget.Budget;
import com.bizeff.procurement.persistences.entity.budget.BudgetEntity;

public class BudgetMapper {
    /** convert Domain model to Entity. */
    public static BudgetEntity toEntity(Budget model) {
        if (model == null) return null;

        BudgetEntity entity = new BudgetEntity();
        if (model.getId() != null){
            entity.setId(model.getId());
        }
        entity.setBudgetId(model.getBudgetId());
        entity.setTotalBudget(model.getTotalBudget());
        entity.setTotalUsedBudget(model.getTotalUsedBudget());
        entity.setTotalRemainingBudget(model.getTotalRemainingBudget());
        entity.setCurrency(model.getCurrency());
        entity.setStartDate(model.getStartDate());
        entity.setEndDate(model.getEndDate());
        entity.setBudgetStatus(model.getBudgetStatus());
        return entity;
    }

    /** Convert Entity to Domain **/
    public static Budget toModel(BudgetEntity entity) {
        if (entity == null) return null;

        Budget model = new Budget();
        model.setId(entity.getId());
        model.setBudgetId(entity.getBudgetId());
        model.setTotalBudget(entity.getTotalBudget());
        model.setTotalUsedBudget(entity.getTotalUsedBudget());
        model.setTotalRemainingBudget(entity.getTotalRemainingBudget());
        model.setCurrency(entity.getCurrency());
        model.setStartDate(entity.getStartDate());
        model.setEndDate(entity.getEndDate());
        model.setBudgetStatus(entity.getBudgetStatus());
        return model;
    }
}
