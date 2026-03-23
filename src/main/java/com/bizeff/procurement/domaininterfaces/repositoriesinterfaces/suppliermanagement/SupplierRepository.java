package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement;

import com.bizeff.procurement.models.supplymanagement.Supplier;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface SupplierRepository {
    Supplier save(Supplier supplier);
    Supplier update(Supplier supplier);
    Optional<Supplier> findBySupplierId(String supplierId);
    Optional<Supplier> findByEmail( String email);
    Optional<Supplier> findByPhoneNumber(String phone);
    List<Supplier> findAll();
    void deleteBySupplierId(String supplierId);
    List<Supplier> findBySupplierCategory(String category);

    // Find active suppliers
    List<Supplier> findByIsActiveTrue();

    List<Supplier> findByRegistrationDateAfter(LocalDate date);

    List<Supplier> findByRegistrationDateBefore(LocalDate date);

    Optional<Supplier> findByTaxIdentificationNumber(String taxId);

    Optional<Supplier> findByRegistrationNumber(String regNumber);
    void deleteAll();
}