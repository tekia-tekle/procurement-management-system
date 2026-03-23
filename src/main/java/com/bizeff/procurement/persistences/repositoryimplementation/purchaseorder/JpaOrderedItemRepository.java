package com.bizeff.procurement.persistences.repositoryimplementation.purchaseorder;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.OrderedItemRepository;
import com.bizeff.procurement.models.purchaseorder.OrderedItem;
import com.bizeff.procurement.persistences.entity.purchaseorder.OrderedItemEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.jparepository.purchaseOrder.SpringDataOrderedItemRepository;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataInventoryRepository;
import com.bizeff.procurement.persistences.mapper.purchaseorder.OrderedItemMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.purchaseorder.OrderedItemMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.purchaseorder.OrderedItemMapper.toModel;

public class JpaOrderedItemRepository implements OrderedItemRepository {
    private SpringDataOrderedItemRepository springDataOrderedItemRepository;
    private SpringDataInventoryRepository springDataInventoryRepository;
    public JpaOrderedItemRepository(SpringDataOrderedItemRepository springDataOrderedItemRepository,
                                    SpringDataInventoryRepository springDataInventoryRepository){
        this.springDataOrderedItemRepository = springDataOrderedItemRepository;
        this.springDataInventoryRepository = springDataInventoryRepository;
    }
    @Override
    public OrderedItem save(OrderedItem orderedItem) {
        InventoryEntity savedInventory = springDataInventoryRepository.findByItemId(orderedItem.getInventory().getItemId()).orElseThrow(()->new IllegalArgumentException("there is no item in the inventory with item id:"+orderedItem.getInventory().getItemId()));
        OrderedItemEntity orderedItemEntity = toEntity(orderedItem,savedInventory);
        OrderedItemEntity savedOrderedItem = springDataOrderedItemRepository.save(orderedItemEntity);

        return toModel(savedOrderedItem);
    }

    @Override
    public Optional<OrderedItem> findByItemId(String itemId) {
        return springDataOrderedItemRepository.findByItemId(itemId).map(OrderedItemMapper::toModel);
    }

    @Override
    public void deleteByItemId(String itemId) {
        springDataOrderedItemRepository.deleteByItemId(itemId);
    }

    @Override
    public List<OrderedItem> findAll() {
        return springDataOrderedItemRepository.findAll().stream()
                .map(OrderedItemMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataOrderedItemRepository.deleteAll();
    }
}
