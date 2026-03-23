package com.bizeff.procurement.persistences.jparepository.procurementreport;

import com.bizeff.procurement.persistences.entity.procurementreport.PaymentReconciliationReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPaymentReconciliationReportRepository extends JpaRepository<PaymentReconciliationReportEntity, Long> {
}
