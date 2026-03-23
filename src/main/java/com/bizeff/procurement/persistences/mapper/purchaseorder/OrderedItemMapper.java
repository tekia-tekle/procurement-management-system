package com.bizeff.procurement.persistences.mapper.purchaseorder;

import com.bizeff.procurement.models.purchaseorder.OrderedItem;
import com.bizeff.procurement.persistences.entity.purchaseorder.OrderedItemEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;

public class OrderedItemMapper {

    public static OrderedItemEntity toEntity(OrderedItem orderedItem, InventoryEntity inventoryEntity){
        if (orderedItem == null ) return null;
        OrderedItemEntity entity = new OrderedItemEntity();
        if (orderedItem.getId() != null){
            entity.setId(orderedItem.getId());
        }
        entity.setOrderedQuantity(orderedItem.getOrderedQuantity());;
        entity.setTotalPrice(orderedItem.getTotalPrice());
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
    public static OrderedItem toModel(OrderedItemEntity entity){
        if (entity == null) return null;
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.setInventory(InventoryMapper.toModel(entity.getInventory()));
        orderedItem.setOrderedQuantity(entity.getOrderedQuantity());
        orderedItem.setTotalPrice(entity.getTotalPrice());
        return orderedItem;
    }
}
