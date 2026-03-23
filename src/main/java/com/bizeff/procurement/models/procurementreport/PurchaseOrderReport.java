package com.bizeff.procurement.models.procurementreport;

import com.bizeff.procurement.models.enums.PurchaseOrderStatus;

import java.math.BigDecimal;
import java.util.Map;

public class PurchaseOrderReport {
    private Long id;
    private Long totalPurchaseOrders;
    private BigDecimal totalPurchaseOrderCosts;
    private Map<PurchaseOrderStatus, Integer> purchaseOrderStatusMap;
    private Map<String, Integer> purchaseOrdersByDepartment;
    private Map<String, BigDecimal> purchaseOrderTotalSpendPerSupplier;
    private Map<String, Integer> purchaseOrdersForSupplier;
    private Map<String, BigDecimal> purchaseOrderTotalSpendPerDepartment;
    public PurchaseOrderReport(){}

    public PurchaseOrderReport(Long totalPurchaseOrders,
                               BigDecimal totalPurchaseOrderCosts,
                               Map<PurchaseOrderStatus, Integer> purchaseOrderStatusMap,
                               Map<String, Integer> purchaseOrdersByDepartment,
                               Map<String, BigDecimal> purchaseOrderTotalSpendPerSupplier,
                               Map<String, Integer> purchaseOrdersForSupplier,
                               Map<String, BigDecimal> purchaseOrderTotalSpendPerDepartment)
    {
        this.totalPurchaseOrders = totalPurchaseOrders;
        this.totalPurchaseOrderCosts = totalPurchaseOrderCosts;
        this.purchaseOrderStatusMap = purchaseOrderStatusMap;
        this.purchaseOrdersByDepartment = purchaseOrdersByDepartment;
        this.purchaseOrderTotalSpendPerSupplier = purchaseOrderTotalSpendPerSupplier;
        this.purchaseOrdersForSupplier = purchaseOrdersForSupplier;
        this.purchaseOrderTotalSpendPerDepartment = purchaseOrderTotalSpendPerDepartment;
    }

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

    public Map<String, Integer> getPurchaseOrdersByDepartment() {
        return purchaseOrdersByDepartment;
    }

    public void setPurchaseOrdersByDepartment(Map<String, Integer> purchaseOrdersByDepartment) {
        this.purchaseOrdersByDepartment = purchaseOrdersByDepartment;
    }

    public Map<String, BigDecimal> getPurchaseOrderTotalSpendPerSupplier() {
        return purchaseOrderTotalSpendPerSupplier;
    }

    public void setPurchaseOrderTotalSpendPerSupplier(Map<String, BigDecimal> purchaseOrderTotalSpendPerSupplier) {
        this.purchaseOrderTotalSpendPerSupplier = purchaseOrderTotalSpendPerSupplier;
    }

    public Map<String, Integer> getPurchaseOrdersForSupplier() {
        return purchaseOrdersForSupplier;
    }

    public void setPurchaseOrdersForSupplier(Map<String, Integer> purchaseOrdersForSupplier) {
        this.purchaseOrdersForSupplier = purchaseOrdersForSupplier;
    }

    public Map<String, BigDecimal> getPurchaseOrderTotalSpendPerDepartment() {
        return purchaseOrderTotalSpendPerDepartment;
    }

    public void setPurchaseOrderTotalSpendPerDepartment(Map<String, BigDecimal> purchaseOrderTotalSpendPerDepartment) {
        this.purchaseOrderTotalSpendPerDepartment = purchaseOrderTotalSpendPerDepartment;
    }

}
