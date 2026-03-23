package com.bizeff.procurement.persistences.repositoryimplementation.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.SupplierEntity;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataSupplierManagementRepository;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.SupplierMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.suppliermanagement.SupplierMapper.toModel;

@Repository
public class JpaSupplierManagementRepository implements SupplierRepository {
    private SpringDataSupplierManagementRepository springDataSupplierManagementRepository;
    public JpaSupplierManagementRepository(SpringDataSupplierManagementRepository springDataSupplierManagementRepository){
        this.springDataSupplierManagementRepository = springDataSupplierManagementRepository;
    }
    @Override
    public Supplier save(Supplier supplier) {
        SupplierEntity supplierEntity = SupplierMapper.toEntity(supplier);

        SupplierEntity savedSupplier = springDataSupplierManagementRepository.save(supplierEntity);

        return toModel(savedSupplier);
    }

    @Override
    public Supplier update(Supplier supplier) {
        if (supplier.getSupplierId() == null || supplier.getSupplierId().trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier Id can't be null or empty.");
        }
        Optional<SupplierEntity> existingSupplierOpt = springDataSupplierManagementRepository.findBySupplierId(supplier.getSupplierId());
        if (!existingSupplierOpt.isPresent()) {
            throw new IllegalArgumentException("Supplier with ID " + supplier.getSupplierId() + " does not exist.");
        }
        SupplierEntity existingSupplier = existingSupplierOpt.get();
        existingSupplier.setSupplierName(supplier.getSupplierName());
        existingSupplier.setSupplierCategory(supplier.getSupplierCategory());
        existingSupplier.setTaxIdentificationNumber(supplier.getTaxIdentificationNumber());
        existingSupplier.setRegistrationNumber(supplier.getRegistrationNumber());
        existingSupplier.setSupplierContactDetail(SupplierMapper.toEntity(supplier.getSupplierContactDetail()));
        existingSupplier.setPaymentMethodEntities(supplier.getSupplierPaymentMethods().stream().map(SupplierMapper::toEntity).collect(Collectors.toList()));
        List<InventoryEntity> inventoryEntities = supplier.getExistedItems().stream()
                        .map(inventory -> {
                            InventoryEntity inventoryEntity = InventoryMapper.toEntity(inventory);
                            existingSupplier.addInventory(inventoryEntity); // Set the supplier reference
                            return inventoryEntity;
                        }).collect(Collectors.toList());
        existingSupplier.setInventoryItems(inventoryEntities);
        existingSupplier.setActive(supplier.isActive());
        existingSupplier.setRegistrationDate(supplier.getRegistrationDate());
        existingSupplier.setLastUpdated(supplier.getLastUpdated());
        SupplierEntity updatedSupplier = springDataSupplierManagementRepository.save(existingSupplier);
        return SupplierMapper.toModel(updatedSupplier);

    }
    @Override
    public Optional<Supplier> findBySupplierId(String supplierId) {
        return springDataSupplierManagementRepository.findBySupplierId(supplierId).map(SupplierMapper::toModel);
    }
    @Override
    public Optional<Supplier> findByEmail(String email){
        return springDataSupplierManagementRepository.findBySupplierContactDetailEmail(email).map(SupplierMapper::toModel);
    }
    @Override
    public Optional<Supplier> findByPhoneNumber(String phone){
        return springDataSupplierManagementRepository.findBySupplierContactDetailPhoneNumber(phone).map(SupplierMapper::toModel);
    }
    @Override
    public List<Supplier> findAll() {
        return springDataSupplierManagementRepository.findAll().stream().map(SupplierMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public void deleteBySupplierId(String supplierId) {
        springDataSupplierManagementRepository.deleteBySupplierId(supplierId);
    }

    @Override
    public List<Supplier> findBySupplierCategory(String category) {
        return springDataSupplierManagementRepository.findBySupplierCategory(category).stream().map(supplierEntity -> toModel(supplierEntity)).collect(Collectors.toList());
    }

    @Override
    public List<Supplier> findByIsActiveTrue() {
        return springDataSupplierManagementRepository.findByIsActiveTrue().stream().map(supplierEntity -> toModel(supplierEntity)).collect(Collectors.toList());
    }
    @Override
    public List<Supplier> findByRegistrationDateAfter(LocalDate date) {
        return springDataSupplierManagementRepository.findByRegistrationDateAfter(date).stream().map(supplierEntity -> toModel(supplierEntity)).collect(Collectors.toList());
    }
    @Override
    public List<Supplier> findByRegistrationDateBefore(LocalDate date) {
        return springDataSupplierManagementRepository.findByRegistrationDateBefore(date).stream().map(supplierEntity -> toModel(supplierEntity)).collect(Collectors.toList());
    }

    @Override
    public Optional<Supplier> findByTaxIdentificationNumber(String taxId) {
        return springDataSupplierManagementRepository.findByTaxIdentificationNumber(taxId).map(supplierEntity -> toModel(supplierEntity));
    }

    @Override
    public Optional<Supplier> findByRegistrationNumber(String regNumber) {
        return springDataSupplierManagementRepository.findByRegistrationNumber(regNumber).map(supplierEntity -> toModel(supplierEntity));
    }
    @Override
    public void deleteAll(){
        springDataSupplierManagementRepository.deleteAll();
    }
}
