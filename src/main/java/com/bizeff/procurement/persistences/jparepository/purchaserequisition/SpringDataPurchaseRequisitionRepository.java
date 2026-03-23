package com.bizeff.procurement.persistences.jparepository.purchaserequisition;

import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.persistences.entity.purchaserequisition.PurchaseRequisitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface SpringDataPurchaseRequisitionRepository extends JpaRepository<PurchaseRequisitionEntity,Long> {
    Optional<PurchaseRequisitionEntity>findByRequisitionId(String requisitionId);
    Optional<PurchaseRequisitionEntity> findByRequisitionNumber(String requisitionNumber);
    List<PurchaseRequisitionEntity> findByRequisitionStatus(RequisitionStatus status);
    List<PurchaseRequisitionEntity> findByPriorityLevel(PriorityLevel priorityLevel);
    List<PurchaseRequisitionEntity> findByRequisitionDateBetween(LocalDate startDate, LocalDate endDate);
    List<PurchaseRequisitionEntity> findByRequestedBy_UserId(String userId);
    List<PurchaseRequisitionEntity> findByDepartmentEntity_DepartmentId(String departmentId);
    List<PurchaseRequisitionEntity> findByCostCenterEntityCostCenterId(String costCenterId);
    List<PurchaseRequisitionEntity> findBySupplierEntitySupplierId(String supplierId);
    List<PurchaseRequisitionEntity> findByPurchaseOrderEntityOrderId(String orderId);
    void deleteByRequisitionId(String requisitionId);
}
