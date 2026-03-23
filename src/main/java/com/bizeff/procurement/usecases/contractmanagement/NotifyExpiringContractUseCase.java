package com.bizeff.procurement.usecases.contractmanagement;

import com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement.NotifyExpiringContractInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.NotifyExpiringContractOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.NotifyExpiringContractOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.services.contract.AlertingContractsServices;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class NotifyExpiringContractUseCase implements NotifyExpiringContractInputBoundary {
    private ContractRepository contractsRepository;
    private AlertingContractsServices alertingContractsServices;
    private NotifyExpiringContractOutputBoundary notifyExpiringContractOutputBoundary;
    public NotifyExpiringContractUseCase(ContractRepository contractsRepository, AlertingContractsServices alertingContractsServices, NotifyExpiringContractOutputBoundary notifyExpiringContractOutputBoundary){
        this.contractsRepository = contractsRepository;
        this.alertingContractsServices = alertingContractsServices;
        this.notifyExpiringContractOutputBoundary = notifyExpiringContractOutputBoundary;
    }
    @Override
    public List<NotifyExpiringContractOutputDS> notifyContracts(int daysBeforeExpirationThreshold) {
        if (daysBeforeExpirationThreshold < 0){
            throw new IllegalArgumentException("daysBeforeExpirationThreshold can't be negative.");
        }
        List<Contract> contracts = contractsRepository.findAll();
        if (contracts == null || contracts.isEmpty()){
            throw new NullPointerException(" the contract repository is empty. no contract can notify now.");
        }
        for (Contract contract: contracts){
            alertingContractsServices.getAllContracts().put(contract.getContractId(), contract);
        }
        List<Contract>expiringContracts = alertingContractsServices.getExpiringContracts(daysBeforeExpirationThreshold);
        if (expiringContracts == null || expiringContracts.isEmpty()){
            throw new IllegalArgumentException("there is no contract that is expiring with the days of expiring.");
        }
        else {
            expiringContracts = alertingContractsServices.alertingContracts(daysBeforeExpirationThreshold);
        }
        List<NotifyExpiringContractOutputDS> expiringContractOutputDS = new ArrayList<>();
        for (Contract contract: expiringContracts){
            contractsRepository.update(contract);
            LocalDate today = LocalDate.now();
            Long remainigDate = ChronoUnit.DAYS.between(today, contract.getEndDate());
            NotifyExpiringContractOutputDS output = new NotifyExpiringContractOutputDS(contract.getContractId(),contract.getContractTitle(),remainigDate, contract.isNotified());
            expiringContractOutputDS.add(output);
        }
        notifyExpiringContractOutputBoundary.notifyContracts(expiringContractOutputDS);
        return expiringContractOutputDS;
    }
}
