package com.bizeff.procurement.persistences.repositoryimplementation.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.InvoicedItemRepository;
import com.bizeff.procurement.models.invoicepaymentreconciliation.InvoicedItem;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoicedItemEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation.SpringDataInvoicedItemRepository;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataInventoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.InvoicedItemMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.InvoicedItemMapper.toModel;

@Repository
public class JpaInvoicedItemRepository implements InvoicedItemRepository {
    private SpringDataInvoicedItemRepository springDataInvoicedItemRepository;
    private SpringDataInventoryRepository springDataInventoryRepository;
    public JpaInvoicedItemRepository(SpringDataInvoicedItemRepository springDataInvoicedItemRepository,
                                     SpringDataInventoryRepository springDataInventoryRepository){
        this.springDataInvoicedItemRepository = springDataInvoicedItemRepository;
        this.springDataInventoryRepository = springDataInventoryRepository;
    }
    @Override
    public InvoicedItem save(InvoicedItem invoicedItem) {
        InventoryEntity inventoryEntity = springDataInventoryRepository.findByItemId(invoicedItem.getInventory().getItemId()).orElseThrow(()->new IllegalArgumentException("there is no inventory saved before."));
        InvoicedItemEntity entity = toEntity(invoicedItem,inventoryEntity);
        InvoicedItemEntity invoicedItemEntity = springDataInvoicedItemRepository.save(entity);
        return toModel(invoicedItemEntity);
    }

    @Override
    public Optional<InvoicedItem> findByItemId(String itemId) {
        return springDataInvoicedItemRepository.findByInventory_ItemId(itemId).map(invoicedItemEntity -> toModel(invoicedItemEntity));
    }

    @Override
    public void deleteByItemId(String itemId) {
        springDataInvoicedItemRepository.deleteByInventory_ItemId(itemId);
    }

    @Override
    public List<InvoicedItem> findAll() {
        return springDataInvoicedItemRepository.findAll().stream()
                .map(invoicedItemEntity -> toModel(invoicedItemEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataInvoicedItemRepository.deleteAll();
    }
}
