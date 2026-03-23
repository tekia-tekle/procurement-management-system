package com.bizeff.procurement.persistences.jparepository.purchaserequisition;

import com.bizeff.procurement.persistences.entity.purchaserequisition.CostCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface SpringDataCostCenterRepository extends JpaRepository<CostCenterEntity, Long > {
    Optional<CostCenterEntity>findByCostCenterId(String costCenterId);
    List<CostCenterEntity>findByDepartment_DepartmentId(String departmentId);
    void deleteByCostCenterId(String costCenterId);
}
