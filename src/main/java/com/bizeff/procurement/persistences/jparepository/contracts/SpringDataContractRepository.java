package com.bizeff.procurement.persistences.jparepository.contracts;


import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.persistences.entity.contracts.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataContractRepository extends JpaRepository<ContractEntity, Long> {
    Optional<ContractEntity> findByContractId(String contractId);
    List<ContractEntity>findBySupplierEntity_SupplierId(String supplierId);
    List<ContractEntity>findByStatus(ContractStatus status);
    void deleteByContractId(String contractId);
}
