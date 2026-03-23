package com.bizeff.procurement.persistences.mapper.procurementreport;

import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.procurementreport.ContractReport;
import com.bizeff.procurement.persistences.entity.contracts.ContractEntity;
import com.bizeff.procurement.persistences.entity.procurementreport.ContractReportEntity;
import com.bizeff.procurement.persistences.mapper.contracts.ContractsMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ContractReportMapper {
    public static ContractReportEntity toEntity(ContractReport contractReport){
        ContractReportEntity contractReportEntity = new ContractReportEntity();

        if (contractReport.getId() != null){
            contractReportEntity.setId(contractReport.getId());
        }
        contractReportEntity.setTotalContracts(contractReport.getTotalContracts());
        contractReportEntity.setTotalContractsCost(contractReport.getTotalContractsCost());
        contractReportEntity.setContractsByStatus(contractReport.getContractsByStatus());
        contractReportEntity.setTotalContractCostsPerSupplier(contractReport.getTotalContractCostsPerSupplier());
        contractReportEntity.setContractsWithSupplier(contractReport.getContractsWithSupplier());
        List<ContractEntity> expiringContractLists = contractReport.getExpiringContracts().stream()
                        .map(ContractsMapper::toEntity).collect(Collectors.toList());

        contractReportEntity.setExpiringContracts(expiringContractLists);


        return contractReportEntity;

    }
    public static ContractReport toModel(ContractReportEntity contractReportEntity){

        ContractReport contractReport = new ContractReport();
        contractReport.setId(contractReportEntity.getId());
        contractReport.setTotalContracts(contractReportEntity.getTotalContracts());
        contractReport.setTotalContractsCost(contractReportEntity.getTotalContractsCost());
        contractReport.setContractsByStatus(contractReportEntity.getContractsByStatus());
        contractReport.setContractsWithSupplier(contractReportEntity.getContractsWithSupplier());


        List<Contract> expiringContracts = contractReportEntity.getExpiringContracts().stream()
                .map(ContractsMapper::toModel).collect(Collectors.toList());

        contractReport.setExpiringContracts(expiringContracts);

        return contractReport;
    }
}
