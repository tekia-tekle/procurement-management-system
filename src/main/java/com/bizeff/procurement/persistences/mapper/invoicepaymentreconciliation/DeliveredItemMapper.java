package com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation;

import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveredItem;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveredItemEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;

public class DeliveredItemMapper {
    public static DeliveredItemEntity toEntity(DeliveredItem deliveredItem, InventoryEntity inventoryEntity){
        if (deliveredItem == null)return null;

        DeliveredItemEntity deliveredItemEntity = new DeliveredItemEntity();
        deliveredItemEntity.setInventory(inventoryEntity);
        deliveredItemEntity.setDeliveredQuantity(deliveredItem.getDeliveredQuantity());
        deliveredItemEntity.setTotalPrice(deliveredItem.getTotalPrice());

        return deliveredItemEntity;
    }
    public static DeliveredItem toModel(DeliveredItemEntity deliveredItemEntity){
        if (deliveredItemEntity == null) return null;
        DeliveredItem deliveredItem = new DeliveredItem();

        deliveredItem.setInventory(InventoryMapper.toModel(deliveredItemEntity.getInventory()));
        deliveredItem.setDeliveredQuantity(deliveredItemEntity.getDeliveredQuantity());
        deliveredItem.setTotalPrice(deliveredItemEntity.getTotalPrice());

        return deliveredItem;
    }
}
