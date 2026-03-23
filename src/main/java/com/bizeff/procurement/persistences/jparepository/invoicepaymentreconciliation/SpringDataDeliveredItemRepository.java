package com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation;

import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveredItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataDeliveredItemRepository extends JpaRepository<DeliveredItemEntity, Long> {
    Optional<DeliveredItemEntity>findByInventory_ItemId(String itemId);
    void deleteByInventory_ItemId(String itemId);

}
