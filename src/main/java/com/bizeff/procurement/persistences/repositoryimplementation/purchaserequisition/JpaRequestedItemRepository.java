package com.bizeff.procurement.persistences.repositoryimplementation.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.RequestedItemRepository;
import com.bizeff.procurement.models.purchaserequisition.RequestedItem;
import com.bizeff.procurement.persistences.entity.purchaserequisition.RequestedItemEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.jparepository.purchaserequisition.SpringDataRequestedItemRepository;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataInventoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.purchaserequisition.RequestedItemMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.purchaserequisition.RequestedItemMapper.toModel;

@Repository
public class JpaRequestedItemRepository implements RequestedItemRepository {
    private SpringDataRequestedItemRepository springDataRequestedItemRepository;
    private SpringDataInventoryRepository springDataInventoryRepository;

    public JpaRequestedItemRepository(SpringDataRequestedItemRepository springDataRequestedItemRepository,
                                      SpringDataInventoryRepository springDataInventoryRepository){
        this.springDataRequestedItemRepository = springDataRequestedItemRepository;
        this.springDataInventoryRepository = springDataInventoryRepository;
    }
    @Override
    public RequestedItem save(RequestedItem requestedItem) {
        InventoryEntity inventoryEntity = springDataInventoryRepository.findByItemId(requestedItem.getInventory().getItemId()).orElseThrow();
        RequestedItemEntity requestedItemEntity = toEntity(requestedItem,inventoryEntity);
        RequestedItemEntity savedRequestedItemEntity = springDataRequestedItemRepository.save(requestedItemEntity);
        return toModel(savedRequestedItemEntity);
    }

    @Override
    public Optional<RequestedItem> findById(Long id) {
        return springDataRequestedItemRepository.findById(id).map(requestedItemEntity -> toModel(requestedItemEntity));
    }

    @Override
    public Optional<RequestedItem> findByInventoryItemId(String itemId) {
        return springDataRequestedItemRepository.findByInventoryItemId(itemId).map(requestedItemEntity -> toModel(requestedItemEntity));
    }

    @Override
    public List<RequestedItem> findAll() {
        return springDataRequestedItemRepository.findAll().stream().map(requestedItemEntity -> toModel(requestedItemEntity)).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataRequestedItemRepository.deleteById(id);
    }

    @Override
    public void deleteByInventoryItemId(String itemId) {
        springDataRequestedItemRepository.deleteByInventoryItemId(itemId);
    }
    @Override
    public void deleteAll(){
        springDataRequestedItemRepository.deleteAll();
    }

    @Override
    public RequestedItem update(RequestedItem requestedItem) {

        if (requestedItem == null || requestedItem.getInventory().getItemId() == null) {
            throw new IllegalArgumentException("Requested Item or ID cannot be null");
        }
        Optional<RequestedItemEntity> existingRequestedItemOpt = springDataRequestedItemRepository.findByInventoryItemId(requestedItem.getInventory().getItemId());
        if (existingRequestedItemOpt.isPresent()) {
            RequestedItemEntity existingRequestedItem = existingRequestedItemOpt.get();
            // Assuming the inventory is not being changed, just re-fetch it to ensure it's up-to-date
            existingRequestedItem.setInventory(existingRequestedItem.getInventory());

            // Update fields as necessary
            existingRequestedItem.setRequestedQuantity(requestedItem.getRequestedQuantity());
            existingRequestedItem.setTotalPrice(requestedItem.getTotalPrice());
            // Save the updated entity
            RequestedItemEntity updatedEntity = springDataRequestedItemRepository.save(existingRequestedItem);
            return toModel(updatedEntity);
        } else {
            throw new IllegalArgumentException("Requested Item with ID " + requestedItem.getInventory().getItemId() + " does not exist");
        }
    }
}
