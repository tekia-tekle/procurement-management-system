package com.bizeff.procurement.usecases.purchaseorder;

import com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder.ApprovePurchaseOrderInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.ApprovePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.ApprovePurchaseOrderOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.ApprovePurchaseOrderOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.budgetandcostcontrol.BudgetRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.DepartmentRepository;
import com.bizeff.procurement.models.budget.Budget;
import com.bizeff.procurement.models.budget.BudgetStatus;
import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.services.purchaseorder.PurchaseOrderTrackingStatusService;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.*;
public class ApprovePurchaseOrderUseCase implements ApprovePurchaseOrderInputBoundary {
    private PurchaseOrderRepository purchaseOrderRepository;
    private DepartmentRepository departmentRepository;
    private BudgetRepository budgetRepository;
    private PurchaseOrderTrackingStatusService purchaseOrderTrackingStatusService;
    private ApprovePurchaseOrderOutputBoundary approvePurchaseOrderOutputBoundary;
    public ApprovePurchaseOrderUseCase(PurchaseOrderRepository purchaseOrderRepository,
                                       DepartmentRepository departmentRepository,
                                       BudgetRepository budgetRepository,
                                       PurchaseOrderTrackingStatusService purchaseOrderTrackingStatusService,
                                       ApprovePurchaseOrderOutputBoundary approvePurchaseOrderOutputBoundary)
    {

        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderTrackingStatusService = purchaseOrderTrackingStatusService;
        this.departmentRepository = departmentRepository;
        this.budgetRepository= budgetRepository;
        this.approvePurchaseOrderOutputBoundary = approvePurchaseOrderOutputBoundary;
    }

    @Override
    public ApprovePurchaseOrderOutputDS trackPurchaseOrderStatus(ApprovePurchaseOrderInputDS input) {

        validateInput(input);
        String managerName = input.getManagerDetails().getName();

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByOrderId(input.getOrderId()).orElseThrow(()->new IllegalArgumentException("purchase order doesn't exist."));

        purchaseOrderTrackingStatusService.getAllPurchaseOrders().put(purchaseOrder.getOrderId(),purchaseOrder);

        String departmentId = purchaseOrder.getDepartment().getDepartmentId();

        Department department = departmentRepository.findByDepartmentId(departmentId).orElseThrow(()->new IllegalArgumentException("Department not Found."));

        purchaseOrderTrackingStatusService.getDepartments().add(department);

        Budget budget = department.getBudget();

        budget = budgetRepository.findByBudgetId(budget.getBudgetId()).orElseThrow(()-> new IllegalArgumentException("Budget Doesn't Found In the repository."));

        if (!budget.getBudgetStatus().equals(BudgetStatus.ACTIVE)){
            throw new IllegalArgumentException("the budget in the repository is in active status.");
        }

        if (input.getApprovalDate().isBefore(purchaseOrder.getOrderDate())) {
            throw new IllegalArgumentException("Approval date can't be before order date.");
        }

        if (input.getApprovalDate().isAfter(purchaseOrder.getDeliveryDate())){
            throw new IllegalArgumentException("approval date can't be after delivery date.");
        }

        purchaseOrder = purchaseOrderTrackingStatusService.trackPurchaseOrderStatus(purchaseOrder.getOrderId());

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.update(purchaseOrder);
        ApprovePurchaseOrderOutputDS output;

        if (savedPurchaseOrder.getPurchaseOrderStatus().equals(PurchaseOrderStatus.APPROVED)){
            output = new ApprovePurchaseOrderOutputDS(savedPurchaseOrder.getOrderId(),true, input.getApprovalDate(), managerName);
        }
        else {
            output = new ApprovePurchaseOrderOutputDS(savedPurchaseOrder.getOrderId(), false, input.getApprovalDate(), managerName);
        }

        approvePurchaseOrderOutputBoundary.presentPurchaseOrderWithStatus(output);

        return output;

    }
    private void validateInput(ApprovePurchaseOrderInputDS input) {
        if (input == null) {
            throw new NullPointerException("Input data is required.");
        }

        validateString(input.getManagerDetails().getName(), "Manager name");
        validateString(input.getManagerDetails().getEmailAddress(), "Manager email");
        validateString(input.getManagerDetails().getDepartmentId(), "Department ID");
        validateString(input.getManagerDetails().getPhoneNumber(), "Phone number");
        validateString(input.getManagerDetails().getRole(), "Role");
        validateString(input.getOrderId(),"Order Id");
        validateDate(input.getApprovalDate(),"Approval Date");
    }
}
