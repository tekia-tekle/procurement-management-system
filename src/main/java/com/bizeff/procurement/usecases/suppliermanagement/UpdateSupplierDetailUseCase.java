package com.bizeff.procurement.usecases.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.inputboundary.suppliermanagement.UpdateSupplierContactDetailInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.SupplierContactDetailsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.SupplierPaymentMethodsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.UpdateSupplierContactDetailInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.UpdateSupplierContactDetailOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.UpdateSupplierContactDetailedOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.models.enums.PaymentMethodType;
import com.bizeff.procurement.models.enums.PaymentTerms;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplymanagement.SupplierContactDetail;
import com.bizeff.procurement.models.supplymanagement.SupplierPaymentMethod;
import com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UpdateSupplierDetailUseCase implements UpdateSupplierContactDetailInputBoundary {
    private SupplierRepository supplierRepository;
    private SupplierMaintenanceService supplierMaintenanceService;
    private UpdateSupplierContactDetailOutputBoundary updateSupplierContactDetailOutputBoundary;
    public UpdateSupplierDetailUseCase(SupplierRepository supplierRepository,
                                       SupplierMaintenanceService supplierMaintenanceService,
                                       UpdateSupplierContactDetailOutputBoundary updateSupplierContactDetailOutputBoundary){
        this.supplierRepository = supplierRepository;
        this.supplierMaintenanceService = supplierMaintenanceService;
        this.updateSupplierContactDetailOutputBoundary = updateSupplierContactDetailOutputBoundary;
    }
    @Override
    public UpdateSupplierContactDetailedOutputDS updateSupplierDetails(UpdateSupplierContactDetailInputDS input) {
        validateSupplierDetailInputDS(input);

        Supplier supplier = supplierRepository.findBySupplierId(input.getSupplierId()).orElseThrow(()->new NoSuchElementException("Supplier with id "+ input.getSupplierId()+" not found."));
        if (input.getUpdatedDate().isBefore(supplier.getRegistrationDate())){
            throw new IllegalArgumentException("date of updation can't be before registration date.");
        }

        supplierMaintenanceService.getSupplierMap().put(supplier.getSupplierId(), supplier);


        SupplierContactDetail newContactDetails = new SupplierContactDetail(input.getNewContactDetail().getFullName(), input.getNewContactDetail().getEmail(), input.getNewContactDetail().getPhone(), input.getNewContactDetail().getAddress());

        List<SupplierPaymentMethod> newPaymentMethods = new ArrayList<>();
        for (SupplierPaymentMethodsInputDS paymentMethodsInputDS: input.getNewPaymentMethods()){
            List<PaymentMethodType> acceptedPaymentMethods = new ArrayList<>();
            for (String paymentMethod: paymentMethodsInputDS.getPaymentMethodsAccepted()){
                PaymentMethodType acceptedPaymentMethod = PaymentMethodType.valueOf(paymentMethod.trim());
                acceptedPaymentMethods.add(acceptedPaymentMethod);
            }
            PaymentMethodType preferredPaymentMethod = PaymentMethodType.valueOf(paymentMethodsInputDS.getPreferredPaymentMethod().trim());
            PaymentTerms paymentTerms = PaymentTerms.valueOf(paymentMethodsInputDS.getPaymentTerms().trim());
            SupplierPaymentMethod newPaymentMethod = new SupplierPaymentMethod(
                    paymentMethodsInputDS.getBankName(),
                    paymentMethodsInputDS.getBankAccountNumber(),
                    paymentMethodsInputDS.getAccountHolderName(),
                    paymentMethodsInputDS.getAccountHolderPhoneNumber(),
                    acceptedPaymentMethods,
                    preferredPaymentMethod,
                    paymentTerms,
                    paymentMethodsInputDS.getCurrencyType(),
                    paymentMethodsInputDS.getCreditLimit()
            );
            newPaymentMethods.add(newPaymentMethod);
        }


        supplier.setSupplierName(input.getNewSupplierName());
        supplier.setSupplierCategory(input.getNewSupplierCategory());
        supplier.setTaxIdentificationNumber(input.getNewTaxIdentificationNumber());
        supplier.setRegistrationNumber(input.getNewRegistrationNumber());
        supplier.setSupplierContactDetail(newContactDetails);
        supplier.setSupplierPaymentMethods(newPaymentMethods);
        supplier.setLastUpdated(input.getUpdatedDate());

        supplierMaintenanceService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        Supplier updatedSupplier = supplierMaintenanceService.updateSupplier(supplier.getSupplierId(), supplier.getSupplierName(), supplier.getSupplierCategory(), supplier.getTaxIdentificationNumber(), supplier.getRegistrationNumber(), newContactDetails, newPaymentMethods, supplier.getExistedItems(), input.getUpdatedDate());


        Supplier savedSupplier = supplierRepository.update(updatedSupplier);

        UpdateSupplierContactDetailedOutputDS updateSupplierContactDetailedOutputDS = new UpdateSupplierContactDetailedOutputDS(
                savedSupplier.getSupplierId(), savedSupplier.getSupplierName(), savedSupplier.getSupplierCategory(), savedSupplier.getTaxIdentificationNumber(),
                savedSupplier.getRegistrationNumber(), savedSupplier.getRegistrationDate(), savedSupplier.getSupplierContactDetail(), savedSupplier.getSupplierPaymentMethods(),input.getUpdatedDate());

        updateSupplierContactDetailedOutputDS.setExistedItems(savedSupplier.getExistedItems());
        updateSupplierContactDetailedOutputDS.setUpdated(true);

        updateSupplierContactDetailOutputBoundary.presentUpdatedSupplier(updateSupplierContactDetailedOutputDS);

        return updateSupplierContactDetailedOutputDS;
    }
    public void validateSupplierDetailInputDS(UpdateSupplierContactDetailInputDS updateSupplierContactDetailInputDS){
        if (updateSupplierContactDetailInputDS == null){
            throw new NullPointerException("we can't update vendor with null,");
        }
        if (updateSupplierContactDetailInputDS.getSupplierId() == null || updateSupplierContactDetailInputDS.getSupplierId().trim().isEmpty()){
            throw new NullPointerException("supplier id can't be null or empty.");
        }
        if (updateSupplierContactDetailInputDS.getNewSupplierName() == null || updateSupplierContactDetailInputDS.getNewSupplierName().trim().isEmpty()){
            throw new NullPointerException("supplier name can't be null or empty.");
        }
        if (updateSupplierContactDetailInputDS.getNewSupplierCategory() == null || updateSupplierContactDetailInputDS.getNewSupplierCategory().trim().isEmpty()){
            throw new NullPointerException("supplier category can't be null or empty.");
        }
        if (updateSupplierContactDetailInputDS.getNewTaxIdentificationNumber() == null || updateSupplierContactDetailInputDS.getNewTaxIdentificationNumber().trim().isEmpty()){
            throw new NullPointerException("supplier tax identification number can't be null or empty.");
        }
        if (updateSupplierContactDetailInputDS.getNewRegistrationNumber() == null || updateSupplierContactDetailInputDS.getNewRegistrationNumber().trim().isEmpty()){
            throw new NullPointerException("supplier registration number can't be null or empty.");
        }
        validateSupplierContactDetail(updateSupplierContactDetailInputDS.getNewContactDetail());

        validateSupplierPaymentMethods(updateSupplierContactDetailInputDS.getNewPaymentMethods());

        if (updateSupplierContactDetailInputDS.getUpdatedDate() == null){
            throw new IllegalArgumentException("Date can't be null ");
        }

    }
    public void validateSupplierContactDetail(SupplierContactDetailsInputDS supplierContactDetailsInputDS){
        if (supplierContactDetailsInputDS == null ){
            throw new IllegalArgumentException("supplier contact detail can't be null.");
        }
        if (supplierContactDetailsInputDS.getFullName() == null || supplierContactDetailsInputDS.getFullName().trim().isEmpty()){
            throw new NullPointerException("supplier Person full name can't be null or empty.");
        }
        if (supplierContactDetailsInputDS.getEmail() == null || supplierContactDetailsInputDS.getEmail().trim().isEmpty()){
            throw new NullPointerException("supplier email address can't be null or empty.");
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!supplierContactDetailsInputDS.getEmail().matches(emailRegex)) {
            throw new IllegalArgumentException("please Enter valid email address based on the emailRegex");
        }
        if (supplierContactDetailsInputDS.getPhone() == null || supplierContactDetailsInputDS.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("supplier phone number is required field. can't be null or empty.");
        }
        String phoneRegex = "^\\+\\d{1,3}\\d{4,14}$";
        if (!supplierContactDetailsInputDS.getPhone().matches(phoneRegex)) {
            throw new IllegalArgumentException("Please Enter valid phone number with country code.");
        }
        if (supplierContactDetailsInputDS.getAddress() == null || supplierContactDetailsInputDS.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("the location of supplier must be specified. can't be null or Empty.");
        }
    }
    public void validateSupplierPaymentMethods(List<SupplierPaymentMethodsInputDS> supplierPaymentMethodsInputDS){
        if (supplierPaymentMethodsInputDS == null || supplierPaymentMethodsInputDS.isEmpty()) {
            throw new IllegalArgumentException("supplier's payment method must be specified. can't be null or Empty.");
        }
        for (SupplierPaymentMethodsInputDS paymentMethodsInputDS:supplierPaymentMethodsInputDS){
            if (paymentMethodsInputDS.getPaymentMethodsAccepted().isEmpty()|| paymentMethodsInputDS.getPaymentMethodsAccepted() == null){
                throw new IllegalArgumentException("supplier must contain atleast one method of transaction. it can't null or empty the lists of  accepted payment methods.");
            }
            if (paymentMethodsInputDS.getPreferredPaymentMethod() == null||paymentMethodsInputDS.getPreferredPaymentMethod().trim().isEmpty()){
                throw new IllegalArgumentException("supplier prefered payment method can't be null or empty.");
            }
            if (!paymentMethodsInputDS.getPaymentMethodsAccepted().contains(paymentMethodsInputDS.getPreferredPaymentMethod())){
                throw new IllegalArgumentException("the prefered payment method is not in the accepted payment method lists.");
            }
            if (paymentMethodsInputDS.getBankName() == null || paymentMethodsInputDS.getBankName().trim().isEmpty()) {
                throw new IllegalArgumentException("bank name used by the supplier must be specified.");
            }
            if (paymentMethodsInputDS.getBankAccountNumber() == null || paymentMethodsInputDS.getBankAccountNumber().trim().isEmpty()) {
                throw new IllegalArgumentException("account number can't be null or Empty. it is required field.");
            }
            if (paymentMethodsInputDS.getPaymentTerms() == null || paymentMethodsInputDS.getPaymentTerms().trim().isEmpty()) {
                throw new IllegalArgumentException("supplier must specifies the method of transaction. can't be null or Empty.");
            }
            if (paymentMethodsInputDS.getCurrencyType() == null || paymentMethodsInputDS.getCurrencyType().trim().isEmpty()){
                throw new IllegalArgumentException("currency of exchange can't be null or empty.");
            }
            if (paymentMethodsInputDS.getCreditLimit().compareTo(BigDecimal.ZERO) < 0){
                throw new IllegalArgumentException("the credit limit can't be negative value.");
            }
        }
    }
}