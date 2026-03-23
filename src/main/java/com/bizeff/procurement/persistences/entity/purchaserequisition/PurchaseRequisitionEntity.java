package com.bizeff.procurement.persistences.entity.purchaserequisition;

import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.SupplierEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_requisition")
public class PurchaseRequisitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "requisition_id", nullable = false, unique = true)
    private String requisitionId;

    @Column(name = "requisition_number", nullable = false,unique = true)
    private String requisitionNumber;

    @Column(name = "requisition_date", nullable = false)
    private LocalDate requisitionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by_user_id",nullable = false)
    private UserEntity requestedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",nullable = false)
    private DepartmentEntity departmentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_center_id")
    private CostCenterEntity costCenterEntity;
    @OneToMany(mappedBy = "purchaseRequisition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestedItemEntity> requestedItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "priority_level", nullable = false)
    private PriorityLevel priorityLevel;

    @Column(name = "expected_delivery_date",nullable = false)
    private LocalDate expectedDeliveryDate;

    @Column(name = "justification", columnDefinition = "TEXT")
    private String justification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplierEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private PurchaseOrderEntity purchaseOrderEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "requisition_status", nullable = false)
    private RequisitionStatus requisitionStatus = RequisitionStatus.PENDING;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    // Constructors
    public PurchaseRequisitionEntity() {}


    // Business methods
    @Transient
    public BigDecimal getTotalEstimatedCost() {
        return requestedItems.stream()
                .map(RequestedItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Helper method for items
    public void addItem(RequestedItemEntity item) {
        requestedItems.add(item);
        item.setPurchaseRequisition(this);
    }
    /** Getters and Setters.*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserEntity getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(UserEntity requestedBy) {
        this.requestedBy = requestedBy;
    }

    public DepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(DepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    public CostCenterEntity getCostCenterEntity() {
        return costCenterEntity;
    }

    public void setCostCenterEntity(CostCenterEntity costCenterEntity) {
        this.costCenterEntity = costCenterEntity;
    }

    public List<RequestedItemEntity> getItems() {
        return requestedItems;
    }

    public void setItems(List<RequestedItemEntity> items) {
        this.requestedItems.clear();
        if (items != null) {
            this.requestedItems.addAll(items);
        }
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

    public SupplierEntity getSupplierEntity() {
        return supplierEntity;
    }

    public void setSupplierEntity(SupplierEntity supplierEntity) {
        this.supplierEntity = supplierEntity;
    }

    public PurchaseOrderEntity getPurchaseOrderEntity() {
        return purchaseOrderEntity;
    }

    public void setPurchaseOrderEntity(PurchaseOrderEntity purchaseOrderEntity) {
        this.purchaseOrderEntity = purchaseOrderEntity;
    }

    public RequisitionStatus getRequisitionStatus() {
        return requisitionStatus;
    }

    public void setRequisitionStatus(RequisitionStatus requisitionStatus) {
        this.requisitionStatus = requisitionStatus;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }
    @PrePersist
    public void validateDate(){
        if (requisitionDate.isAfter(expectedDeliveryDate)){
            throw new IllegalArgumentException("delivery date can't be before requisition date.");
        }
    }
    @Override
    public String toString() {
        return "PurchaseRequisitionEntity{" +
                "requisitionId='" + requisitionId + '\'' +
                ", requisitionNumber='" + requisitionNumber + '\'' +
                ", requisitionDate=" + requisitionDate +
                ", priorityLevel=" + priorityLevel +
                ", expectedDeliveryDate=" + expectedDeliveryDate +
                ", justification='" + justification + '\'' +
                ", requisitionStatus=" + requisitionStatus +
                ", updatedDate=" + updatedDate +
                ", requestedBy=" + (requestedBy != null ? requestedBy.getUserId() : null) +
                ", department=" + (departmentEntity != null ? departmentEntity.getDepartmentId() : null) +
                ", costCenter=" + (costCenterEntity != null ? costCenterEntity.getCostCenterId() : null) +
                ", supplier=" + (supplierEntity != null ? supplierEntity.getSupplierId() : null) +
                ", items.size=" + (requestedItems != null ? requestedItems.size() : 0) +
                '}';
    }
}
