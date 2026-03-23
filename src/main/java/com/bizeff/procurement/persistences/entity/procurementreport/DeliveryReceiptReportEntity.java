package com.bizeff.procurement.persistences.entity.procurementreport;

import com.bizeff.procurement.models.enums.DeliveryStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "delivery_receipt_reports")
public class DeliveryReceiptReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalDeliveryReceipts;
    private Long totalDeliveredItems;
    private BigDecimal totalDeliveredAmount;

    @ElementCollection
    @CollectionTable(name = "delivery_receipts_by_status", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "status")
    @Column(name = "count")
    private Map<DeliveryStatus, Integer> deliveryReceiptsByStatus; // Using String for status for simplicity

    @ElementCollection
    @CollectionTable(name = "delivery_receipts_per_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "delivery_receipt_count")
    private Map<String, Integer> deliveryReceiptsPerSupplier;

    @ElementCollection
    @CollectionTable(name = "delivered_amount_per_supplier", joinColumns = @JoinColumn(name = "report_id"))
    @MapKeyColumn(name = "supplier")
    @Column(name = "total_delivered_amount")
    private Map<String, BigDecimal> totalCostsForDeliveredItemPerSupplier;


    @OneToOne(mappedBy = "deliveryReceiptReport",cascade = CascadeType.ALL)
    private ProcurementReportEntity procurementReportEntity;
    public DeliveryReceiptReportEntity() {}

    //getter and setters
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

    public BigDecimal getTotalDeliveredAmount() {
        return totalDeliveredAmount;
    }

    public void setTotalDeliveredAmount(BigDecimal totalDeliveredAmount) {
        this.totalDeliveredAmount = totalDeliveredAmount;
    }

    public Map<DeliveryStatus, Integer> getDeliveryReceiptsByStatus() {
        return deliveryReceiptsByStatus;
    }

    public void setDeliveryReceiptsByStatus(Map<DeliveryStatus, Integer> deliveryReceiptsByStatus) {
        this.deliveryReceiptsByStatus = deliveryReceiptsByStatus;
    }

    public Map<String, Integer> getDeliveryReceiptsPerSupplier() {
        return deliveryReceiptsPerSupplier;
    }

    public void setDeliveryReceiptsPerSupplier(Map<String, Integer> deliveryReceiptsPerSupplier) {
        this.deliveryReceiptsPerSupplier = deliveryReceiptsPerSupplier;
    }

    public Map<String, BigDecimal> getTotalCostsForDeliveredItemPerSupplier() {
        return totalCostsForDeliveredItemPerSupplier;
    }

    public void setTotalCostsForDeliveredItemPerSupplier(Map<String, BigDecimal> totalCostsForDeliveredItemPerSupplier) {
        this.totalCostsForDeliveredItemPerSupplier = totalCostsForDeliveredItemPerSupplier;
    }


    public ProcurementReportEntity getProcurementReportEntity() {
        return procurementReportEntity;
    }

    public void setProcurementReportEntity(ProcurementReportEntity procurementReportEntity) {
        this.procurementReportEntity = procurementReportEntity;
    }

    // toString(
    @Override
    public String toString() {
        return "DeliveryReceiptReportEntity{" +
                "id=" + id +
                ", totalDeliveryReceipts=" + totalDeliveryReceipts +
                ", totalDeliveredItems=" + totalDeliveredItems +
                ", totalDeliveredAmount=" + totalDeliveredAmount +
                ", deliveryReceiptsByStatus=" + deliveryReceiptsByStatus +
                ", deliveryReceiptsPerSupplier=" + deliveryReceiptsPerSupplier +
                ", totalCostsForDeliveredItemPerSupplier=" + totalCostsForDeliveredItemPerSupplier +
                '}';
    }
}
