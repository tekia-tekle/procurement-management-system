package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance;

import com.bizeff.procurement.models.enums.SupplierPerformanceRate;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SupplierPerformanceRepository {
    SupplierPerformance save(SupplierPerformance supplierPerformance);
    SupplierPerformance update(SupplierPerformance supplierPerformance);
    Optional<SupplierPerformance>findById(Long id);
    List<SupplierPerformance> findBySupplierId(String supplierId);
    List<SupplierPerformance>findByPerformanceRate(SupplierPerformanceRate performanceRate);
    List<SupplierPerformance>findByEvaluationDateAfter(LocalDate date);
    List<SupplierPerformance>findByEvaluationDateBefore(LocalDate date);
    void deleteById(Long id);
    void deleteBySupplierId(String supplierId);

    List<SupplierPerformance> findAll();
    void deleteAll();
}
