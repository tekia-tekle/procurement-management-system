package com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition;

import com.bizeff.procurement.models.enums.PriorityLevel;
import java.time.LocalDate;
import java.util.List;

public class CreateRequisitionInputDS {
    private RequisitionContactDetailsInputDS requester;
    private String  departmentId;
    private String costCenterId;
    private String requisitionNumber;
    private LocalDate requisitionDate;
    private List<ItemsInputDS> items;
    private PriorityLevel priorityLevel;
    private LocalDate expectedDeliveryDate;
    private String justification;
    private String supplierId;
    public CreateRequisitionInputDS(){}

    // Constructor with all fields
    public CreateRequisitionInputDS(
            RequisitionContactDetailsInputDS requester,
            String departmentId,
            String costCenterId,
            String requisitionNumber,
            LocalDate requisitionDate,
            List<ItemsInputDS> items,
            PriorityLevel priorityLevel,
            LocalDate expectedDeliveryDate,
            String justification,
            String supplierId) {
        this.requester = requester;
        this.departmentId = departmentId;
        this.costCenterId = costCenterId;
        this.requisitionNumber = requisitionNumber;
        this.requisitionDate = requisitionDate;
        this.items = items;
        this.priorityLevel = priorityLevel;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.justification = justification;
        this.supplierId = supplierId;
    }

    // Getters and setters

    public RequisitionContactDetailsInputDS getRequester() {
        return requester;
    }

    public void setRequester(RequisitionContactDetailsInputDS requester) {
        this.requester = requester;
    }

    public String  getDepartment() {
        return departmentId;
    }

    public void setDepartment(String  departmentId) {
        this.departmentId = departmentId;
    }

    public String getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(String costCenterId) {
        this.costCenterId = costCenterId;
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

    public List<ItemsInputDS> getItems() {
        return items;
    }

    public void setItems(List<ItemsInputDS> items) {
        this.items = items;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
