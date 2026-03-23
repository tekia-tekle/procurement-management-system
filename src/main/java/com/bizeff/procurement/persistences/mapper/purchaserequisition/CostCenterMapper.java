package com.bizeff.procurement.persistences.mapper.purchaserequisition;

import com.bizeff.procurement.models.purchaserequisition.CostCenter;
import com.bizeff.procurement.persistences.entity.purchaserequisition.CostCenterEntity;

public class CostCenterMapper {

    public static CostCenter toModel(CostCenterEntity entity) {
        if (entity == null) return null;

        CostCenter domain = new CostCenter();
        domain.setId(entity.getId());
        domain.setCostCenterId(entity.getCostCenterId());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setAllocatedBudget(entity.getAllocatedBudget());
        domain.setUsedBudget(entity.getUsedBudget());
        domain.setRemainingBudget(entity.getRemainingBudget());

        return domain;
    }

    public static CostCenterEntity toEntity(CostCenter domain){
        if (domain == null) return null;

        CostCenterEntity entity = new CostCenterEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId()); // <-- important!
        }
        entity.setCostCenterId(domain.getCostCenterId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setAllocatedBudget(domain.getAllocatedBudget());
        entity.setUsedBudget(domain.getUsedBudget());
        entity.setRemainingBudget(domain.getRemainingBudget());
        return entity;
    }
}
