package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder;

import com.bizeff.procurement.models.purchaseorder.OrderedItem;

import java.util.List;
import java.util.Optional;

public interface OrderedItemRepository {
    OrderedItem save(OrderedItem orderedItem);
    Optional<OrderedItem> findByItemId(String itemId);
    void deleteByItemId(String itemId);
    List<OrderedItem> findAll();
    void deleteAll();
}
