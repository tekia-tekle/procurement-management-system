package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition;

import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface PurchaseRequisitionRepository {
    PurchaseRequisition save(PurchaseRequisition requisition);

    PurchaseRequisition update(PurchaseRequisition requisition);
    Optional<PurchaseRequisition> findByRequisitionId(String requisitionId);
    void deleteByRequisitionId(String id);

    List<PurchaseRequisition> findAll();
    void deleteAll();
    Optional<PurchaseRequisition> findByRequisitionNumber(String requisitionNumber);

    List<PurchaseRequisition> findByRequisitionStatus(RequisitionStatus status);

    List<PurchaseRequisition> findByPriorityLevel(PriorityLevel priorityLevel);

    List<PurchaseRequisition> findByRequisitionDateBetween(LocalDate startDate, LocalDate endDate);

    List<PurchaseRequisition> findByRequestedByUserId(String userId);

    List<PurchaseRequisition> findByDepartmentEntityDepartmentId(String departmentId);

    List<PurchaseRequisition> findByCostCenterEntityCostCenterId(String costCenterId);

    List<PurchaseRequisition> findBySupplierEntitySupplierId(String supplierId);

    List<PurchaseRequisition> findByPurchaseOrderEntityOrderId(String orderId);

}