package com.bizeff.procurement.persistences.jparepository.procurementreport;

import com.bizeff.procurement.persistences.entity.procurementreport.ContractReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataContractReportRepository extends JpaRepository<ContractReportEntity, Long> {

}
