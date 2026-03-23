package com.bizeff.procurement.persistences.jparepository.suppliermanagement;

import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataInventoryRepository extends JpaRepository<InventoryEntity,Long> {
    Optional<InventoryEntity>findByItemId(String itemId);
    Optional<InventoryEntity>findByItemIdAndSupplierId(String itemId, Long supplierId);
    void deleteByItemId(String itemId);
    List<InventoryEntity>findByExpiryDateIsAfter(LocalDate date);

}
