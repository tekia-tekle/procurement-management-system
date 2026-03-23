package com.bizeff.procurement.persistences.jparepository.purchaseOrder;

import com.bizeff.procurement.persistences.entity.purchaseorder.OrderedItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataOrderedItemRepository extends JpaRepository<OrderedItemEntity,Long> {
    Optional<OrderedItemEntity> findByItemId(String itemId);
    void deleteByItemId(String itemId);
}
