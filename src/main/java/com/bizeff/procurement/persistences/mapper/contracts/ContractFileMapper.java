package com.bizeff.procurement.persistences.mapper.contracts;

import com.bizeff.procurement.models.contracts.ContractFile;
import com.bizeff.procurement.persistences.entity.contracts.ContractFileEntity;

public class ContractFileMapper {
    public static ContractFileEntity toEntity(ContractFile contractFile){
        if (contractFile == null) return null;
        ContractFileEntity contractFileEntity = new ContractFileEntity();
        if (contractFile.getId() != null) contractFileEntity.setId(contractFile.getId());
        contractFileEntity.setFileName(contractFile.getFileName());
        contractFileEntity.setFileType(contractFile.getFileType());
        contractFileEntity.setFileUrl(contractFile.getFileUrl());
        contractFileEntity.setUploadDate(contractFile.getUploadDate());
        return contractFileEntity;
    }

    public static ContractFile tomodel(ContractFileEntity contractFileEntity){
        if (contractFileEntity == null) return null;
        ContractFile contractFile = new ContractFile();
        contractFile.setId(contractFileEntity.getId());
        contractFile.setFileName(contractFileEntity.getFileName());
        contractFile.setFileType(contractFileEntity.getFileType());
        contractFile.setFileUrl(contractFileEntity.getFileUrl());
        contractFile.setUploadDate(contractFileEntity.getUploadDate());
        return contractFile;
    }
}
