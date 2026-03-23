package com.bizeff.procurement.persistences.mapper.procurementreport;

import com.bizeff.procurement.models.procurementreport.DeliveryReceiptReport;
import com.bizeff.procurement.persistences.entity.procurementreport.DeliveryReceiptReportEntity;

public class DeliveryReceiptReportMapper {
    public static DeliveryReceiptReportEntity toEntity(DeliveryReceiptReport deliveryReceiptReport){
        DeliveryReceiptReportEntity deliveryReceiptReportEntity = new DeliveryReceiptReportEntity();
        if (deliveryReceiptReport.getId() != null){
            deliveryReceiptReportEntity.setId(deliveryReceiptReport.getId());
        }
        if (deliveryReceiptReport.getTotalDeliveryReceipts() != 0){
            deliveryReceiptReportEntity.setTotalDeliveryReceipts(deliveryReceiptReport.getTotalDeliveryReceipts());
        }
        deliveryReceiptReportEntity.setTotalDeliveredItems(deliveryReceiptReport.getTotalDeliveredItems());
        deliveryReceiptReportEntity.setTotalDeliveredAmount(deliveryReceiptReport.getTotalCostForDeliveredItem());
        deliveryReceiptReportEntity.setDeliveryReceiptsByStatus(deliveryReceiptReport.getDeliveryReceiptsByStatus());
        deliveryReceiptReportEntity.setTotalCostsForDeliveredItemPerSupplier(deliveryReceiptReport.getTotalCostsForDeliveredItemPerSupplier());
        deliveryReceiptReportEntity.setDeliveryReceiptsPerSupplier(deliveryReceiptReport.getDeliveryReceiptsFromSupplier());

        return deliveryReceiptReportEntity;
    }
    public static DeliveryReceiptReport toModel(DeliveryReceiptReportEntity deliveryReceiptReportEntity){
        DeliveryReceiptReport deliveryReceiptReport = new DeliveryReceiptReport();
        deliveryReceiptReport.setId(deliveryReceiptReportEntity.getId());
        deliveryReceiptReport.setTotalDeliveryReceipts(deliveryReceiptReportEntity.getTotalDeliveryReceipts());
        deliveryReceiptReport.setTotalDeliveredItems(deliveryReceiptReportEntity.getTotalDeliveredItems());
        deliveryReceiptReport.setTotalCostForDeliveredItem(deliveryReceiptReportEntity.getTotalDeliveredAmount());
        deliveryReceiptReport.setDeliveryReceiptsByStatus(deliveryReceiptReportEntity.getDeliveryReceiptsByStatus());
        deliveryReceiptReport.setTotalCostsForDeliveredItemPerSupplier(deliveryReceiptReportEntity.getTotalCostsForDeliveredItemPerSupplier());
        deliveryReceiptReport.setDeliveryReceiptsFromSupplier(deliveryReceiptReportEntity.getDeliveryReceiptsPerSupplier());


        return deliveryReceiptReport;
    }
}
