package com.bizeff.procurement.persistences.jparepository.purchaserequisition;

import com.bizeff.procurement.persistences.entity.purchaserequisition.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataDepartmentRepository extends JpaRepository<DepartmentEntity,Long> {
    Optional<DepartmentEntity> findByDepartmentId(String departmentId);

    void deleteByDepartmentId(String departmentId);
}
