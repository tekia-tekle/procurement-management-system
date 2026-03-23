package com.bizeff.procurement.persistences.mapper.suppliermanagement;

import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;

public class InventoryMapper {

    public static InventoryEntity toEntity(Inventory model) {
        if (model == null) return null;

        InventoryEntity entity = new InventoryEntity();

        if (model.getId() != null) {
            entity.setId(model.getId()); // <- Set the DB ID so Hibernate knows it's managed
        }
        entity.setItemId(model.getItemId());
        entity.setItemName(model.getItemName());
        entity.setQuantityAvailable(model.getQuantityAvailable());
        entity.setUnitPrice(model.getUnitPrice());
        entity.setItemCategory(model.getItemCategory());
        entity.setExpiryDate(model.getExpiryDate());
        entity.setSpecification(model.getSpecification());

        return entity;
    }

    public static Inventory toModel(InventoryEntity entity) {
        if (entity == null) return null;

        Inventory model = new Inventory();
        model.setId(entity.getId()); // <- Carry over the ID
        model.setItemId(entity.getItemId());
        model.setItemName(entity.getItemName());
        model.setQuantityAvailable(entity.getQuantityAvailable());
        model.setUnitPrice(entity.getUnitPrice());
        model.setItemCategory(entity.getItemCategory());
        model.setExpiryDate(entity.getExpiryDate());
        model.setSpecification(entity.getSpecification());

        return model;
    }
}
