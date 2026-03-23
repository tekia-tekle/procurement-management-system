package com.bizeff.procurement.persistences.repositoryimplementation.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.InventoryRepository;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataInventoryRepository;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper.toModel;

public class JpaInventoryRepository implements InventoryRepository {
    private SpringDataInventoryRepository springDataInventoryRepository;
    public JpaInventoryRepository(SpringDataInventoryRepository springDataInventoryRepository){
        this.springDataInventoryRepository = springDataInventoryRepository;
    }
    @Override
    public Inventory save(Inventory inventory) {
        InventoryEntity entity = toEntity(inventory);
        InventoryEntity savedEntity = springDataInventoryRepository.save(entity);
        return toModel(savedEntity);
    }

    @Override
    public Optional<Inventory> findByItemId(String inventoryId) {
        return springDataInventoryRepository.findByItemId(inventoryId).map(entity-> toModel(entity));
    }

    @Override
    public Optional<Inventory> findByItemIdAndSupplierId(String itemId, Long supplierId) {
        return springDataInventoryRepository.findByItemIdAndSupplierId(itemId, supplierId).map(entity -> toModel(entity));
    }
    @Override
    public void deleteByItemId(String inventoryId) {
        springDataInventoryRepository.deleteByItemId(inventoryId);
    }

    @Override
    public List<Inventory> findByExpiryDateIsAfter(LocalDate date) {
        return springDataInventoryRepository.findByExpiryDateIsAfter(date).stream().map(inventoryEntity -> InventoryMapper.toModel(inventoryEntity)).collect(Collectors.toList());
    }

    @Override
    public List<Inventory> findAll() {
        return springDataInventoryRepository.findAll().stream().map(InventoryMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataInventoryRepository.deleteAll();
    }

    @Override
    public Inventory update(Inventory inventory) {
        if (inventory.getItemId() == null || inventory.getItemId().isEmpty()){
            throw new IllegalArgumentException("Inventory ID cannot be empty.");
        }
        Optional<InventoryEntity> existingInventoryOpt = springDataInventoryRepository.findByItemId(inventory.getItemId());
        if (existingInventoryOpt.isEmpty()){
            throw new IllegalArgumentException("Inventory with ID " + inventory.getItemId() + " does not exist.");
        }
        InventoryEntity existingInventory = existingInventoryOpt.get();
        existingInventory.setItemName(inventory.getItemName());
        existingInventory.setQuantityAvailable(inventory.getQuantityAvailable());
        existingInventory.setUnitPrice(inventory.getUnitPrice());
        existingInventory.setItemCategory(inventory.getItemCategory());
        existingInventory.setExpiryDate(inventory.getExpiryDate());
        existingInventory.setSpecification(inventory.getSpecification());
        return toModel(springDataInventoryRepository.save(existingInventory));
    }

}
