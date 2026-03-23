package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition;

import com.bizeff.procurement.models.purchaserequisition.RequestedItem;

import java.util.List;
import java.util.Optional;

public interface RequestedItemRepository {
    RequestedItem save(RequestedItem requestedItem);
    RequestedItem update(RequestedItem requestedItem);
    Optional<RequestedItem> findById(Long id);
    Optional<RequestedItem>findByInventoryItemId(String itemId);
    List<RequestedItem> findAll();
    void deleteById(Long id);
    void deleteByInventoryItemId(String itemId);
    void deleteAll();
}
