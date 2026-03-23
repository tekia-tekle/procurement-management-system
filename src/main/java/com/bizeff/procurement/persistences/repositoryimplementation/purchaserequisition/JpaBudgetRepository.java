package com.bizeff.procurement.persistences.repositoryimplementation.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.budgetandcostcontrol.BudgetRepository;
import com.bizeff.procurement.models.budget.Budget;
import com.bizeff.procurement.models.budget.BudgetStatus;
import com.bizeff.procurement.persistences.entity.budget.BudgetEntity;
import com.bizeff.procurement.persistences.jparepository.purchaserequisition.SpringDataBudgetRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.purchaserequisition.BudgetMapper.*;

@Repository
public class JpaBudgetRepository implements BudgetRepository {
    private SpringDataBudgetRepository springDataBudgetRepository;

    public JpaBudgetRepository(SpringDataBudgetRepository springDataBudgetRepository){
        this.springDataBudgetRepository = springDataBudgetRepository;
    }
    @Override
    public Budget save(Budget budget) {
        BudgetEntity budgetEntity = toEntity(budget);
        BudgetEntity savedBudget = springDataBudgetRepository.save(budgetEntity);
        return toModel(savedBudget);
    }
    @Override
    public Optional<Budget> findByBudgetId(String budgetId) {
        return springDataBudgetRepository.findByBudgetId(budgetId).map(budgetEntity -> toModel(budgetEntity));
    }
    @Override
    public void deleteByBudgetId(String budgetId) {
        springDataBudgetRepository.deleteByBudgetId(budgetId);
    }

    @Override
    public List<Budget> findByStatus(BudgetStatus status) {
        return springDataBudgetRepository.findByBudgetStatus(status).stream().map(budgetEntity -> toModel(budgetEntity)).collect(Collectors.toList());
    }

    @Override
    public List<Budget> findAll() {
    return springDataBudgetRepository.findAll().stream().map(budgetEntity -> toModel(budgetEntity)).collect(Collectors.toList());
    }
    @Override
    public void deleteAll(){
        springDataBudgetRepository.deleteAll();
    }

    @Override
    public Budget update(Budget budget) {
        if (budget.getBudgetId() == null || budget.getBudgetId().isEmpty()) {
            throw new IllegalArgumentException("Budget ID must not be null or empty.");
        }
        Optional<BudgetEntity> existingBudgetOpt = springDataBudgetRepository.findByBudgetId(budget.getBudgetId());
        if (existingBudgetOpt.isEmpty()) {
            throw new IllegalArgumentException("Budget with ID " + budget.getBudgetId() + " does not exist.");
        }
        BudgetEntity existingBudget = existingBudgetOpt.get();
        // Update fields as necessary
        existingBudget.setTotalBudget(budget.getTotalBudget());
        existingBudget.setTotalUsedBudget(budget.getTotalBudget());
        existingBudget.setBudgetStatus(budget.getBudgetStatus());
        existingBudget.setTotalRemainingBudget(budget.getTotalRemainingBudget());
        existingBudget.setCurrency(budget.getCurrency());
        existingBudget.setStartDate(budget.getStartDate());
        existingBudget.setEndDate(budget.getEndDate());
        BudgetEntity updatedBudget = springDataBudgetRepository.save(existingBudget);
        return toModel(updatedBudget);
    }
}
