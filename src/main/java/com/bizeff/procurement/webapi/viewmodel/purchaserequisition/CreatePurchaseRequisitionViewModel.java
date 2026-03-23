package com.bizeff.procurement.webapi.viewmodel.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.CreateRequisitionOutputDS;

import java.util.List;

public class CreatePurchaseRequisitionViewModel {
    private String requisitionId;
    private String requisitionNumber;
    private String requisitionDate;
    private List<RequestedItemViewModel> requestedItems;
    private String requestedBy; // requester
    private String departmentId;
    private String costCenterId;
    private String totalEstimatedCosts;
    private String expectedDeliveryDate;
    private String status;
    private String requestedto; // supplier
    public CreatePurchaseRequisitionViewModel(){};

    public CreatePurchaseRequisitionViewModel(String requisitionId,
                                              String requisitionNumber,
                                              String requisitionDate,
                                              List<RequestedItemViewModel> requestedItems,
                                              String requestedBy,
                                              String departmentId,
                                              String costCenterId,
                                              String totalEstimatedCosts,
                                              String  expectedDeliveryDate,
                                              String requestedto,
                                              String status)
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

    private CreateRequisitionOutputDS requisitionRequesterOutput;


    public CreatePurchaseRequisitionViewModel(CreateRequisitionOutputDS requisitionRequesterOutput){
        this.requisitionRequesterOutput = requisitionRequesterOutput;
    }

    public CreateRequisitionOutputDS getRequisitionRequesterOutput() {
        return requisitionRequesterOutput;
    }

    public void setRequisitionRequesterOutput(CreateRequisitionOutputDS requisitionRequesterOutput) {
        this.requisitionRequesterOutput = requisitionRequesterOutput;
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

    public String getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public List<RequestedItemViewModel> getRequestedItems() {
        return requestedItems;
    }

    public void setRequestedItems(List<RequestedItemViewModel> requestedItems) {
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

    public void setCostCenterId(String costCenterId) {
        this.costCenterId = costCenterId;
    }

    public String getTotalEstimatedCosts() {
        return totalEstimatedCosts;
    }

    public void setTotalEstimatedCosts(String totalEstimatedCosts) {
        this.totalEstimatedCosts = totalEstimatedCosts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getRequestedto() {
        return requestedto;
    }

    public void setRequestedto(String requestedto) {
        this.requestedto = requestedto;
    }

    @Override
    public String toString() {
        return "CreatePurchaseRequisitionViewModel{" +
                "requisitionRequesterOutput=" + requisitionRequesterOutput +
                '}';
    }

}
