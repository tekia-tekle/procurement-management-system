package com.bizeff.procurement.persistences.mapper.purchaseorder;

import com.bizeff.procurement.models.purchaseorder.OrderedItem;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.persistences.entity.purchaseorder.OrderedItemEntity;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.PurchaseRequisitionEntity;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.DepartmentMapper;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.PurchaseRequisitionMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.SupplierMapper;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseOrderMapper {

    public static PurchaseOrderEntity toEntity(PurchaseOrder purchaseOrder) {
        if (purchaseOrder ==null) return null;

        PurchaseOrderEntity purchaseOrderEntity = new PurchaseOrderEntity();
        if (purchaseOrder.getId() != null){
            purchaseOrderEntity.setId(purchaseOrder.getId());
        }
        purchaseOrderEntity.setOrderId(purchaseOrder.getOrderId());
        purchaseOrderEntity.setDepartmentEntity(DepartmentMapper.toEntity(purchaseOrder.getDepartment()));
        List<PurchaseRequisitionEntity> requisitionEntityList = purchaseOrder.getRequisitionList().stream()
                .map(requisition->{PurchaseRequisitionEntity requisitionEntity = PurchaseRequisitionMapper.toEntity(requisition);
                    purchaseOrderEntity.addPurchaseRequisition(requisitionEntity);
                    return requisitionEntity;
                }).collect(Collectors.toList());

        List<OrderedItemEntity> orderedItemEntityList = purchaseOrder.getOrderedItems().stream()
                        .map(orderedItem -> {OrderedItemEntity orderedItemEntity = OrderedItemMapper.toEntity(orderedItem, InventoryMapper.toEntity(orderedItem.getInventory()));
                            purchaseOrderEntity.addOrderedItem(orderedItemEntity);
                            return orderedItemEntity;
                        }).collect(Collectors.toList());

        purchaseOrderEntity.setOrderedItems(orderedItemEntityList);
        purchaseOrderEntity.setPurchaseRequisitionEntities(requisitionEntityList);
        purchaseOrderEntity.setOrderDate(purchaseOrder.getOrderDate());
        purchaseOrderEntity.setSupplierEntity(SupplierMapper.toEntity(purchaseOrder.getSupplier()));
        purchaseOrderEntity.setShippingMethod(purchaseOrder.getShippingMethod());
        purchaseOrderEntity.setDeliveryDate(purchaseOrder.getDeliveryDate());
        purchaseOrderEntity.setPurchaseOrderStatus(purchaseOrder.getPurchaseOrderStatus());
        purchaseOrderEntity.setApproved(purchaseOrder.isApproved());
        purchaseOrderEntity.setShipped(purchaseOrder.isShipped());
        purchaseOrderEntity.setLastUpdatedDate(purchaseOrder.getLastUpdatedDate());
        purchaseOrderEntity.getTotalCost();
        return purchaseOrderEntity;
    }

    public static PurchaseOrder toModel(PurchaseOrderEntity purchaseOrderEntity) {
        if (purchaseOrderEntity == null) {
            return null;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(purchaseOrderEntity.getId());
        purchaseOrder.setOrderId(purchaseOrderEntity.getOrderId());
        purchaseOrder.setDepartment(DepartmentMapper.toDomain(purchaseOrderEntity.getDepartmentEntity()));
        List<PurchaseRequisition> requisitionList = purchaseOrderEntity.getPurchaseRequisitionEntities().stream()
                .map(PurchaseRequisitionMapper::toModel).collect(Collectors.toList());

        List<OrderedItem> orderedItemList = purchaseOrderEntity.getOrderedItems().stream()
                        .map(OrderedItemMapper::toModel).collect(Collectors.toList());
        purchaseOrder.setOrderedItems(orderedItemList);
        purchaseOrder.setRequisitionList(requisitionList);
        purchaseOrder.setOrderDate(purchaseOrderEntity.getOrderDate());
        purchaseOrder.setSupplier(SupplierMapper.toModel(purchaseOrderEntity.getSupplierEntity()));
        purchaseOrder.setShippingMethod(purchaseOrderEntity.getShippingMethod());
        purchaseOrder.setDeliveryDate(purchaseOrderEntity.getDeliveryDate());
        purchaseOrder.setPurchaseOrderStatus(purchaseOrderEntity.getPurchaseOrderStatus());
        purchaseOrder.setApproved(purchaseOrderEntity.isApproved());
        purchaseOrder.setShipped(purchaseOrderEntity.isShipped());
        purchaseOrder.setLastUpdatedDate(purchaseOrderEntity.getLastUpdatedDate());
        purchaseOrder.getTotalCost();
        return purchaseOrder;
    }
}
