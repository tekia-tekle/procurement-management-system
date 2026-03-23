package com.bizeff.procurement.persistences.jparepository.procurementreport;

import com.bizeff.procurement.persistences.entity.procurementreport.PurchaseRequisitionReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPurchaseRequisitionReportRepository extends JpaRepository<PurchaseRequisitionReportEntity, Long> {
}
