package com.bizeff.procurement.persistences.jparepository.contracts;

import com.bizeff.procurement.persistences.entity.contracts.ContractFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataContractFileRepository extends JpaRepository<ContractFileEntity, Long> {
    List<ContractFileEntity> findByContractId(Long contractId);
    Optional<ContractFileEntity> findByFileUrl(String fileUrl);

}
