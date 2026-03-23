package com.bizeff.procurement.persistences.jparepository.procurementreport;

import com.bizeff.procurement.persistences.entity.procurementreport.PurchaseOrderReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPurchaseOrderReportRepository extends JpaRepository<PurchaseOrderReportEntity, Long> {
}
