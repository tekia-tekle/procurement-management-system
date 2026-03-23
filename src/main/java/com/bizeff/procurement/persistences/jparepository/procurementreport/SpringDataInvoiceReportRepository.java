package com.bizeff.procurement.persistences.jparepository.procurementreport;

import com.bizeff.procurement.persistences.entity.procurementreport.InvoiceReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInvoiceReportRepository extends JpaRepository<InvoiceReportEntity, Long> {
}
