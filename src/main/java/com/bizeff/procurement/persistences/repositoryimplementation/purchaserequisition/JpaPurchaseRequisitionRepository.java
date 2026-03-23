package com.bizeff.procurement.persistences.repositoryimplementation.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.persistences.entity.purchaserequisition.PurchaseRequisitionEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.RequestedItemEntity;
import com.bizeff.procurement.persistences.jparepository.purchaserequisition.SpringDataPurchaseRequisitionRepository;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.*;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.SupplierMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.purchaserequisition.PurchaseRequisitionMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.purchaserequisition.PurchaseRequisitionMapper.toModel;

@Repository
public class JpaPurchaseRequisitionRepository implements PurchaseRequisitionRepository {
    private SpringDataPurchaseRequisitionRepository springDataPurchaseRequisitionRepository;
    public JpaPurchaseRequisitionRepository(SpringDataPurchaseRequisitionRepository springDataPurchaseRequisitionRepository) {
        this.springDataPurchaseRequisitionRepository = springDataPurchaseRequisitionRepository;
    }
    @Override
    public PurchaseRequisition save(PurchaseRequisition requisition) {
        PurchaseRequisitionEntity entity = toEntity(requisition);
        PurchaseRequisitionEntity savedEntity = springDataPurchaseRequisitionRepository.save(entity);
        return toModel(savedEntity);
    }



    @Override
    public Optional<PurchaseRequisition> findByRequisitionId(String requisitionId) {
        return springDataPurchaseRequisitionRepository.findByRequisitionId(requisitionId).map(entity -> toModel(entity));  // Explicit lambda to ensure correct type inference
    }

    @Override
    public void deleteByRequisitionId(String requisitionId) {
            springDataPurchaseRequisitionRepository.deleteByRequisitionId(requisitionId);
    }

    @Override
    public List<PurchaseRequisition> findAll() {
        return springDataPurchaseRequisitionRepository.findAll()
                .stream()
                .map(PurchaseRequisitionMapper::toModel)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteAll(){
        springDataPurchaseRequisitionRepository.deleteAll();
    }

    @Override
    public Optional<PurchaseRequisition> findByRequisitionNumber(String requisitionNumber) {
        return springDataPurchaseRequisitionRepository.findByRequisitionNumber(requisitionNumber).map(purchaseRequisitionEntity -> toModel(purchaseRequisitionEntity));
    }

    @Override
    public List<PurchaseRequisition> findByRequisitionStatus(RequisitionStatus status) {
        return springDataPurchaseRequisitionRepository.findByRequisitionStatus(status).stream()
                .map(purchaseRequisitionEntity -> toModel(purchaseRequisitionEntity)).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseRequisition> findByPriorityLevel(PriorityLevel priorityLevel) {
        return springDataPurchaseRequisitionRepository.findByPriorityLevel(priorityLevel).stream()
                .map(purchaseRequisitionEntity -> toModel(purchaseRequisitionEntity)).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseRequisition> findByRequisitionDateBetween(LocalDate startDate, LocalDate endDate) {
        return springDataPurchaseRequisitionRepository.findByRequisitionDateBetween(startDate,endDate).stream()
                .map(purchaseRequisitionEntity ->toModel(purchaseRequisitionEntity)).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseRequisition> findByRequestedByUserId(String userId) {
        return springDataPurchaseRequisitionRepository.findByRequestedBy_UserId(userId).stream()
                .map(purchaseRequisitionEntity -> toModel(purchaseRequisitionEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseRequisition> findByDepartmentEntityDepartmentId(String departmentId) {
        return springDataPurchaseRequisitionRepository.findByDepartmentEntity_DepartmentId(departmentId).stream()
                .map(purchaseRequisitionEntity -> toModel(purchaseRequisitionEntity)).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseRequisition> findByCostCenterEntityCostCenterId(String costCenterId) {
        return springDataPurchaseRequisitionRepository.findByCostCenterEntityCostCenterId(costCenterId).stream()
                .map(purchaseRequisitionEntity -> toModel(purchaseRequisitionEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseRequisition> findBySupplierEntitySupplierId(String supplierId) {
        return springDataPurchaseRequisitionRepository.findBySupplierEntitySupplierId(supplierId).stream()
                .map(purchaseRequisitionEntity -> toModel(purchaseRequisitionEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseRequisition> findByPurchaseOrderEntityOrderId(String orderId) {
        return springDataPurchaseRequisitionRepository.findByPurchaseOrderEntityOrderId(orderId).stream()
                .map(purchaseRequisitionEntity -> toModel(purchaseRequisitionEntity))
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseRequisition update(PurchaseRequisition requisition) {
        if (requisition.getRequisitionId() == null || requisition.getRequisitionId().isEmpty()) {
            throw new IllegalArgumentException("Requisition ID must not be null or empty.");
        }
        Optional<PurchaseRequisitionEntity> existingPurchaseRequisition = springDataPurchaseRequisitionRepository.findByRequisitionId(requisition.getRequisitionId());

        if (existingPurchaseRequisition.isEmpty()) {
            throw new IllegalArgumentException("Requisition with ID " + requisition.getRequisitionId() + " does not exist.");
        }
        PurchaseRequisitionEntity existingEntity = existingPurchaseRequisition.get();

        // Update fields as necessary
        existingEntity.setRequisitionId(requisition.getRequisitionId());
        existingEntity.setRequisitionNumber(requisition.getRequisitionNumber());
        existingEntity.setRequisitionDate(requisition.getRequisitionDate());

        existingEntity.setDepartmentEntity(DepartmentMapper.toEntity(requisition.getDepartment()));
        existingEntity.setRequestedBy(UserMapper.toEntity(requisition.getRequestedBy()));
        existingEntity.setCostCenterEntity(CostCenterMapper.toEntity(requisition.getCostCenter()));

        existingEntity.setPriorityLevel(requisition.getPriorityLevel());
        existingEntity.setExpectedDeliveryDate(requisition.getExpectedDeliveryDate());
        existingEntity.setJustification(requisition.getJustification());

        existingEntity.setRequisitionStatus(requisition.getRequisitionStatus());
        existingEntity.setUpdatedDate(requisition.getUpdatedDate());

        List<RequestedItemEntity> requestedItemEntities = requisition.getItems().stream()
                .map(requestedItem -> {
                    RequestedItemEntity requestedItemEntity = RequestedItemMapper.toEntity(
                            requestedItem,
                            InventoryMapper.toEntity(requestedItem.getInventory())
                    );
                    requestedItemEntity.setPurchaseRequisition(existingEntity); // 🔥 Set the owning side
                    return requestedItemEntity;
                })
                .collect(Collectors.toList());

        existingEntity.setItems(requestedItemEntities);
        if (requisition.getSupplier() != null){
            existingEntity.setSupplierEntity(SupplierMapper.toEntity(requisition.getSupplier()));
        }

        PurchaseRequisitionEntity updatedEntity = springDataPurchaseRequisitionRepository.save(existingEntity);

        return toModel(updatedEntity);
    }

}