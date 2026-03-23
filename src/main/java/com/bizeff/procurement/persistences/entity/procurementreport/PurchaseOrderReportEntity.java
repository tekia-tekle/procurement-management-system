package com.bizeff.procurement.persistences.entity.procurementreport;

import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.persistences.entity.purchaserequisition.PurchaseRequisitionEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@Entity
@Table(name = "purchase_order_report")
public class PurchaseOrderReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long totalPurchaseOrders;
    private BigDecimal totalPurchaseOrderCosts;
    @ElementCollection
    @CollectionTable(name = "purchase_order_status_counts", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "status")
    @Column(name = "count")
    private Map<PurchaseOrderStatus, Integer> purchaseOrderStatusMap;
    @ElementCollection
    @CollectionTable(name = "purchase_order_supplier_counts", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "count")
    private Map<String, Integer> purchaseOrderSupplierMap;// count purchase orders that are with the same supplier.the map is supplier id and the value is the number of purchase order
    @ElementCollection
    @CollectionTable(name = "purchase_order_department_counts", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "department")
    @Column(name = "count")
    private Map<String, Integer> purchaseOrderDepartmentMap; // purchase orders with the same departments.
    @ElementCollection
    @CollectionTable(name = "purchase_order_spend_per_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "total_spend")
    private Map<String, BigDecimal> purchaseOrderTotalSpendPerSupplier;
    @ElementCollection
    @CollectionTable(name = "purchase_order_spend_per_department", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "department")
    @Column(name = "total_spend")
    private Map<String, BigDecimal> purchaseOrderTotalSpendPerDepartment;
    @ElementCollection
    @CollectionTable(name = "requisition_lists_per_purchase_order", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "purchase_order_id")
    @Column(name = "requisition_list")
    private List<PurchaseRequisitionEntity> requisitionListsPerPurchaseOrder; // Storing requisition IDs as strings for simplicity
    @OneToOne(mappedBy = "purchaseOrderReport",cascade = CascadeType.ALL)
    private ProcurementReportEntity procurementReportEntity;
    public PurchaseOrderReportEntity() {}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTotalPurchaseOrders() {
        return totalPurchaseOrders;
    }
    public void setTotalPurchaseOrders(Long totalPurchaseOrders) {
        this.totalPurchaseOrders = totalPurchaseOrders;
    }
    public BigDecimal getTotalPurchaseOrderCosts() {
        return totalPurchaseOrderCosts;
    }
    public void setTotalPurchaseOrderCosts(BigDecimal totalPurchaseOrderCosts) {
        this.totalPurchaseOrderCosts = totalPurchaseOrderCosts;
    }
    public Map<PurchaseOrderStatus, Integer> getPurchaseOrderStatusMap() {
        return purchaseOrderStatusMap;
    }
    public void setPurchaseOrderStatusMap(Map<PurchaseOrderStatus, Integer> purchaseOrderStatusMap) {
        this.purchaseOrderStatusMap = purchaseOrderStatusMap;
    }
    public Map<String, Integer> getPurchaseOrderSupplierMap() {
        return purchaseOrderSupplierMap;
    }
    public void setPurchaseOrderSupplierMap(Map<String, Integer> purchaseOrderSupplierMap) {
        this.purchaseOrderSupplierMap = purchaseOrderSupplierMap;
    }
    public Map<String, Integer> getPurchaseOrderDepartmentMap() {
        return purchaseOrderDepartmentMap;
    }
    public void setPurchaseOrderDepartmentMap(Map<String, Integer> purchaseOrderDepartmentMap) {
        this.purchaseOrderDepartmentMap = purchaseOrderDepartmentMap;
    }
    public Map<String, BigDecimal> getPurchaseOrderTotalSpendPerSupplier() {
        return purchaseOrderTotalSpendPerSupplier;
    }
    public void setPurchaseOrderTotalSpendPerSupplier(Map<String, BigDecimal> purchaseOrderTotalSpendPerSupplier) {
        this.purchaseOrderTotalSpendPerSupplier = purchaseOrderTotalSpendPerSupplier;
    }
    public Map<String, BigDecimal> getPurchaseOrderTotalSpendPerDepartment() {
        return purchaseOrderTotalSpendPerDepartment;
    }
    public void setPurchaseOrderTotalSpendPerDepartment(Map<String, BigDecimal> purchaseOrderTotalSpendPerDepartment) {
        this.purchaseOrderTotalSpendPerDepartment = purchaseOrderTotalSpendPerDepartment;
    }
    public List<PurchaseRequisitionEntity> getRequisitionListsPerPurchaseOrder() {
        return requisitionListsPerPurchaseOrder;
    }
    public void setRequisitionListsPerPurchaseOrder(List<PurchaseRequisitionEntity> requisitionListsPerPurchaseOrder) {
        this.requisitionListsPerPurchaseOrder = requisitionListsPerPurchaseOrder;
    }

    public ProcurementReportEntity getProcurementReportEntity() {
        return procurementReportEntity;
    }

    public void setProcurementReportEntity(ProcurementReportEntity procurementReportEntity) {
        this.procurementReportEntity = procurementReportEntity;
    }
    @Override
    public String toString() {
        return "PurchaseOrderReportEntity{" +
                "id=" + id +
                ", totalPurchaseOrders=" + totalPurchaseOrders +
                ", totalPurchaseOrderCosts=" + totalPurchaseOrderCosts +
                ", purchaseOrderStatusMap=" + purchaseOrderStatusMap +
                ", purchaseOrderSupplierMap=" + purchaseOrderSupplierMap +
                ", purchaseOrderDepartmentMap=" + purchaseOrderDepartmentMap +
                ", purchaseOrderTotalSpendPerSupplier=" + purchaseOrderTotalSpendPerSupplier +
                ", purchaseOrderTotalSpendPerDepartment=" + purchaseOrderTotalSpendPerDepartment +
                ", requisitionListsPerPurchaseOrder=" + requisitionListsPerPurchaseOrder +
                '}';
    }
}