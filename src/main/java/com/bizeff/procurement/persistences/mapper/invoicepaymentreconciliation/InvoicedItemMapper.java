package com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation;

import com.bizeff.procurement.models.invoicepaymentreconciliation.InvoicedItem;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoicedItemEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;

public class InvoicedItemMapper {
    public static InvoicedItemEntity toEntity(InvoicedItem invoicedItem, InventoryEntity inventoryEntity){
        if (inventoryEntity == null)return null;

        InvoicedItemEntity invoicedItemEntity = new InvoicedItemEntity();
        invoicedItemEntity.setInventory(inventoryEntity);
        invoicedItemEntity.setInvoicedQuantity(invoicedItem.getInvoicedQuantity());
        invoicedItemEntity.setTotalPrice(invoicedItem.getTotalPrice());

        return invoicedItemEntity;
    }
    public static InvoicedItem toModel(InvoicedItemEntity invoicedItemEntity){
        if (invoicedItemEntity == null) return null;
        InvoicedItem invoicedItem = new InvoicedItem();

        invoicedItem.setInventory(InventoryMapper.toModel(invoicedItemEntity.getInventory()));
        invoicedItem.setInvoicedQuantity(invoicedItemEntity.getInvoicedQuantity());
        invoicedItem.setTotalPrice(invoicedItemEntity.getTotalPrice());

        return invoicedItem;
    }
}
