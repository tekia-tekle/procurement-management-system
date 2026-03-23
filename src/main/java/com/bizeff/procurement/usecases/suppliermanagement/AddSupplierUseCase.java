package com.bizeff.procurement.usecases.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.inputboundary.suppliermanagement.AddSupplierInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.ItemsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.AddSupplierInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.SupplierContactDetailsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.SupplierPaymentMethodsInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.AddSupplierOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.AddSupplierOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.models.enums.PaymentMethodType;
import com.bizeff.procurement.models.enums.PaymentTerms;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplymanagement.SupplierContactDetail;
import com.bizeff.procurement.models.supplymanagement.SupplierPaymentMethod;
import com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddSupplierUseCase implements AddSupplierInputBoundary {
    private SupplierRepository supplierRepository;
    private SupplierMaintenanceService supplierMaintenanceService;
    private AddSupplierOutputBoundary addSupplierOutputBoundary;
    public AddSupplierUseCase(SupplierRepository supplierRepository,
                              SupplierMaintenanceService supplierMaintenanceService,
                              AddSupplierOutputBoundary addSupplierOutputBoundary)
    {
        this.supplierRepository = supplierRepository;
        this.supplierMaintenanceService = supplierMaintenanceService;
        this.addSupplierOutputBoundary = addSupplierOutputBoundary;
    }
    @Override
    public AddSupplierOutputDS addSupplier(AddSupplierInputDS inputData) {
        validateSupplierInputEntity(inputData);

        SupplierContactDetail contactDetails = new SupplierContactDetail(
                inputData.getSupplierContactDetailsInputDS().getFullName(),
                inputData.getSupplierContactDetailsInputDS().getEmail(),
                inputData.getSupplierContactDetailsInputDS().getPhone(),
                inputData.getSupplierContactDetailsInputDS().getAddress()
        );
        List<SupplierPaymentMethod> paymentMethods = new ArrayList<>();
        for (SupplierPaymentMethodsInputDS paymentMethodsInputDS: inputData.getSupplierPaymentMethodsInputDS()){
            List<PaymentMethodType> acceptedPaymentMethods = new ArrayList<>();
            for (String paymentMethod: paymentMethodsInputDS.getPaymentMethodsAccepted()){
                PaymentMethodType acceptedPaymentMethod = PaymentMethodType.valueOf(paymentMethod.trim());
                acceptedPaymentMethods.add(acceptedPaymentMethod);
            }
            PaymentMethodType preferredPaymentMethod = PaymentMethodType.valueOf(paymentMethodsInputDS.getPreferredPaymentMethod().trim());
            PaymentTerms paymentTerms = PaymentTerms.valueOf(paymentMethodsInputDS.getPaymentTerms().trim());
            SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod(
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
            paymentMethods.add(paymentMethod);
        }
        List<ItemsInputDS> items = inputData.getItemstobeIncluded(); //list of items that received from the input.
        List<Inventory> suppliersItem = new ArrayList<>(); //
        for (ItemsInputDS inputItem: items){
            Inventory inventory = new Inventory(
                    inputItem.getItemId(),
                    inputItem.getItemName(),
                    inputItem.getRequestedQuantity(),
                    inputItem.getUnitPrice(),
                    inputItem.getItemCategory(),
                    inputItem.getExpireDate(),
                    inputItem.getSpecification()
            );
            suppliersItem.add(inventory);
        }

        Supplier createdSupplier = supplierMaintenanceService.createSupplier(
                inputData.getSupplierName(),
                inputData.getSupplierCategory(),
                inputData.getTaxIdentificationNumber(),
                inputData.getRegistrationNumber(),
                contactDetails,paymentMethods,
                suppliersItem,
                inputData.getRegistrationDate());
        supplierRepository.findByRegistrationNumber(createdSupplier.getRegistrationNumber())
                .ifPresent(supplier -> {
                    throw new IllegalArgumentException("Supplier with registration number " + createdSupplier.getRegistrationNumber() + " already exists.");
                });
        supplierRepository.findByTaxIdentificationNumber(createdSupplier.getTaxIdentificationNumber())
                .ifPresent(supplier -> {
                    throw new IllegalArgumentException("Supplier with tax identification number " + createdSupplier.getTaxIdentificationNumber() + " already exists.");
                        });

        supplierRepository.findByEmail(createdSupplier.getSupplierContactDetail().getEmail())
                .ifPresent(supplier -> {
                    throw new IllegalArgumentException("Supplier with email " + createdSupplier.getSupplierContactDetail().getEmail() + " already exists.");
                });
        supplierRepository.findByPhoneNumber(createdSupplier.getSupplierContactDetail().getPhone())
                .ifPresent(supplier -> {
                    throw new IllegalArgumentException("Supplier with phone Number " + createdSupplier.getSupplierContactDetail().getPhone() + " already exists. ");
                });
        Supplier newSupplier = supplierRepository.save(createdSupplier);
        List<Inventory> existedItems = newSupplier.getExistedItems();

        AddSupplierOutputDS output = new AddSupplierOutputDS(
                newSupplier.getSupplierId(),
                newSupplier.getSupplierName(),
                newSupplier.getSupplierCategory(),
                newSupplier.getTaxIdentificationNumber(),
                newSupplier.getRegistrationNumber(),
                newSupplier.getSupplierContactDetail(),
                newSupplier.getSupplierPaymentMethods(),
                existedItems,
                newSupplier.isActive(),
                newSupplier.getRegistrationDate(),
                newSupplier.getLastUpdated());

        addSupplierOutputBoundary.presentAddedSupplier(output);
        return output;
    }
    public void validateSupplierInputEntity(AddSupplierInputDS inputData){
        if (inputData == null){
            throw new NullPointerException("input data can't be null");
        }

        if (inputData.getSupplierName() == null || inputData.getSupplierName().trim().isEmpty()){
            throw new IllegalArgumentException("supplier name is required field can't be null or empty.");
        }
        if (inputData.getSupplierCategory() == null || inputData.getSupplierCategory().trim().isEmpty()){
            throw new IllegalArgumentException("supplier category can't be null or empty.");
        }
        if (inputData.getTaxIdentificationNumber() == null || inputData.getTaxIdentificationNumber().trim().isEmpty()){
            throw new IllegalArgumentException("supplier tax identification number is required field can't be null or empty.");
        }
        if (inputData.getRegistrationNumber() == null || inputData.getRegistrationNumber().trim().isEmpty()){
            throw new IllegalArgumentException("supplier Registration Number can't be null or empty.");
        }
        validateSupplierContactDetailsInputData(inputData.getSupplierContactDetailsInputDS());
        validateSupplierPaymentMethodInputData(inputData.getSupplierPaymentMethodsInputDS());
        validateItemEntity(inputData.getItemstobeIncluded());

    }
    public void validateSupplierContactDetailsInputData(SupplierContactDetailsInputDS contactDetails){
        if (contactDetails == null){
            throw new IllegalArgumentException("supplier Person's contact detail can't be null.");
        }
        if (contactDetails.getFullName() == null || contactDetails.getFullName().trim().isEmpty()){
            throw new IllegalArgumentException("supplier person name must be specified. can't be null or Empty.");
        }
        if (contactDetails.getEmail() == null || contactDetails.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("supplier Person email address is required field. can't be null or empty.");
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!contactDetails.getEmail().matches(emailRegex)){
            throw new IllegalArgumentException("please Enter valid email address.based on the emailRegex = \"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\";\n");
        }
        if (contactDetails.getPhone() == null || contactDetails.getPhone().trim().isEmpty()){
            throw new IllegalArgumentException("vendor phone number is required field. can't be null or empty.");
        }
        String phoneRegex = "^\\+\\d{1,3}\\d{4,14}$";
        if (!contactDetails.getPhone().matches(phoneRegex)){
            throw new IllegalArgumentException("Please Enter valid phone number with country code.");
        }
        if (contactDetails.getAddress() == null || contactDetails.getAddress().trim().isEmpty()){
            throw new IllegalArgumentException("the location of vendor must be specified. can't be null or Empty.");
        }
    }
    public void validateSupplierPaymentMethodInputData(List<SupplierPaymentMethodsInputDS> paymentMethods){
        if (paymentMethods == null){
            throw new IllegalArgumentException("vendor's payment method must be specified. can't be null or Empty.");
        }
        for (SupplierPaymentMethodsInputDS inputPaymentMethod: paymentMethods){
            if (inputPaymentMethod.getBankName() == null || inputPaymentMethod.getBankName().trim().isEmpty()){
                throw new IllegalArgumentException("bank name used by the vendor must be specified.");
            }
            if (inputPaymentMethod.getBankAccountNumber() == null || inputPaymentMethod.getBankAccountNumber().trim().isEmpty()){
                throw new IllegalArgumentException("account number can't be null or Empty. it is required field.");
            }
            if (inputPaymentMethod.getPaymentTerms() == null || inputPaymentMethod.getPaymentTerms().trim().isEmpty()){
                throw new IllegalArgumentException("vendor must specifies the payment trem  of transaction.can't be null or Empty.");
            }
            if (inputPaymentMethod.getCurrencyType() == null || inputPaymentMethod.getCurrencyType().trim().isEmpty()){
                throw new IllegalArgumentException("currency of transaction must be specified. can't be null or empty.");
            }
        }

    }
    public void validateItemEntity(List<ItemsInputDS> items) {
        if (items == null ) {
            throw new NullPointerException("inventory data can't be null.");
        }
        if (items.isEmpty()) {
            throw new IllegalArgumentException("At least one item is required in the supplier inventory.");
        }
        for (ItemsInputDS item : items) {
            if (item.getItemId() == null || item.getItemId().trim().isEmpty()) {
                throw new NullPointerException("Item ID cannot be null or empty.");
            }
            if (item.getItemName() == null || item.getItemName().trim().isEmpty()) {
                throw new NullPointerException("Item name cannot be null or empty.");
            }
            if (item.getItemCategory() == null || item.getItemCategory().trim().isEmpty()) {
                throw new NullPointerException(" itemCategory can't be null or empty.");
            }
            if (item.getRequestedQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero.");
            }
            if (item.getUnitPrice().compareTo(BigDecimal.ZERO)< 0) {
                throw new IllegalArgumentException("Unit price must be non-negative.");
            }
            if (item.getSpecification() == null || item.getSpecification().trim().isEmpty()) {
                throw new NullPointerException("Specification cannot be null or empty.");
            }
        }
    }
}