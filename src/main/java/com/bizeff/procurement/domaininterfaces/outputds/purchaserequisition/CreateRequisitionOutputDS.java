package com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition;

import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.models.purchaserequisition.RequestedItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreateRequisitionOutputDS {
    private String requisitionId;
    private String requisitionNumber;
    private LocalDate requisitionDate;
    private List<RequestedItem> requestedItems;
    private String requestedBy;//user or requester name.
    private String departmentId;// department.
    private String costCenterId;
    private BigDecimal totalEstimatedCosts;
    private LocalDate expectedDeliveryDate;
    private RequisitionStatus status;
    private String requestedto; // supplierName

    public CreateRequisitionOutputDS(String requisitionId,
                                     String requisitionNumber,
                                     LocalDate requisitionDate,
                                     List<RequestedItem> requestedItems,
                                     String requestedBy,
                                     String departmentId,
                                     String costCenterId,
                                     BigDecimal totalEstimatedCosts,
                                     LocalDate expectedDeliveryDate,
                                     String requestedto,
                                     RequisitionStatus status)
    {
        this.requisitionId = requisitionId;
        this.requisitionNumber = requisitionNumber;
        this.requisitionDate = requisitionDate;
        this.requestedItems = requestedItems;
        this.requestedBy = requestedBy;
        this.departmentId = departmentId;
        this.costCenterId = costCenterId;
        this.totalEstimatedCosts = totalEstimatedCosts;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.requestedto = requestedto;
        this.status = status;
    }

    public String getRequisitionId() {
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

    public List<RequestedItem> getRequestedItems() {
        return requestedItems;
    }

    public void setRequestedItems(List<RequestedItem> requestedItems) {
        this.requestedItems = requestedItems;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenter(String costCenterId) {
        this.costCenterId = costCenterId;
    }

    public BigDecimal getTotalEstimatedCosts() {
        return totalEstimatedCosts;
    }

    public void setTotalEstimatedCosts(BigDecimal totalEstimatedCosts) {
        this.totalEstimatedCosts = totalEstimatedCosts;
    }

    public RequisitionStatus getStatus() {
        return status;
    }

    public void setStatus(RequisitionStatus status) {
        this.status = status;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getRequestedto() {
        return requestedto;
    }

    public void setRequestedto(String requestedto) {
        this.requestedto = requestedto;
    }
}
