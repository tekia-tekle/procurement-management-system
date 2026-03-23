package com.bizeff.procurement.persistences.entity.procurementreport;

import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "purchase_requisition_reports")
public class PurchaseRequisitionReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long totalRequisitions;
    private Long totalRequestedItems;
    @Column(precision = 19, scale = 2)
    private BigDecimal totalSpendingAmount;
    @ElementCollection
    @CollectionTable(name = "requisition_by_priority", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyEnumerated(EnumType.STRING) // store enum name
    @MapKeyColumn(name = "priority")
    @Column(name = "count")
    private Map<PriorityLevel, Integer> requisitionsByPriority = new HashMap<>();
    @ElementCollection
    @CollectionTable(name = "requisition_by_department", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "department_name")
    @Column(name = "count")
    private Map<String, Integer> requisitionsByDepartment = new HashMap<>();
    @ElementCollection
    @CollectionTable(name = "spending_per_department", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "department_name")
    @Column(name = "spending_amount", precision = 19, scale = 2)
    private Map<String, BigDecimal> totalSpendingPerDepartment = new HashMap<>();
    @ElementCollection
    @CollectionTable(name = "requisition_status_map", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "status")
    @Column(name = "count")
    private Map<RequisitionStatus, Integer> requisitionStatusMap = new HashMap<>();
    @OneToOne(mappedBy = "requisitionReport",cascade = CascadeType.ALL)
    private ProcurementReportEntity procurementReportEntity;
    public PurchaseRequisitionReportEntity() {}
    public PurchaseRequisitionReportEntity(Long totalRequisitions,
                                           Long totalRequestedItems,
                                           BigDecimal totalSpendingAmount) {
        this.totalRequisitions = totalRequisitions;
        this.totalRequestedItems = totalRequestedItems;
        this.totalSpendingAmount = totalSpendingAmount;
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
    public ProcurementReportEntity getProcurementReportEntity() {
        return procurementReportEntity;
    }
    public void setProcurementReportEntity(ProcurementReportEntity procurementReportEntity) {
        this.procurementReportEntity = procurementReportEntity;
    }
}