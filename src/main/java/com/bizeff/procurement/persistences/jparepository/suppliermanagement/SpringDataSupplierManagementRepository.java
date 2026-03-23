package com.bizeff.procurement.persistences.jparepository.suppliermanagement;

import com.bizeff.procurement.persistences.entity.suppliermanagement.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface SpringDataSupplierManagementRepository extends JpaRepository<SupplierEntity,Long> {
    Optional<SupplierEntity>findBySupplierId(String supplierId);
    Optional<SupplierEntity> findBySupplierContactDetailEmail(String email);
    Optional<SupplierEntity> findBySupplierContactDetailPhoneNumber(String phone);
    void deleteBySupplierId(String supplierId);
    List<SupplierEntity> findBySupplierCategory(String category);
    List<SupplierEntity> findByIsActiveTrue();
    List<SupplierEntity> findByRegistrationDateAfter(LocalDate date);
    List<SupplierEntity> findByRegistrationDateBefore(LocalDate date);
    Optional<SupplierEntity> findByTaxIdentificationNumber(String taxId);
    Optional<SupplierEntity> findByRegistrationNumber(String regNumber);
}
