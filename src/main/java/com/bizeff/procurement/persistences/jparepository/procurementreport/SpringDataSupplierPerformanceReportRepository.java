package com.bizeff.procurement.persistences.jparepository.procurementreport;

import com.bizeff.procurement.persistences.entity.procurementreport.SupplierPerformanceReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSupplierPerformanceReportRepository extends JpaRepository<SupplierPerformanceReportEntity, Long> {
}
