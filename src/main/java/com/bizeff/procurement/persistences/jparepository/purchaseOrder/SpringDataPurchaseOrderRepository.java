package com.bizeff.procurement.persistences.jparepository.purchaseOrder;


import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataPurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, Long> {
    Optional<PurchaseOrderEntity>findByOrderId(String orderId);
    void deleteByOrderId(String orderId);
    List<PurchaseOrderEntity> findByDepartmentEntity_DepartmentId(String departmentId);

    List<PurchaseOrderEntity> findBySupplierEntity_SupplierId(String supplierId);

    List<PurchaseOrderEntity> findByPurchaseOrderStatus(PurchaseOrderStatus status);

    List<PurchaseOrderEntity> findByIsApproved(boolean isApproved);

    List<PurchaseOrderEntity> findByIsShipped(boolean isShipped);

    List<PurchaseOrderEntity> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    List<PurchaseOrderEntity> findByDeliveryDateBetween(LocalDate startDate, LocalDate endDate);

    List<PurchaseOrderEntity> findByOrderDateBefore(LocalDate date);

    List<PurchaseOrderEntity> findByOrderDateAfter(LocalDate date);
}
