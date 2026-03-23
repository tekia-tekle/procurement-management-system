package com.bizeff.procurement.persistences.mapper.purchaserequisition;

import com.bizeff.procurement.models.purchaserequisition.*;
import com.bizeff.procurement.persistences.entity.purchaserequisition.PurchaseRequisitionEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.RequestedItemEntity;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.SupplierMapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseRequisitionMapper {

    // Convert Model to Entity
    public static PurchaseRequisitionEntity toEntity(PurchaseRequisition requisition) {
        if (requisition == null) return null;

        PurchaseRequisitionEntity entity = new PurchaseRequisitionEntity();
        if (requisition.getId() != null){
            entity.setId(requisition.getId());
        }
        entity.setRequisitionId(requisition.getRequisitionId());
        entity.setRequisitionNumber(requisition.getRequisitionNumber());
        entity.setRequisitionDate(requisition.getRequisitionDate());

        entity.setDepartmentEntity(DepartmentMapper.toEntity(requisition.getDepartment()));
        entity.setRequestedBy(UserMapper.toEntity(requisition.getRequestedBy()));
        entity.setCostCenterEntity(CostCenterMapper.toEntity(requisition.getCostCenter()));

        entity.setPriorityLevel(requisition.getPriorityLevel());
        entity.setExpectedDeliveryDate(requisition.getExpectedDeliveryDate());
        entity.setJustification(requisition.getJustification());

        entity.setRequisitionStatus(requisition.getRequisitionStatus());
        entity.setUpdatedDate(requisition.getUpdatedDate());

        List<RequestedItemEntity> requestedItemEntities = requisition.getItems().stream()
                .map(requestedItem -> {
                    RequestedItemEntity requestedItemEntity = RequestedItemMapper.toEntity(
                            requestedItem,
                            InventoryMapper.toEntity(requestedItem.getInventory())
                    );
                    requestedItemEntity.setPurchaseRequisition(entity); // 🔥 Set the owning side
                    return requestedItemEntity;
                })
                .collect(Collectors.toList());

        entity.setItems(requestedItemEntities);
        if (requisition.getSupplier() != null){
            entity.setSupplierEntity(SupplierMapper.toEntity(requisition.getSupplier()));
        }
        return entity;
    }

    public static PurchaseRequisition toModel(PurchaseRequisitionEntity entity) {
        if (entity == null){
            return null;
        }
        User user = UserMapper.toDomain(entity.getRequestedBy());
        Department department = DepartmentMapper.toDomain(entity.getDepartmentEntity());
        CostCenter costCenter =CostCenterMapper.toModel(entity.getCostCenterEntity());

        PurchaseRequisition requisition = new PurchaseRequisition();
        requisition.setId(entity.getId());
        requisition.setRequisitionId(entity.getRequisitionId());
        requisition.setRequisitionNumber(entity.getRequisitionNumber());
        requisition.setRequisitionDate(entity.getRequisitionDate());
        requisition.setDepartment(department);
        requisition.setRequestedBy(user);
        requisition.setCostCenter(costCenter);

        List<RequestedItem>requestedItemList = entity.getItems().stream()
                .map(requestedItemEntity -> RequestedItemMapper.toModel(requestedItemEntity))
                .collect(Collectors.toList());

        requisition.setItems(requestedItemList);
        requisition.setPriorityLevel(entity.getPriorityLevel());
        requisition.setExpectedDeliveryDate(entity.getExpectedDeliveryDate());
        requisition.setJustification(entity.getJustification());
        requisition.setUpdatedDate(entity.getUpdatedDate());
        requisition.setRequisitionStatus(entity.getRequisitionStatus());
        if (entity.getSupplierEntity() != null){
            requisition.setSupplier(SupplierMapper.toModel(entity.getSupplierEntity()));
        }
        return requisition;
    }
}
