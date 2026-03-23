package com.bizeff.procurement.services.purchaserequisition;

import com.bizeff.procurement.models.budget.Budget;
import com.bizeff.procurement.models.budget.BudgetStatus;
import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.models.purchaserequisition.CostCenter;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.*;

public class PurchaseRequisitionTrackingStatusService {

    private Map<String, PurchaseRequisition> purchaseRequisitionMap;
    private List<Department> departments;

    public PurchaseRequisitionTrackingStatusService() {
        this.purchaseRequisitionMap = new HashMap<>();
        this.departments = new ArrayList<>();
    }

    public Department createDepartment(Department department){
        validateNotNull(department,"department");
        departments.add(department);
        return department;
    }

    public void addCostCenterToDepartment(String departmentId, CostCenter newCostCenter) {
        // Find the department
        Department department = getDepartmentById(departmentId);
        // Initialize costCenters list if it's null
        if (department.getCostCenters() == null) {
            department.setCostCenters(new ArrayList<>());
        }
        // Add the new cost center
        department.getCostCenters().add(newCostCenter);
    }

    public PurchaseRequisition trackRequisitionStatus(String requisitionId){
        PurchaseRequisition requisition = getRequisitionById(requisitionId);
        String departmentId = requisition.getDepartment().getDepartmentId();
        String costCenterId = requisition.getCostCenter().getCostCenterId();
        Department department = getDepartmentById(departmentId);

        CostCenter costCenter = department.getCostCenters().stream().filter(c->c.getCostCenterId().equals(costCenterId)).findFirst()
                .orElseThrow(()->new NullPointerException("there is no cost center found with costCenterId: "+ costCenterId));

        Budget budget = department.getBudget();
        if (!budget.getBudgetStatus().equals(BudgetStatus.ACTIVE)){
            throw new IllegalArgumentException("the budget is not active we can't do anything with not active budgets.");
        }
        BigDecimal totalCosts = requisition.getTotalEstimatedCosts();
        BigDecimal allocatedBudget = costCenter.getAllocatedBudget();
        if (totalCosts.compareTo(allocatedBudget) <= 0){
            requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
            costCenter.setUsedBudget(totalCosts);
            costCenter.setRemainingBudget(allocatedBudget.subtract(totalCosts));
            costCenter.setAllocatedBudget(costCenter.getRemainingBudget());
        }
        else {

            if (totalCosts.compareTo(allocatedBudget) > 0){
                BigDecimal discrepancyBudget = totalCosts.subtract(allocatedBudget);
                if (isHighPriority(requisition) && discrepancyBudget.compareTo(budget.getTotalRemainingBudget()) <= 0){
                    requisition.setRequisitionStatus(RequisitionStatus.APPROVED);// since it is high priority.
                    budget.setTotalUsedBudget(totalCosts);
                    budget.setTotalRemainingBudget(budget.getTotalRemainingBudget().subtract(discrepancyBudget));
                }
                else if (!isHighPriority(requisition) && discrepancyBudget.compareTo(budget.getTotalRemainingBudget()) < 0){
                    requisition.setRequisitionStatus(RequisitionStatus.PENDING);//since it is less probability.
                }
                else if (isHighPriority(requisition) && discrepancyBudget.compareTo(budget.getTotalRemainingBudget()) > 0){
                    requisition.setRequisitionStatus(RequisitionStatus.PENDING);
                }
                else if (!isHighPriority(requisition) && discrepancyBudget.compareTo(budget.getTotalRemainingBudget()) >= 0){
                    requisition.setRequisitionStatus(RequisitionStatus.REJECTED);
                }
            }
        }

        return requisition;
    }

    /** Retrieves a requisition by ID. */
    public PurchaseRequisition getRequisitionById(String requisitionId) {
        validateString(requisitionId,"Requisition Id");
        return Optional.ofNullable(purchaseRequisitionMap.get(requisitionId))
                .orElseThrow(() -> new IllegalArgumentException("No requisition found with ID: " + requisitionId));
    }

    /** Retrieves a department by its department id */
    public Department getDepartmentById(String departmentId){
        validateString(departmentId,"Department Id");
        return departments.stream()
                .filter(d -> d.getDepartmentId().equals(departmentId))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("No department found for departmentId: " + departmentId));

    }

    /** Checks if requisition is high priority */
    private boolean isHighPriority(PurchaseRequisition requisition) {
        return requisition.getPriorityLevel() == PriorityLevel.HIGH || requisition.getPriorityLevel() == PriorityLevel.CRITICAL;
    }

    /**Getting all Approved Requisitions. */
    public List<PurchaseRequisition> approvedRequisitions(){
        return purchaseRequisitionMap.values().stream().filter(requisition -> requisition.getRequisitionStatus().equals(RequisitionStatus.APPROVED)).collect(Collectors.toList());
    }

    /** Returns all purchase requisitions */
    public Map<String, PurchaseRequisition> getAllRequisitions() {
        return purchaseRequisitionMap;
    }

    public List<Department> getDepartments() {
        return departments;
    }
}
