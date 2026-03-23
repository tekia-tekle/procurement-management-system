package com.bizeff.procurement.persistences.jparepository.procurementreport;


import com.bizeff.procurement.persistences.entity.procurementreport.ProcurementReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataProcurementReportRepository extends JpaRepository<ProcurementReportEntity, Long> {
    Optional<ProcurementReportEntity>findByReportId(String reportId);
    void deleteByReportId(String reportId);

}
