package com.bizeff.procurement.persistences.mapper.procurementreport;

import com.bizeff.procurement.models.procurementreport.PurchaseOrderReport;
import com.bizeff.procurement.persistences.entity.procurementreport.PurchaseOrderReportEntity;

public class PurchaseOrderReportMapper {
    public static PurchaseOrderReportEntity toEntity(PurchaseOrderReport purchaseOrderReport){

        PurchaseOrderReportEntity purchaseOrderReportEntity = new PurchaseOrderReportEntity();
        if (purchaseOrderReport.getId() != null){
            purchaseOrderReportEntity.setId(purchaseOrderReport.getId());;
        }
        purchaseOrderReportEntity.setTotalPurchaseOrders(purchaseOrderReport.getTotalPurchaseOrders());
        purchaseOrderReportEntity.setTotalPurchaseOrderCosts(purchaseOrderReport.getTotalPurchaseOrderCosts());
        purchaseOrderReportEntity.setPurchaseOrderStatusMap(purchaseOrderReport.getPurchaseOrderStatusMap());
        purchaseOrderReportEntity.setPurchaseOrderSupplierMap(purchaseOrderReport.getPurchaseOrdersForSupplier());
        purchaseOrderReportEntity.setPurchaseOrderDepartmentMap(purchaseOrderReport.getPurchaseOrdersByDepartment());
        purchaseOrderReportEntity.setPurchaseOrderTotalSpendPerSupplier(purchaseOrderReport.getPurchaseOrderTotalSpendPerSupplier());
        purchaseOrderReportEntity.setPurchaseOrderTotalSpendPerDepartment(purchaseOrderReport.getPurchaseOrderTotalSpendPerDepartment());


        return purchaseOrderReportEntity;

    }
    public static PurchaseOrderReport toModel(PurchaseOrderReportEntity purchaseOrderReportEntity){

        PurchaseOrderReport purchaseOrderReport = new PurchaseOrderReport();
        purchaseOrderReport.setId(purchaseOrderReportEntity.getId());
        purchaseOrderReport.setTotalPurchaseOrders(purchaseOrderReportEntity.getTotalPurchaseOrders());
        purchaseOrderReport.setTotalPurchaseOrderCosts(purchaseOrderReportEntity.getTotalPurchaseOrderCosts());
        purchaseOrderReport.setPurchaseOrderStatusMap(purchaseOrderReportEntity.getPurchaseOrderStatusMap());
        purchaseOrderReport.setPurchaseOrdersForSupplier(purchaseOrderReportEntity.getPurchaseOrderSupplierMap());
        purchaseOrderReport.setPurchaseOrdersByDepartment(purchaseOrderReportEntity.getPurchaseOrderDepartmentMap());
        purchaseOrderReport.setPurchaseOrderTotalSpendPerSupplier(purchaseOrderReportEntity.getPurchaseOrderTotalSpendPerSupplier());
        purchaseOrderReport.setPurchaseOrderTotalSpendPerDepartment(purchaseOrderReportEntity.getPurchaseOrderTotalSpendPerDepartment());
        //storing the Map of List of purchase requisitions with respect to the purchase order id.

        return purchaseOrderReport;
    }
}
