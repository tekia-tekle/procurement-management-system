package com.bizeff.procurement.persistences.repositoryimplementation.contracts;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.persistences.entity.contracts.ContractEntity;
import com.bizeff.procurement.persistences.entity.contracts.ContractFileEntity;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.SupplierEntity;
import com.bizeff.procurement.persistences.jparepository.contracts.SpringDataContractFileRepository;
import com.bizeff.procurement.persistences.jparepository.contracts.SpringDataContractRepository;
import com.bizeff.procurement.persistences.jparepository.purchaseOrder.SpringDataPurchaseOrderRepository;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataSupplierManagementRepository;
import com.bizeff.procurement.persistences.mapper.contracts.ContractsMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.contracts.ContractsMapper.toModel;

@Repository
public class JpaContractRepository implements ContractRepository {
    private SpringDataContractRepository springDataContractRepository;
    private SpringDataPurchaseOrderRepository springDataPurchaseOrderRepository;
    private SpringDataContractFileRepository springDataContractFileRepository;
    private SpringDataSupplierManagementRepository springDataSupplierManagementRepository;
    public JpaContractRepository(SpringDataContractRepository springDataContractRepository,
                                 SpringDataPurchaseOrderRepository springDataPurchaseOrderRepository,
                                 SpringDataContractFileRepository springDataContractFileRepository,
                                 SpringDataSupplierManagementRepository springDataSupplierManagementRepository) {
        this.springDataContractRepository = springDataContractRepository;
        this.springDataPurchaseOrderRepository = springDataPurchaseOrderRepository;
        this.springDataContractFileRepository = springDataContractFileRepository;
        this.springDataSupplierManagementRepository = springDataSupplierManagementRepository;
    }

    @Override
    public Contract save(Contract contract) {
        ContractEntity entity = ContractsMapper.toEntity(contract);
        if (contract.getPurchaseOrders() != null) {
            List<PurchaseOrderEntity> managedPurchaseOrders = contract.getPurchaseOrders().stream()
                    .map(po -> springDataPurchaseOrderRepository.findById(po.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase order not found: " + po.getId())))
                    .collect(Collectors.toList());
            entity.setPurchaseOrder(managedPurchaseOrders);
        }
        ContractEntity savedEntity = springDataContractRepository.save(entity);
        return toModel(savedEntity);
    }
    @Override
    public Contract update(Contract contract) {
        if (contract.getContractId() == null) {
            throw new IllegalArgumentException("Contract ID cannot be null.");
        }
        Optional<ContractEntity> existingContractOpt = springDataContractRepository.findByContractId(contract.getContractId());
        if (existingContractOpt.isEmpty()) {
            throw new IllegalArgumentException("Contract with ID " + contract.getContractId() + " does not exist.");
        }
        ContractEntity existingContract = existingContractOpt.get();
        // Update fields from the provided contract

        if (contract.getSupplier() != null && contract.getSupplier().getId() != null) {
            SupplierEntity managedSupplier = springDataSupplierManagementRepository.findById(contract.getSupplier().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Supplier not found: " + contract.getSupplier().getId()));
            existingContract.setSupplierEntity(managedSupplier);
        }

        existingContract.setContractTitle(contract.getContractTitle());
        existingContract.setStartDate(contract.getStartDate());
        existingContract.setEndDate(contract.getEndDate());
        existingContract.setRenewalDate(contract.getRenewalDate());
        existingContract.setPaymentTerms(contract.getPaymentTerms());
        existingContract.setDeliveryTerms(contract.getDeliveryTerms());
        existingContract.setStatus(contract.getStatus());
        existingContract.setTotalCost(contract.getTotalCost());

        if (contract.getPurchaseOrders() != null) {
            List<PurchaseOrderEntity> managedPurchaseOrders = contract.getPurchaseOrders().stream()
                    .map(po -> springDataPurchaseOrderRepository.findById(po.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Purchase order not found: " + po.getId())))
                    .collect(Collectors.toList());
            existingContract.setPurchaseOrder(managedPurchaseOrders);
        }
        existingContract.setRenewable(contract.isRenewable());
        existingContract.setNotified(contract.isNotified());
        existingContract.setApproved(contract.isApproved());

        if (contract.getAttachments() != null) {
            List<ContractFileEntity> contractFileEntities = contract.getAttachments().stream()
                    .map(contractFile -> springDataContractFileRepository.findByFileUrl(contractFile.getFileUrl())
                            .orElseThrow(() -> new IllegalArgumentException("Contract file not found: " + contractFile.getFileUrl())))
                    .collect(Collectors.toList());
            existingContract.setAttachments(contractFileEntities);
        }
        existingContract.setCreatedDate(contract.getCreatedDate());

        ContractEntity savedEntity = springDataContractRepository.save(existingContract);
        return toModel(savedEntity);
    }
    @Override
    public Optional<Contract> findByContractId(String contractId) {
        return springDataContractRepository.findByContractId(contractId)
                .map(contractsEntity -> toModel(contractsEntity));
    }
    @Override
    public List<Contract> findAll() {
        return springDataContractRepository.findAll().stream().map(ContractsMapper::toModel).collect(Collectors.toList());
    }
    @Override
    public void deleteByContractId(String contractId) {
        springDataContractRepository.deleteByContractId(contractId);
    }
    @Override
    public List<Contract> findBySupplierId(String supplireId) {
        return springDataContractRepository.findBySupplierEntity_SupplierId(supplireId).stream()
                .map(contractEntity -> toModel(contractEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<Contract> findByStatus(ContractStatus status) {
        return springDataContractRepository.findByStatus(status).stream()
                .map(contractEntity -> toModel(contractEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataContractRepository.deleteAll();
    }
}
