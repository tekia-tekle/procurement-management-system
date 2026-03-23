package com.bizeff.procurement.persistences.mapper.purchaserequisition;

import com.bizeff.procurement.models.purchaserequisition.RequestedItem;
import com.bizeff.procurement.persistences.entity.purchaserequisition.RequestedItemEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;

public class RequestedItemMapper {

    public static RequestedItemEntity toEntity(RequestedItem model, InventoryEntity inventoryEntity) {
        RequestedItemEntity entity = new RequestedItemEntity();

        entity.setRequestedQuantity(model.getRequestedQuantity());
        entity.setTotalPrice(model.getTotalPrice());
        entity.setInventory(inventoryEntity);
        entity.setItemId(inventoryEntity.getItemId());
        entity.setItemName(inventoryEntity.getItemName());
        entity.setQuantityAvailable(inventoryEntity.getQuantityAvailable());
        entity.setUnitPrice(inventoryEntity.getUnitPrice());
        entity.setItemCategory(inventoryEntity.getItemCategory());
        entity.setExpiryDate(inventoryEntity.getExpiryDate());
        entity.setSpecification(inventoryEntity.getSpecification());
        return entity;
    }
    public static RequestedItem toModel(RequestedItemEntity entity) {
        if (entity == null) return null;
        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(InventoryMapper.toModel(entity.getInventory()));
        requestedItem.setRequestedQuantity(entity.getRequestedQuantity());
        requestedItem.setTotalPrice(entity.getTotalPrice());
        return requestedItem;
    }
}
