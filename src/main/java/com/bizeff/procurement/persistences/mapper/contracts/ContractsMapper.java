package com.bizeff.procurement.persistences.mapper.contracts;

import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.contracts.ContractFile;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.persistences.entity.contracts.ContractEntity;
import com.bizeff.procurement.persistences.entity.contracts.ContractFileEntity;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import com.bizeff.procurement.persistences.mapper.purchaseorder.PurchaseOrderMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.SupplierMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ContractsMapper {
    public static ContractEntity toEntity(Contract contract) {

        ContractEntity contractsEntity = new ContractEntity();
        if (contract.getId() != null){
            contractsEntity.setId(contract.getId());
        }
        contractsEntity.setContractId(contract.getContractId());
        contractsEntity.setSupplierEntity(SupplierMapper.toEntity(contract.getSupplier()));
        contractsEntity.setContractTitle(contract.getContractTitle());
        contractsEntity.setStartDate(contract.getStartDate());
        contractsEntity.setEndDate(contract.getEndDate());
        contractsEntity.setRenewalDate(contract.getRenewalDate());
        contractsEntity.setPaymentTerms(contract.getPaymentTerms());
        contractsEntity.setDeliveryTerms(contract.getDeliveryTerms());
        contractsEntity.setStatus(contract.getStatus());
        contractsEntity.setTotalCost(contract.getTotalCost());

        List<PurchaseOrderEntity> purchaseOrderEntities = contract.getPurchaseOrders().stream()
                .map(purchaseOrder -> {
                    PurchaseOrderEntity purchaseOrderEntity = PurchaseOrderMapper.toEntity(purchaseOrder);
                    purchaseOrderEntity.setContract(contractsEntity);
                    return purchaseOrderEntity;
                }).collect(Collectors.toList());

        contractsEntity.setPurchaseOrder(purchaseOrderEntities);

        contractsEntity.setRenewable(contract.isRenewable());
        contractsEntity.setNotified(contract.isNotified());
        contractsEntity.setApproved(contract.isApproved());

        List<ContractFileEntity> contractFileEntities = contract.getAttachments().stream()
                        .map(contractFile -> {
                            ContractFileEntity contractFileEntity = ContractFileMapper.toEntity(contractFile);
                            contractsEntity.addAttachements(contractFileEntity);
                            return contractFileEntity;
                        }).collect(Collectors.toList());

        contractsEntity.setAttachments(contractFileEntities);
        contractsEntity.setCreatedDate(contract.getCreatedDate());

        return contractsEntity;
    }

    public static Contract toModel(ContractEntity contractsEntity) {
        if (contractsEntity == null) {
            return null;
        }
        Contract contract = new Contract();
        contract.setId(contractsEntity.getId());
        contract.setContractId(contractsEntity.getContractId());
        contract.setContractTitle(contractsEntity.getContractTitle());
        contract.setSupplier(SupplierMapper.toModel(contractsEntity.getSupplierEntity()));
        contract.setStartDate(contractsEntity.getStartDate());
        contract.setEndDate(contractsEntity.getEndDate());
        contract.setRenewalDate(contractsEntity.getRenewalDate());
        contract.setPaymentTerms(contractsEntity.getPaymentTerms());
        contract.setDeliveryTerms(contractsEntity.getDeliveryTerms());
        contract.setStatus(contractsEntity.getStatus());
        contract.setTotalCost(contractsEntity.getTotalCost());
        contract.setRenewalDate(contractsEntity.getRenewalDate());
        contract.setApproved(contractsEntity.isApproved());
        contract.setNotified(contractsEntity.isNotified());

        List<PurchaseOrder> purchaseOrderList = contractsEntity.getPurchaseOrder().stream()
                .map(purchaseOrderEntity -> PurchaseOrderMapper.toModel(purchaseOrderEntity)).collect(Collectors.toList());

        contract.setPurchaseOrders(purchaseOrderList);

        List<ContractFile> contractFileList = contractsEntity.getAttachments().stream()
                .map(contractFileEntity -> {
                    ContractFile contractFile = ContractFileMapper.tomodel(contractFileEntity);
                    contract.addAttachment(contractFile);
                    return contractFile;
                }).collect(Collectors.toList());

        contract.setAttachments(contractFileList);

        contract.setCreatedDate(contractsEntity.getCreatedDate());

        return contract;
    }
}
