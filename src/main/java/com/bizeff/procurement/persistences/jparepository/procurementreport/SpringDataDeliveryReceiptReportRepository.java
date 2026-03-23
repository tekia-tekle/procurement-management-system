package com.bizeff.procurement.persistences.jparepository.procurementreport;

import com.bizeff.procurement.persistences.entity.procurementreport.DeliveryReceiptReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataDeliveryReceiptReportRepository extends JpaRepository<DeliveryReceiptReportEntity, Long> {
}
