package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition;

import com.bizeff.procurement.models.purchaserequisition.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    Department save(Department department);

    Department update(Department department);

    Optional<Department> findByDepartmentId(String departmentId);

    void deleteByDepartmentId(String departmentId);

    List<Department> findAll();
    void deleteAll();
}
