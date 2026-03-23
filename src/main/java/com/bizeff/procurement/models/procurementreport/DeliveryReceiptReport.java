package com.bizeff.procurement.models.procurementreport;

import com.bizeff.procurement.models.enums.DeliveryStatus;

import java.math.BigDecimal;
import java.util.Map;

public class DeliveryReceiptReport {
    private Long id;
    private Long totalDeliveryReceipts;
    private Long totalDeliveredItems;
    private BigDecimal totalCostForDeliveredItem;
    private Map<DeliveryStatus, Integer> deliveryReceiptsByStatus;
    private Map<String, BigDecimal> totalCostsForDeliveredItemPerSupplier;
    private Map<String, Integer> deliveryReceiptsFromSupplier;
    public DeliveryReceiptReport(){}

    public DeliveryReceiptReport(Long totalDeliveryReceipts,
                                 Long totalDeliveredItems,
                                 BigDecimal totalCostForDeliveredItem,
                                 Map<DeliveryStatus, Integer> deliveryReceiptsByStatus,
                                 Map<String, BigDecimal> totalCostsForDeliveredItemPerSupplier,
                                 Map<String, Integer> deliveryReceiptsFromSupplier)
    {
        this.totalDeliveryReceipts = totalDeliveryReceipts;
        this.totalDeliveredItems = totalDeliveredItems;
        this.totalCostForDeliveredItem = totalCostForDeliveredItem;
        this.deliveryReceiptsByStatus = deliveryReceiptsByStatus;
        this.totalCostsForDeliveredItemPerSupplier = totalCostsForDeliveredItemPerSupplier;
        this.deliveryReceiptsFromSupplier = deliveryReceiptsFromSupplier;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTotalDeliveryReceipts() {
        return totalDeliveryReceipts;
    }
    public void setTotalDeliveryReceipts(Long totalDeliveryReceipts) {
        this.totalDeliveryReceipts = totalDeliveryReceipts;
    }
    public Long getTotalDeliveredItems() {
        return totalDeliveredItems;
    }
    public void setTotalDeliveredItems(Long totalDeliveredItems) {
        this.totalDeliveredItems = totalDeliveredItems;
    }
    public BigDecimal getTotalCostForDeliveredItem() {
        return totalCostForDeliveredItem;
    }
    public void setTotalCostForDeliveredItem(BigDecimal totalCostForDeliveredItem) {
        this.totalCostForDeliveredItem = totalCostForDeliveredItem;
    }
    public Map<DeliveryStatus, Integer> getDeliveryReceiptsByStatus() {
        return deliveryReceiptsByStatus;
    }
    public void setDeliveryReceiptsByStatus(Map<DeliveryStatus, Integer> deliveryReceiptsByStatus) {
        this.deliveryReceiptsByStatus = deliveryReceiptsByStatus;
    }
    public Map<String, BigDecimal> getTotalCostsForDeliveredItemPerSupplier() {
        return totalCostsForDeliveredItemPerSupplier;
    }
    public void setTotalCostsForDeliveredItemPerSupplier(Map<String, BigDecimal> totalCostsForDeliveredItemPerSupplier) {
        this.totalCostsForDeliveredItemPerSupplier = totalCostsForDeliveredItemPerSupplier;
    }
    public Map<String, Integer> getDeliveryReceiptsFromSupplier() {
        return deliveryReceiptsFromSupplier;
    }
    public void setDeliveryReceiptsFromSupplier(Map<String, Integer> deliveryReceiptsFromSupplier) {
        this.deliveryReceiptsFromSupplier = deliveryReceiptsFromSupplier;
    }
}
