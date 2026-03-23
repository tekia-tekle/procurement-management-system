package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition;

import com.bizeff.procurement.models.purchaserequisition.CostCenter;

import java.util.List;
import java.util.Optional;

public interface CostCenterRepository {
    CostCenter save(CostCenter costCenter);
    CostCenter update(CostCenter costCenter);
    Optional<CostCenter> findByCostCenterId(String costCenterId);
    List<CostCenter>findByDepartmentId(String departmentId);
    void deleteByCostCenterId(String costCenterId);
    List<CostCenter> findAll();
    void deleteAll();
}
