package com.bizeff.procurement.persistences.jparepository.purchaserequisition;

import com.bizeff.procurement.persistences.entity.purchaserequisition.RequestedItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface SpringDataRequestedItemRepository extends JpaRepository<RequestedItemEntity,Long> {
    Optional<RequestedItemEntity> findByInventoryItemId(String itemId);

    void deleteByInventoryItemId(String itemId);

}
