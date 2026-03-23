package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation;

import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveredItem;

import java.util.List;
import java.util.Optional;

public interface DeliveredItemRepository {
    DeliveredItem save(DeliveredItem deliveredItem);
    Optional<DeliveredItem> findByItemId(String itemId);
    void deleteByItemId(String itemId);
    List<DeliveredItem> findAll();
    void deleteAll();
}
