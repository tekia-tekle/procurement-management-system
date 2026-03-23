package com.bizeff.procurement.persistences.mapper.purchaserequisition;


import com.bizeff.procurement.models.purchaserequisition.CostCenter;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.persistences.entity.purchaserequisition.CostCenterEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.DepartmentEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentMapper {

    public static DepartmentEntity toEntity(Department domain) {
        if (domain == null)return null;

        DepartmentEntity entity = new DepartmentEntity();
        if (domain.getId() != null){
            entity.setId(domain.getId());
        }
        entity.setDepartmentId(domain.getDepartmentId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setBudget(BudgetMapper.toEntity(domain.getBudget()));

        if (domain.getCostCenters() != null ) {
            List<CostCenterEntity> costCenterEntities = domain.getCostCenters().stream()
                    .map(costCenter -> {
                        CostCenterEntity costCenterEntity = CostCenterMapper.toEntity(costCenter);
                        entity.addCostCenter(costCenterEntity);
                        return costCenterEntity;
                    }).collect(Collectors.toList());
            entity.setCostCenters(costCenterEntities);
        }
        return entity;
    }

    public static Department toDomain(DepartmentEntity entity) {
        if (entity == null) return null;

        Department domain = new Department();
        domain.setId(entity.getId());
        domain.setDepartmentId(entity.getDepartmentId());
        domain.setName(entity.getName());
        domain.setDescription(entity.getDescription());
        domain.setBudget(BudgetMapper.toModel(entity.getBudget()));

        if (entity.getCostCenters() != null) {
            entity.getCostCenters().forEach(costCenterEntity -> {
                CostCenter costCenter = CostCenterMapper.toModel(costCenterEntity);
                domain.addCostCenter(costCenter);
            });
        }

        return domain;
    }
}



