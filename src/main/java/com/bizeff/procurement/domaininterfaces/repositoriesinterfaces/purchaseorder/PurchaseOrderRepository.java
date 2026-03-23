package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder;

import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PurchaseOrderRepository {
    PurchaseOrder save(PurchaseOrder purchaseOrder);
    PurchaseOrder update(PurchaseOrder purchaseOrder);
    Optional<PurchaseOrder> findByOrderId(String orderId);
    List<PurchaseOrder>findAll();

    void deleteByOrderId(String orderId);
    List<PurchaseOrder> findByDepartmentId(String departmentId);

    List<PurchaseOrder> findBySupplierId(String supplierId);

    List<PurchaseOrder> findByPurchaseOrderStatus(PurchaseOrderStatus status);

    List<PurchaseOrder> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    List<PurchaseOrder> findByDeliveryDateBetween(LocalDate startDate, LocalDate endDate);

    List<PurchaseOrder> findByOrderDateBefore(LocalDate date);

    List<PurchaseOrder> findByOrderDateAfter(LocalDate date);
    void deleteAll();
}
