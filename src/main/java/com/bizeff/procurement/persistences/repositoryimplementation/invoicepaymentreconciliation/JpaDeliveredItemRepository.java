package com.bizeff.procurement.persistences.repositoryimplementation.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.DeliveredItemRepository;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveredItem;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveredItemEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation.SpringDataDeliveredItemRepository;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataInventoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.DeliveredItemMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.DeliveredItemMapper.toModel;

@Repository
public class JpaDeliveredItemRepository implements DeliveredItemRepository {
    private SpringDataDeliveredItemRepository springDataDeliveredItemRepository;
    private SpringDataInventoryRepository springDataInventoryRepository;
    public JpaDeliveredItemRepository(SpringDataDeliveredItemRepository springDataDeliveredItemRepository,
                                      SpringDataInventoryRepository springDataInventoryRepository){
        this.springDataDeliveredItemRepository = springDataDeliveredItemRepository;
        this.springDataInventoryRepository = springDataInventoryRepository;
    }
    @Override
    public DeliveredItem save(DeliveredItem deliveredItem) {
        InventoryEntity inventoryEntity = springDataInventoryRepository.findByItemId(deliveredItem.getInventory().getItemId()).orElseThrow(()-> new IllegalArgumentException("item doesn't existed in the inventory"));
        DeliveredItemEntity entity = toEntity(deliveredItem,inventoryEntity);
        DeliveredItemEntity savedDeliveryItem = springDataDeliveredItemRepository.save(entity);

        return toModel(savedDeliveryItem);
    }

    @Override
    public Optional<DeliveredItem> findByItemId(String itemId) {
        return springDataDeliveredItemRepository.findByInventory_ItemId(itemId).map(deliveredItemEntity -> toModel(deliveredItemEntity));
    }

    @Override
    public void deleteByItemId(String itemId) {
        springDataDeliveredItemRepository.deleteByInventory_ItemId(itemId);
    }

    @Override
    public List<DeliveredItem> findAll() {
        return springDataDeliveredItemRepository.findAll().stream()
                .map(deliveredItemEntity -> toModel(deliveredItemEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataDeliveredItemRepository.deleteAll();
    }
}
