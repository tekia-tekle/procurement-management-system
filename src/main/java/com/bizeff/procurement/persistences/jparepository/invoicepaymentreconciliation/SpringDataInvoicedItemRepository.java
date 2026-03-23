package com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation;

import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoicedItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataInvoicedItemRepository extends JpaRepository<InvoicedItemEntity, Long> {
    Optional<InvoicedItemEntity> findByInventory_ItemId(String itemId);
    void deleteByInventory_ItemId(String itemId);
}
