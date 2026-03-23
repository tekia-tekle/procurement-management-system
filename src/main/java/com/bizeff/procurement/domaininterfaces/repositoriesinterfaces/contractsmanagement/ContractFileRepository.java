package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement;

import com.bizeff.procurement.models.contracts.ContractFile;

import java.util.List;
import java.util.Optional;

public interface ContractFileRepository {
    ContractFile save(ContractFile contractFile);
    ContractFile update(ContractFile contractFile);
    Optional<ContractFile> findById(Long id);
    Optional<ContractFile>findByFileUrl(String fileUrl);
    void deleteById(Long id);
    void deleteAll();
    List<ContractFile> findAll();
    List<ContractFile> findByContractId(Long contractId);
}
