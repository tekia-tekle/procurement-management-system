package com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation;

import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveredItem;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveredItemEntity;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveryReceiptEntity;
import com.bizeff.procurement.persistences.mapper.purchaseorder.PurchaseOrderMapper;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.UserMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.SupplierMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryReceiptsMapper {

    public static DeliveryReceiptEntity toEntity(DeliveryReceipt deliveryReceipt) {
        if (deliveryReceipt == null) return null;
        DeliveryReceiptEntity entity = new DeliveryReceiptEntity();
        if (deliveryReceipt.getId() != null){
            entity.setId(deliveryReceipt.getId());
        }
        entity.setReceiptId(deliveryReceipt.getReceiptId());
        entity.setSupplier(SupplierMapper.toEntity(deliveryReceipt.getSupplier()));
        entity.setPurchaseOrder(PurchaseOrderMapper.toEntity(deliveryReceipt.getPurchaseOrder()));

        List<DeliveredItemEntity> deliveredItemEntityList = deliveryReceipt.getReceivedItems().stream()
                .map(deliveredItem -> {
                    DeliveredItemEntity deliveredItemEntity = DeliveredItemMapper.toEntity(deliveredItem, InventoryMapper.toEntity(deliveredItem.getInventory()));
                    deliveredItemEntity.setDeliveryReceipt(entity);
                    return deliveredItemEntity;
                }).collect(Collectors.toList());

        entity.setReceivedItems(deliveredItemEntityList);
        entity.setDeliveryDate(deliveryReceipt.getDeliveryDate());
        entity.setDeliveryLocation(deliveryReceipt.getDeliveryLocation());
        entity.setReceivedBy(UserMapper.toEntity(deliveryReceipt.getReceivedBy()));
        entity.setDeliveryStatus(deliveryReceipt.getDeliveryStatus());
        entity.setDeliveryNotes(deliveryReceipt.getDeliveryNotes());

        return entity;
    }

    public static DeliveryReceipt toModel(DeliveryReceiptEntity entity) {
        if (entity == null) {
            return null;
        }
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt();
        deliveryReceipt.setId(entity.getId());
        deliveryReceipt.setReceiptId(entity.getReceiptId());
        deliveryReceipt.setSupplier(SupplierMapper.toModel(entity.getSupplier()));
        deliveryReceipt.setPurchaseOrder(PurchaseOrderMapper.toModel(entity.getPurchaseOrder()));

        List<DeliveredItem> deliveredItems = entity.getReceivedItems().stream()
                .map(deliveredItemEntity -> DeliveredItemMapper.toModel(deliveredItemEntity)).collect(Collectors.toList());

        deliveryReceipt.setReceivedItems(deliveredItems);
        deliveryReceipt.setDeliveryDate(entity.getDeliveryDate());
        deliveryReceipt.setDeliveryLocation(deliveryReceipt.getDeliveryLocation());
        deliveryReceipt.setReceivedBy(UserMapper.toDomain(entity.getReceivedBy()));
        deliveryReceipt.setDeliveryStatus(entity.getDeliveryStatus());
        deliveryReceipt.setDeliveryNotes(deliveryReceipt.getDeliveryNotes());

        return deliveryReceipt;
    }
}
