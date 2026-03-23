package com.bizeff.procurement.persistences.repositoryimplementation.purchaseorder;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.persistences.entity.purchaseorder.OrderedItemEntity;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.PurchaseRequisitionEntity;
import com.bizeff.procurement.persistences.jparepository.purchaseOrder.SpringDataPurchaseOrderRepository;
import com.bizeff.procurement.persistences.jparepository.purchaserequisition.SpringDataPurchaseRequisitionRepository;
import com.bizeff.procurement.persistences.mapper.purchaseorder.OrderedItemMapper;
import com.bizeff.procurement.persistences.mapper.purchaseorder.PurchaseOrderMapper;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.DepartmentMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.SupplierMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.purchaseorder.PurchaseOrderMapper.toModel;

@Repository
public class JpaPurchaseOrderRepository implements PurchaseOrderRepository {
    private SpringDataPurchaseOrderRepository springDataPurchaseOrderRepository;
    private SpringDataPurchaseRequisitionRepository springDataPurchaseRequisitionRepository;
    public JpaPurchaseOrderRepository(SpringDataPurchaseOrderRepository springDataPurchaseOrderRepository,
                                      SpringDataPurchaseRequisitionRepository springDataPurchaseRequisitionRepository) {
        this.springDataPurchaseOrderRepository = springDataPurchaseOrderRepository;
        this.springDataPurchaseRequisitionRepository = springDataPurchaseRequisitionRepository;
    }
    @Override
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        PurchaseOrderEntity purchaseOrderEntity = PurchaseOrderMapper.toEntity(purchaseOrder);

        if (purchaseOrder.getRequisitionList() != null) {
            List<PurchaseRequisitionEntity> managedRequisitions =
                    purchaseOrder.getRequisitionList().stream()
                            .map(req -> springDataPurchaseRequisitionRepository.findById(req.getId())
                                    .orElseThrow(() -> new IllegalArgumentException("Requisition not found: " + req.getId())))
                            .collect(Collectors.toList());
            purchaseOrderEntity.setPurchaseRequisitionEntities(managedRequisitions);
        }
        PurchaseOrderEntity savedPurchaseOrder = springDataPurchaseOrderRepository.save(purchaseOrderEntity);

        return toModel(savedPurchaseOrder);
    }

    @Override
    public Optional<PurchaseOrder> findByOrderId(String orderId) {
        return springDataPurchaseOrderRepository.findByOrderId(orderId).map(purchaseOrderEntity -> toModel(purchaseOrderEntity));
    }

    @Override
    public List<PurchaseOrder> findAll() {
        return springDataPurchaseOrderRepository.findAll().stream().map(purchaseOrderEntity -> toModel(purchaseOrderEntity)).collect(Collectors.toList());
    }

    @Override
    public void deleteByOrderId(String orderId) {
        springDataPurchaseOrderRepository.deleteByOrderId(orderId);
    }

    @Override
    public List<PurchaseOrder> findByDepartmentId(String departmentId) {
        return springDataPurchaseOrderRepository.findByDepartmentEntity_DepartmentId(departmentId).stream().map(purchaseOrderEntity -> toModel(purchaseOrderEntity)).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseOrder> findBySupplierId(String supplierId) {
        return springDataPurchaseOrderRepository.findBySupplierEntity_SupplierId(supplierId).stream().map(purchaseOrderEntity -> toModel(purchaseOrderEntity)).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseOrder> findByPurchaseOrderStatus(PurchaseOrderStatus status) {
        return springDataPurchaseOrderRepository.findByPurchaseOrderStatus(status).stream().map(purchaseOrderEntity -> toModel(purchaseOrderEntity)).collect(Collectors.toList());
    }
    @Override
    public List<PurchaseOrder> findByOrderDateBetween(LocalDate startDate, LocalDate endDate) {
        return springDataPurchaseOrderRepository.findByOrderDateBetween(startDate, endDate).stream().map(purchaseOrderEntity -> toModel(purchaseOrderEntity)).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseOrder> findByDeliveryDateBetween(LocalDate startDate, LocalDate endDate) {
        return springDataPurchaseOrderRepository.findByDeliveryDateBetween(startDate, endDate).stream().map(purchaseOrderEntity -> toModel(purchaseOrderEntity)).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseOrder> findByOrderDateBefore(LocalDate date) {
        return springDataPurchaseOrderRepository.findByOrderDateBefore(date).stream().map(purchaseOrderEntity -> toModel(purchaseOrderEntity)).collect(Collectors.toList());
    }

    @Override
    public List<PurchaseOrder> findByOrderDateAfter(LocalDate date) {
        return springDataPurchaseOrderRepository.findByOrderDateAfter(date).stream().map(purchaseOrderEntity -> toModel(purchaseOrderEntity)).collect(Collectors.toList());
    }
    @Override
    public void deleteAll(){
        springDataPurchaseOrderRepository.deleteAll();
    }

    @Override
    public PurchaseOrder update(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getOrderId() == null || purchaseOrder.getOrderId().isEmpty()) {
            throw new IllegalArgumentException("Order ID must not be null or empty for update.");
        }
        Optional<PurchaseOrderEntity> existingOrderOpt = springDataPurchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId());
        if (existingOrderOpt.isEmpty()) {
            throw new IllegalArgumentException("Purchase Order with ID " + purchaseOrder.getOrderId() + " does not exist.");
        }
        PurchaseOrderEntity existingOrder = existingOrderOpt.get();
        existingOrder.setDepartmentEntity(DepartmentMapper.toEntity(purchaseOrder.getDepartment()));

        if (purchaseOrder.getRequisitionList() != null) {
            List<PurchaseRequisitionEntity> managedRequisitions =
                    purchaseOrder.getRequisitionList().stream()
                            .map(req -> springDataPurchaseRequisitionRepository.findById(req.getId())
                                    .orElseThrow(() -> new IllegalArgumentException("Requisition not found: " + req.getId())))
                            .collect(Collectors.toList());
            existingOrder.setPurchaseRequisitionEntities(managedRequisitions);
        }

        List<OrderedItemEntity> orderedItemEntityList = purchaseOrder.getOrderedItems().stream()
                .map(orderedItem -> {OrderedItemEntity orderedItemEntity = OrderedItemMapper.toEntity(orderedItem, InventoryMapper.toEntity(orderedItem.getInventory()));
                    existingOrder.addOrderedItem(orderedItemEntity);
                    return orderedItemEntity;
                }).collect(Collectors.toList());

        existingOrder.setOrderedItems(orderedItemEntityList);
        existingOrder.setOrderDate(purchaseOrder.getOrderDate());
        existingOrder.setSupplierEntity(SupplierMapper.toEntity(purchaseOrder.getSupplier()));
        existingOrder.setShippingMethod(purchaseOrder.getShippingMethod());
        existingOrder.setDeliveryDate(purchaseOrder.getDeliveryDate());
        existingOrder.setPurchaseOrderStatus(purchaseOrder.getPurchaseOrderStatus());
        existingOrder.setApproved(purchaseOrder.isApproved());
        existingOrder.setShipped(purchaseOrder.isShipped());
        existingOrder.setLastUpdatedDate(purchaseOrder.getLastUpdatedDate());
        existingOrder.setTotalCost(purchaseOrder.getTotalCost());
        PurchaseOrderEntity updatedOrder = springDataPurchaseOrderRepository.save(existingOrder);
        return toModel(updatedOrder);
    }
}
