package com.bizeff.procurement.persistences.repositoryimplementation.contracts;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractFileRepository;
import com.bizeff.procurement.models.contracts.ContractFile;
import com.bizeff.procurement.persistences.entity.contracts.ContractFileEntity;
import com.bizeff.procurement.persistences.jparepository.contracts.SpringDataContractFileRepository;
import com.bizeff.procurement.persistences.mapper.contracts.ContractFileMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaContractFileRepository implements ContractFileRepository {

    private SpringDataContractFileRepository springDataContractFileRepository;
    public JpaContractFileRepository(SpringDataContractFileRepository springDataContractFileRepository){
        this.springDataContractFileRepository = springDataContractFileRepository;
    }

    @Override
    public ContractFile save(ContractFile contractFile) {
        ContractFileEntity contractFileEntity = ContractFileMapper.toEntity(contractFile);

        ContractFileEntity savedEntity = springDataContractFileRepository.save(contractFileEntity);

        return ContractFileMapper.tomodel(savedEntity);
    }
    @Override
    public ContractFile update(ContractFile contractFile) {
        if (contractFile.getId() == null) {
            throw new IllegalArgumentException("ContractFile ID must not be null.");
        }
        Optional<ContractFileEntity> existingFileOpt = springDataContractFileRepository.findById(contractFile.getId());
        if (existingFileOpt.isEmpty()) {
            throw new IllegalArgumentException("ContractFile with ID " + contractFile.getId() + " does not exist.");
        }
        ContractFileEntity existingFile = existingFileOpt.get();
        // Update fields from the provided contractFile
        existingFile.setFileName(contractFile.getFileName());
        existingFile.setFileType(contractFile.getFileType());
        existingFile.setFileUrl(contractFile.getFileUrl());
        existingFile.setUploadDate(contractFile.getUploadDate());
        // Save the updated entity
        ContractFileEntity updatedEntity = springDataContractFileRepository.save(existingFile);
        // Convert back to model
        return ContractFileMapper.tomodel(updatedEntity);
    }
    @Override
    public Optional<ContractFile> findById(Long id) {
        return springDataContractFileRepository.findById(id).map(ContractFileMapper::tomodel);
    }
    @Override
    public void deleteById(Long id) {
        springDataContractFileRepository.deleteById(id);
    }
    @Override
    public void deleteAll() {
        springDataContractFileRepository.deleteAll();
    }
    @Override
    public List<ContractFile> findAll() {
        return springDataContractFileRepository.findAll().stream()
                .map(ContractFileMapper::tomodel).toList();
    }
    @Override
    public List<ContractFile> findByContractId(Long contractId) {
        return springDataContractFileRepository.findByContractId(contractId).stream()
                .map(ContractFileMapper::tomodel).toList();
    }
    @Override
    public Optional<ContractFile> findByFileUrl(String fileUrl) {
        return springDataContractFileRepository.findByFileUrl(fileUrl).map(ContractFileMapper::tomodel);
    }

}
