package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.ContractReport;

import java.util.List;
import java.util.Optional;

public interface ContractReportRepository {
    ContractReport save(ContractReport contractReport);
    Optional<ContractReport>findById(Long id);
    void deleteById(Long id);
    List<ContractReport> findAll();
    void deleteAll();
}
