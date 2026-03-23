package com.bizeff.procurement.services.purchaseorder;


import com.bizeff.procurement.models.budget.Budget;
import com.bizeff.procurement.models.budget.BudgetStatus;
import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.Department;

import java.math.BigDecimal;
import java.util.*;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateString;

public class PurchaseOrderTrackingStatusService {
    private Map<String, PurchaseOrder> purchaseOrders;
    private List<Department> departments;
    public PurchaseOrderTrackingStatusService(){
        this.purchaseOrders = new HashMap<>();
        this.departments = new ArrayList<>();
    }
    public PurchaseOrder trackPurchaseOrderStatus(String orderId){
        validateString(orderId, "Order ID");
        PurchaseOrder purchaseOrder = getPurchaseOrderById(orderId);
        Department department = departments.stream()
                .filter(department1 -> department1.getDepartmentId().equals(purchaseOrder.getDepartment().getDepartmentId()))
                .findFirst()
                .orElseThrow(()->new NullPointerException("there is no department in the department list which is matched with the purchase order."));

        Budget budget = department.getBudget();
        if (budget == null || budget.getBudgetStatus().equals(BudgetStatus.INACTIVE)){
            throw new IllegalArgumentException("budget can't null or InActive.");
        }
        BigDecimal totalCost = purchaseOrder.getTotalCost();
        BigDecimal totalBudget = budget.getTotalBudget();
        if (totalCost.compareTo(totalBudget) < 0){

            if (purchaseOrder.getPurchaseOrderStatus().equals(PurchaseOrderStatus.PENDING)){
                purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
                purchaseOrder.setApproved(true);
            }
            else if (purchaseOrder.getPurchaseOrderStatus().equals(PurchaseOrderStatus.APPROVED)){
                purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.SHIPPED);
                purchaseOrder.setShipped(true);
            }
            else if (purchaseOrder.getPurchaseOrderStatus().equals(PurchaseOrderStatus.SHIPPED)){
                purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.COMPLETED);
            }
        }
        else {
            purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.TERMINATED);
        }
        return purchaseOrder;
    }

    // Method to get a purchase order by ID
    public PurchaseOrder getPurchaseOrderById(String orderId) {
        validateString(orderId, "Order ID");
        return Optional.ofNullable(purchaseOrders.get(orderId)).orElseThrow(()-> new NoSuchElementException("there is no purchase order with the order id "+ orderId));

    }


    /**getting departments*/
    public List<Department> getDepartments() {
        return departments;
    }

    // Method to get all purchase orders (for reporting)
    public Map<String, PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrders;
    }
}