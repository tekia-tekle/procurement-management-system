package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement;

import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.ContractStatus;

import java.util.List;
import java.util.Optional;

public interface ContractRepository {
    Contract save(Contract contract);
    Contract update(Contract contract);
    Optional<Contract> findByContractId(String contractId);
    List<Contract> findAll();
    List<Contract> findBySupplierId(String supplireId);
    List<Contract>findByStatus(ContractStatus status);

    void deleteByContractId(String contractId);
    void deleteAll();
}
