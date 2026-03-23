package com.bizeff.procurement.persistences.mapper.procurementreport;

import com.bizeff.procurement.models.procurementreport.SupplierPerformanceReport;
import com.bizeff.procurement.persistences.entity.procurementreport.SupplierPerformanceReportEntity;
import com.bizeff.procurement.persistences.mapper.supplierPerformance.SupplierPerformanceMapper;

import java.util.stream.Collectors;
import java.util.Map.Entry;

public class SupplierPerformanceReportMapper {
    public static SupplierPerformanceReport toModel(SupplierPerformanceReportEntity supplierPerformanceReportEntity){
        if (supplierPerformanceReportEntity == null) return null;
        SupplierPerformanceReport supplierPerformanceReport = new SupplierPerformanceReport();
        supplierPerformanceReport.setId(supplierPerformanceReportEntity.getId());
        if (supplierPerformanceReportEntity.getLatestSupplierPerformance() != null){
            supplierPerformanceReport.setLatestPerformancePerSupplier(
                    supplierPerformanceReportEntity.getLatestSupplierPerformance().entrySet().stream()
                            .collect(Collectors.toMap(
                                    Entry::getKey,
                                    entry -> SupplierPerformanceMapper.toModel(entry.getValue())
                            ))
            );
        }
        supplierPerformanceReport.setSupplierWithAveragePerformanceScore(supplierPerformanceReportEntity.getAverageRatingsPerSupplier());
        supplierPerformanceReport.setSuppliersByCurrentPerformanceRate(supplierPerformanceReportEntity.getPerformanceByCurrentRate());
        if (supplierPerformanceReportEntity.getPerformancesPerSupplier() != null){
            supplierPerformanceReport.setSupplierPerformances(
                    supplierPerformanceReportEntity.getPerformancesPerSupplier().stream()
                            .map(SupplierPerformanceMapper::toModel)
                            .collect(Collectors.toList())
            );
        }
        return supplierPerformanceReport;
    }
    public static SupplierPerformanceReportEntity toEntity(SupplierPerformanceReport supplierPerformanceReport){
        if (supplierPerformanceReport == null) return null;

        SupplierPerformanceReportEntity supplierPerformanceReportEntity = new SupplierPerformanceReportEntity();
        if (supplierPerformanceReport.getId() != null){
            supplierPerformanceReportEntity.setId(supplierPerformanceReport.getId());
        }
        if (supplierPerformanceReport.getLatestPerformancePerSupplier() != null){
            supplierPerformanceReportEntity.setLatestSupplierPerformance(
                    supplierPerformanceReport.getLatestPerformancePerSupplier().entrySet().stream()
                            .collect(Collectors.toMap(
                                    Entry::getKey,
                                    entry -> SupplierPerformanceMapper.toEntity(entry.getValue())
                            ))
            );
        }
        supplierPerformanceReportEntity.setAverageRatingsPerSupplier(supplierPerformanceReport.getSupplierWithAveragePerformanceScore());
        supplierPerformanceReportEntity.setPerformanceByCurrentRate(supplierPerformanceReport.getSuppliersByCurrentPerformanceRate());
        if (supplierPerformanceReport.getSupplierPerformances() != null){
            supplierPerformanceReportEntity.setPerformancesPerSupplier(
                    supplierPerformanceReport.getSupplierPerformances().stream()
                            .map(SupplierPerformanceMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }
        return supplierPerformanceReportEntity;
    }
}
