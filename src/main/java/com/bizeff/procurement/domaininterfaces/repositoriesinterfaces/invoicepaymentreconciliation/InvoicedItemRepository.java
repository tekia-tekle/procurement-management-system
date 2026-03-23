package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation;

import com.bizeff.procurement.models.invoicepaymentreconciliation.InvoicedItem;

import java.util.List;
import java.util.Optional;

public interface InvoicedItemRepository {
    InvoicedItem save(InvoicedItem invoicedItem);
    Optional<InvoicedItem> findByItemId(String itemId);
    void deleteByItemId(String itemId);
    List<InvoicedItem> findAll();
    void deleteAll();

}
