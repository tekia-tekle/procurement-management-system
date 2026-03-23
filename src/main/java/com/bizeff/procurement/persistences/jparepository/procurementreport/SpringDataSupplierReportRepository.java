package com.bizeff.procurement.persistences.jparepository.procurementreport;

import com.bizeff.procurement.persistences.entity.procurementreport.SupplierReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSupplierReportRepository extends JpaRepository<SupplierReportEntity, Long> {
}
