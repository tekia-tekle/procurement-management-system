package com.bizeff.procurement.persistences.mapper.procurementreport;

import com.bizeff.procurement.models.procurementreport.SupplierReport;
import com.bizeff.procurement.persistences.entity.procurementreport.SupplierReportEntity;

public class SupplierReportMapper {
    public static SupplierReportEntity toEntity(SupplierReport supplierReport){
        SupplierReportEntity supplierReportEntity = new SupplierReportEntity();
        if (supplierReport.getId() != null){
            supplierReportEntity.setId(supplierReport.getId());
        }
        supplierReportEntity.setTotalSuppliers(supplierReport.getTotalSuppliers());
        supplierReportEntity.setSuppliersByCategory(supplierReport.getSuppliersByCategory());
        supplierReportEntity.setActiveSuppliers(supplierReport.getActiveSuppliers());

        return supplierReportEntity;
    }
    public static SupplierReport toModel(SupplierReportEntity supplierReportEntity){
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setId(supplierReportEntity.getId());
        supplierReport.setTotalSuppliers(supplierReportEntity.getTotalSuppliers());
        supplierReport.setSuppliersByCategory(supplierReportEntity.getSuppliersByCategory());
        supplierReport.setActiveSuppliers(supplierReportEntity.getActiveSuppliers());


        return supplierReport;
    }
}
