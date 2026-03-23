package com.bizeff.procurement.models.purchaserequisition;

import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseRequisition {
    private Long id;
    private String requisitionId;
    private String requisitionNumber;
    private LocalDate requisitionDate;
    private User requestedBy;
    private Department department;
    private CostCenter costCenter;

    private List<RequestedItem> items = new ArrayList<>();

    private PriorityLevel priorityLevel;
    private LocalDate expectedDeliveryDate;

    private String justification;
    private Supplier supplier;

    private RequisitionStatus requisitionStatus;
    private LocalDate updatedDate;
    /** Constructor - Initializes a new purchase requisition */
    public PurchaseRequisition(){}
    public PurchaseRequisition(
            String requisitionNumber,
            LocalDate requisitionDate,
            User requestedBy,
            Department department,
            CostCenter costCenter,
            PriorityLevel priorityLevel,
            LocalDate expectedDeliveryDate,
            String justification
    ) {
        this.requisitionId = IdGenerator.generateId("REQ");
        this.requisitionNumber = requisitionNumber;
        this.requisitionDate = requisitionDate;
        this.requestedBy = requestedBy;
        this.department = department;
        this.costCenter = costCenter;
        this.items = new ArrayList<>();
        this.priorityLevel = priorityLevel;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.justification = justification;
        this.requisitionStatus = RequisitionStatus.PENDING;
        this.updatedDate = requisitionDate;
    }



    /** Getters and Setters */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String  getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(String requisitionId) {
        this.requisitionId = requisitionId;
    }

    public String getRequisitionNumber() {
        return requisitionNumber;
    }

    public void setRequisitionNumber(String requisitionNumber) {
        this.requisitionNumber = requisitionNumber;
    }

    public LocalDate getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(LocalDate requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public CostCenter getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(CostCenter costCenter) {
        this.costCenter = costCenter;
    }

    public List<RequestedItem> getItems() {
        return items;
    }

    public void setItems(List<RequestedItem> items) {
        this.items = items;
    }
    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    /** Calculates the total estimated cost of all items in the requisition */
    public void addItem(RequestedItem item) {
        items.add(item);
    }

    public BigDecimal getTotalEstimatedCosts(){
        return items.stream()
                .map(RequestedItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public RequisitionStatus getRequisitionStatus() {
        return requisitionStatus;
    }

    public void setRequisitionStatus(RequisitionStatus requisitionStatus) {
        this.requisitionStatus = requisitionStatus;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "PurchaseRequisition{" +
                "requisitionId='" + requisitionId + '\'' +
                ", requisitionNumber='" + requisitionNumber + '\'' +
                ", requisitionDate=" + requisitionDate +
                ", requestedBy=" + requestedBy +
                ", department=" + department +
                ", costCenter=" + costCenter +
                ", items=" + items +
                ", priorityLevel=" + priorityLevel +
                ", expectedDeliveryDate=" + expectedDeliveryDate +
                ", justification='" + justification + '\'' +
                ", supplier=" + supplier +
                ", requisitionStatus=" + requisitionStatus +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
