package com.bizeff.procurement.persistences.jparepository.supplierperformance;

import com.bizeff.procurement.models.enums.SupplierPerformanceRate;
import com.bizeff.procurement.persistences.entity.supplierPerformance.SupplierPerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpringDataSupplierPerformanceRepository extends JpaRepository<SupplierPerformanceEntity,Long> {
    List<SupplierPerformanceEntity>findBySupplier_SupplierId(String supplierId);
    List<SupplierPerformanceEntity>findByPerformanceRate(SupplierPerformanceRate performanceRate);
    List<SupplierPerformanceEntity>findByEvaluationDateAfter(LocalDate date);
    List<SupplierPerformanceEntity>findByEvaluationDateBefore(LocalDate date);
    void deleteBySupplier_SupplierId(String supplierId);
}
