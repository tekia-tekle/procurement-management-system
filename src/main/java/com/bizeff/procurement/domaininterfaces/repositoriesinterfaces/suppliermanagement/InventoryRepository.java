package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement;

import com.bizeff.procurement.models.supplymanagement.Inventory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    Inventory save(Inventory inventory);
    Inventory update(Inventory inventory);
    Optional<Inventory> findByItemId(String itemId);
    Optional<Inventory> findByItemIdAndSupplierId(String itemId, Long supplierId);

    void deleteByItemId(String inventoryId);
    List<Inventory>findByExpiryDateIsAfter(LocalDate date);

    List<Inventory> findAll();
    void deleteAll();
}
