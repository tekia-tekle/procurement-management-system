package com.bizeff.procurement.persistences.jparepository.purchaserequisition;

import com.bizeff.procurement.persistences.entity.purchaserequisition.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity>findByUserId(String userId);
    Optional<UserEntity>findByPhoneNumber(String phoneNumber);
    Optional<UserEntity>findByEmail(String email);
    List<UserEntity>findByDepartment_DepartmentId(String departmentId);
    void deleteByUserId(String userId);
    void deleteByPhoneNumber(String phoneNumber);
    void deleteByEmail(String email);
}
