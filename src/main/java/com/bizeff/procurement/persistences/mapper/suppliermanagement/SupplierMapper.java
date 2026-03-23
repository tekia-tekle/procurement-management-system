package com.bizeff.procurement.persistences.mapper.suppliermanagement;

import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplymanagement.SupplierContactDetail;
import com.bizeff.procurement.models.supplymanagement.SupplierPaymentMethod;
import com.bizeff.procurement.persistences.entity.suppliermanagement.EmbeddedContactDetails;
import com.bizeff.procurement.persistences.entity.suppliermanagement.InventoryEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.PaymentMethodEntity;
import com.bizeff.procurement.persistences.entity.suppliermanagement.SupplierEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierMapper {

    public static SupplierEntity toEntity(Supplier supplier) {
        if (supplier == null) return null;
        SupplierEntity supplierEntity = new SupplierEntity();
        if (supplier.getId() != null){
            supplierEntity.setId(supplier.getId());
        }
        supplierEntity.setSupplierId(supplier.getSupplierId());
        supplierEntity.setSupplierName(supplier.getSupplierName());
        supplierEntity.setSupplierCategory(supplier.getSupplierCategory());
        supplierEntity.setTaxIdentificationNumber(supplier.getTaxIdentificationNumber());
        supplierEntity.setRegistrationNumber(supplier.getRegistrationNumber());
        supplierEntity.setSupplierContactDetail(toEntity(supplier.getSupplierContactDetail()));
        List<PaymentMethodEntity> paymentMethodEntities = supplier.getSupplierPaymentMethods().stream()
                .map(supplierPaymentMethod -> {
                    PaymentMethodEntity paymentMethodEntity = toEntity(supplierPaymentMethod);
                    paymentMethodEntity.getSuppliers().add(supplierEntity);
                    return paymentMethodEntity;
                }).collect(Collectors.toList());

        supplierEntity.setPaymentMethodEntities(paymentMethodEntities);

        List<InventoryEntity>inventoryEntities = supplier.getExistedItems().stream()
                        .map(inventory -> {
                            InventoryEntity  inventoryEntity = InventoryMapper.toEntity(inventory);
                            supplierEntity.addInventory(inventoryEntity);
                            return inventoryEntity;
                        }).collect(Collectors.toList());

        supplierEntity.setInventoryItems(inventoryEntities);
        supplierEntity.setActive(supplier.isActive());
        supplierEntity.setRegistrationDate(supplier.getRegistrationDate());
        supplierEntity.setLastUpdated(supplier.getLastUpdated());

        return supplierEntity;
    }

    public static Supplier toModel(SupplierEntity supplierEntity) {
        if (supplierEntity == null) {
            return null;
        }
        Supplier supplier =  new Supplier();
        supplier.setId(supplierEntity.getId());
        supplier.setSupplierId(supplierEntity.getSupplierId());
        supplier.setSupplierName(supplierEntity.getSupplierName());
        supplier.setSupplierCategory(supplierEntity.getSupplierCategory());
        supplier.setTaxIdentificationNumber(supplierEntity.getTaxIdentificationNumber());
        supplier.setRegistrationNumber(supplierEntity.getRegistrationNumber());
        supplier.setSupplierContactDetail(toModel(supplierEntity.getSupplierContactDetail()));
        List<SupplierPaymentMethod> paymentMethods = Optional.ofNullable(supplierEntity.getPaymentMethodEntities())
                .orElse(List.of())
                .stream()
                .map(SupplierMapper::toModel)
                .collect(Collectors.toList());

        supplier.setSupplierPaymentMethods(paymentMethods);
        List<Inventory> inventoryList = supplierEntity.getInventoryItems().stream()
                .map(inventoryEntity -> {
                    Inventory inventory = InventoryMapper.toModel(inventoryEntity);
                    supplier.addInventory(inventory);
                    return inventory;
                }).collect(Collectors.toList());
        supplier.setExistedItems(inventoryList);
        supplier.setActive(supplierEntity.isActive());
        supplier.setRegistrationDate(supplierEntity.getRegistrationDate());
        supplier.setLastUpdated(supplierEntity.getLastUpdated());

        return supplier;
    }

    public static EmbeddedContactDetails toEntity(SupplierContactDetail contactDetails) {
        validateContactDetails(contactDetails);
        return new EmbeddedContactDetails(
                contactDetails.getFullName(),
                contactDetails.getEmail(),
                contactDetails.getPhone(),
                contactDetails.getAddress());
    }

    public static SupplierContactDetail toModel(EmbeddedContactDetails embeddedContactDetails) {
        if (embeddedContactDetails == null) {
            return null;
        }
        return new SupplierContactDetail(
                embeddedContactDetails.getFullName(),
                embeddedContactDetails.getEmail(),
                embeddedContactDetails.getPhoneNumber(),
                embeddedContactDetails.getAddress()
        );
    }
    public static PaymentMethodEntity toEntity(SupplierPaymentMethod paymentMethod){
        validatePaymentMethod(paymentMethod);
        return new PaymentMethodEntity(
                paymentMethod.getBankName(),
                paymentMethod.getBankAccountNumber(),
                paymentMethod.getAccountHolderName(),
                paymentMethod.getAccountHolderPhoneNumber(),
                paymentMethod.getPaymentMethods(),
                paymentMethod.getPreferredPaymentMethod(),
                paymentMethod.getPaymentTerms(),
                paymentMethod.getCurrencyType(),
                paymentMethod.getCreditLimit()
        );
    }
    public static SupplierPaymentMethod toModel(PaymentMethodEntity paymentMethodEntity){
        if (paymentMethodEntity == null){
            return null;
        }
        return new SupplierPaymentMethod(
                paymentMethodEntity.getBankName(),
                paymentMethodEntity.getBankAccountNumber(),
                paymentMethodEntity.getAccountHolderName(),
                paymentMethodEntity.getAccountHolderPhoneNumber(),
                paymentMethodEntity.getAcceptedPaymentTypes(),
                paymentMethodEntity.getPreferredPaymentMethod(),
                paymentMethodEntity.getPaymentTerms(),
                paymentMethodEntity.getCurrencyType(),
                paymentMethodEntity.getCreditLimit()
        );
    }

    static void validateContactDetails(SupplierContactDetail contactDetails){
        if (contactDetails == null){
            throw new NullPointerException("vendor's contact detail can't be null.");
        }
        if (contactDetails.getFullName() == null || contactDetails.getFullName().trim().isEmpty()){
            throw new NullPointerException("vendor name must be specified. can't be null or Empty.");
        }
        if (contactDetails.getEmail() == null || contactDetails.getEmail().trim().isEmpty()){
            throw new NullPointerException("vendor email address is required field. can't be null or empty.");
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!contactDetails.getEmail().matches(emailRegex)){
            throw new IllegalArgumentException("please Enter valid email address.based on the emailRegex = \"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\";\n");
        }
        if (contactDetails.getPhone() == null || contactDetails.getPhone().trim().isEmpty()){
            throw new NullPointerException("vendor phone number is required field. can't be null or empty.");
        }
        String phoneRegex = "^\\+\\d{1,3}\\d{4,14}$";
        if (!contactDetails.getPhone().matches(phoneRegex)){
            throw new IllegalArgumentException("Please Enter valid phone number with country code.");
        }
        if (contactDetails.getAddress() == null || contactDetails.getAddress().trim().isEmpty()){
            throw new NullPointerException("the location of vendor must be specified. can't be null or Empty.");
        }
    }
    static void validatePaymentMethod(SupplierPaymentMethod paymentMethods){
        if (paymentMethods == null){
            throw new NullPointerException("vendor's payment method must be specified. can't be null or Empty.");
        }
        if (paymentMethods.getPaymentMethods() == null || paymentMethods.getPaymentMethods().isEmpty()){
            throw new IllegalArgumentException("Accepted payment methods can't be  null or empty.");
        }
        if (paymentMethods.getBankName() == null || paymentMethods.getBankName().trim().isEmpty()){
            throw new NullPointerException("bank name used by the vendor must be specified.");
        }
        if (paymentMethods.getBankAccountNumber() == null || paymentMethods.getBankAccountNumber().trim().isEmpty()){
            throw new NullPointerException("account number can't be null or Empty. it is required field.");
        }
        if (paymentMethods.getAccountHolderName() == null || paymentMethods.getAccountHolderName().trim().isEmpty()){
            throw new IllegalArgumentException("account holder name is required.");
        }
        if (paymentMethods.getAccountHolderPhoneNumber() == null || paymentMethods.getAccountHolderPhoneNumber().trim().isEmpty()){
            throw new IllegalArgumentException("Account Holder Phone number is required. can't be null or empty.");
        }
        String phoneRegex = "^\\+\\d{1,3}\\d{4,14}$";
        if (!paymentMethods.getAccountHolderPhoneNumber().matches(phoneRegex)){
            throw new IllegalArgumentException("Please Enter valid phone number with country code.");
        }
        if (paymentMethods.getPreferredPaymentMethod() == null || paymentMethods.getPreferredPaymentMethod().toString().trim().isEmpty()){
            throw new IllegalArgumentException("the preferred payment method can't be null or empty.");
        }
        if (!paymentMethods.getPaymentMethods().contains(paymentMethods.getPreferredPaymentMethod())){
            throw new IllegalArgumentException("the preferred payment method doesn't existed in the accepted Payment method.");
        }
        if (paymentMethods.getPaymentTerms() == null || paymentMethods.getPaymentTerms().toString().trim().isEmpty()){
            throw new IllegalArgumentException("payment term can't be null or empty.");
        }
        if (paymentMethods.getCurrencyType() == null || paymentMethods.getCurrencyType().trim().isEmpty()){
            throw new NullPointerException("currency of transaction must be specified. can't be null or empty.");
        }
        if (paymentMethods.getCreditLimit().compareTo(BigDecimal.ZERO) < 0.0){
            throw new IllegalArgumentException("Credit Limit can't be negative.");
        }
    }
}