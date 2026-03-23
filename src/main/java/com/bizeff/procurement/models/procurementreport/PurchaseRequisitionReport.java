package com.bizeff.procurement.models.procurementreport;

import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.enums.RequisitionStatus;

import java.math.BigDecimal;
import java.util.Map;

public class PurchaseRequisitionReport {
    private Long id;
    private Long totalRequisitions;
    private Long totalRequestedItems;
    private BigDecimal totalSpendingAmount;
    private Map<PriorityLevel, Integer> requisitionsByPriority;
    private Map<String, Integer> requisitionsByDepartment;
    private Map<String,BigDecimal>totalSpendingPerDepartment;
    private Map<RequisitionStatus, Integer> requisitionStatusMap;

    public PurchaseRequisitionReport(){}
    public PurchaseRequisitionReport(Long totalRequisitions,
                                     Long totalRequestedItems,
                                     BigDecimal totalSpendingAmount,
                                     Map<PriorityLevel, Integer> requisitionsByPriority,
                                     Map<String, Integer> requisitionsByDepartment,
                                     Map<RequisitionStatus, Integer> requisitionStatusMap)
    {
        this.totalRequisitions = totalRequisitions;
        this.totalRequestedItems = totalRequestedItems;
        this.totalSpendingAmount = totalSpendingAmount;
        this.requisitionsByPriority = requisitionsByPriority;
        this.requisitionsByDepartment = requisitionsByDepartment;
        this.requisitionStatusMap = requisitionStatusMap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalRequisitions() {
        return totalRequisitions;
    }

    public void setTotalRequisitions(Long totalRequisitions) {
        this.totalRequisitions = totalRequisitions;
    }

    public Long getTotalRequestedItems() {
        return totalRequestedItems;
    }

    public void setTotalRequestedItems(Long totalRequestedItems) {
        this.totalRequestedItems = totalRequestedItems;
    }

    public BigDecimal getTotalSpendingAmount() {
        return totalSpendingAmount;
    }

    public void setTotalSpendingAmount(BigDecimal totalSpendingAmount) {
        this.totalSpendingAmount = totalSpendingAmount;
    }

    public Map<PriorityLevel, Integer> getRequisitionsByPriority() {
        return requisitionsByPriority;
    }

    public void setRequisitionsByPriority(Map<PriorityLevel, Integer> requisitionsByPriority) {
        this.requisitionsByPriority = requisitionsByPriority;
    }

    public Map<String, Integer> getRequisitionsByDepartment() {
        return requisitionsByDepartment;
    }

    public void setRequisitionsByDepartment(Map<String, Integer> requisitionsByDepartment) {
        this.requisitionsByDepartment = requisitionsByDepartment;
    }

    public Map<String, BigDecimal> getTotalSpendingPerDepartment() {
        return totalSpendingPerDepartment;
    }

    public void setTotalSpendingPerDepartment(Map<String, BigDecimal> totalSpendingPerDepartment) {
        this.totalSpendingPerDepartment = totalSpendingPerDepartment;
    }

    public Map<RequisitionStatus, Integer> getRequisitionStatusMap() {
        return requisitionStatusMap;
    }

    public void setRequisitionStatusMap(Map<RequisitionStatus, Integer> requisitionStatusMap) {
        this.requisitionStatusMap = requisitionStatusMap;
    }

    //toString
    @Override
    public String toString() {
        return "PurchaseRequisitionReport [id=" + id +
                ", totalRequisitions=" + totalRequisitions +
                ", totalRequestedItems=" + totalRequestedItems +
                ", totalSpendingAmount=" + totalSpendingAmount +
                ", requisitionsByPriority=" + requisitionsByPriority +
                ", requisitionsByDepartment=" + requisitionsByDepartment +
                ", totalSpendingPerDepartment=" + totalSpendingPerDepartment +
                ", requisitionStatusMap=" + requisitionStatusMap +
                "]";

    }
}
