package com.bizeff.procurement.persistences.mapper.procurementreport;

import com.bizeff.procurement.models.procurementreport.PurchaseRequisitionReport;
import com.bizeff.procurement.persistences.entity.procurementreport.PurchaseRequisitionReportEntity;

public class PurchaseRequisitionReportMapper {
    public static PurchaseRequisitionReportEntity toEntity(PurchaseRequisitionReport purchaseRequisitionReport){
        PurchaseRequisitionReportEntity purchaseRequisitionReportEntity = new PurchaseRequisitionReportEntity();
        if (purchaseRequisitionReport.getId() !=  null){
            purchaseRequisitionReportEntity.setId(purchaseRequisitionReport.getId());
        }
        purchaseRequisitionReportEntity.setTotalRequisitions(purchaseRequisitionReport.getTotalRequisitions());
        purchaseRequisitionReportEntity.setTotalRequestedItems(purchaseRequisitionReport.getTotalRequestedItems());
        purchaseRequisitionReportEntity.setTotalSpendingAmount(purchaseRequisitionReport.getTotalSpendingAmount());
        purchaseRequisitionReportEntity.setRequisitionsByPriority(purchaseRequisitionReport.getRequisitionsByPriority());
        purchaseRequisitionReportEntity.setRequisitionsByDepartment(purchaseRequisitionReport.getRequisitionsByDepartment());
        purchaseRequisitionReportEntity.setTotalSpendingPerDepartment(purchaseRequisitionReport.getTotalSpendingPerDepartment());
        purchaseRequisitionReportEntity.setRequisitionStatusMap(purchaseRequisitionReport.getRequisitionStatusMap());

        return purchaseRequisitionReportEntity;
    }

    public static PurchaseRequisitionReport toModel(PurchaseRequisitionReportEntity purchaseRequisitionReportEntity){
        PurchaseRequisitionReport requisitionReport = new PurchaseRequisitionReport();
        requisitionReport.setId(purchaseRequisitionReportEntity.getId());
        requisitionReport.setTotalRequisitions(purchaseRequisitionReportEntity.getTotalRequisitions());
        requisitionReport.setTotalRequestedItems(purchaseRequisitionReportEntity.getTotalRequestedItems());
        requisitionReport.setTotalSpendingAmount(purchaseRequisitionReportEntity.getTotalSpendingAmount());
        requisitionReport.setRequisitionsByPriority(purchaseRequisitionReportEntity.getRequisitionsByPriority());
        requisitionReport.setRequisitionsByDepartment(purchaseRequisitionReportEntity.getRequisitionsByDepartment());
        requisitionReport.setTotalSpendingPerDepartment(purchaseRequisitionReportEntity.getTotalSpendingPerDepartment());
        requisitionReport.setRequisitionStatusMap(purchaseRequisitionReportEntity.getRequisitionStatusMap());


        return requisitionReport;
    }

}
