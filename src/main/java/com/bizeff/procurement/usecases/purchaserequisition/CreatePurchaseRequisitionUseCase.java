package com.bizeff.procurement.usecases.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition.CreateRequisitionInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.CreateRequisitionInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.ItemsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.RequisitionContactDetailsInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.CreateRequisitionOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.CreateRequisitionOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.CostCenterRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.DepartmentRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.UserRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.models.purchaserequisition.*;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionMaintenancesService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CreatePurchaseRequisitionUseCase implements CreateRequisitionInputBoundary {
    private PurchaseRequisitionRepository purchaseRequisitionRepository;
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;
    private CostCenterRepository costCenterRepository;
    private SupplierRepository supplierRepository;
    private PurchaseRequisitionMaintenancesService purchaseRequisitionMaintenancesService;
    private CreateRequisitionOutputBoundary requisitionRequesterOutputBoundary;
    public CreatePurchaseRequisitionUseCase(
            PurchaseRequisitionRepository purchaseRequisitionRepository,
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            CostCenterRepository costCenterRepository,
            SupplierRepository supplierRepository,
            PurchaseRequisitionMaintenancesService purchaseRequisitionMaintenancesService,
            CreateRequisitionOutputBoundary requisitionRequesterOutputBoundary)
    {
        this.purchaseRequisitionRepository = purchaseRequisitionRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.costCenterRepository = costCenterRepository;
        this.supplierRepository = supplierRepository;
        this.purchaseRequisitionMaintenancesService = purchaseRequisitionMaintenancesService;
        this.requisitionRequesterOutputBoundary = requisitionRequesterOutputBoundary;
    }

    @Override
    public CreateRequisitionOutputDS createRequisition(CreateRequisitionInputDS inputData) {
        validateRequisitionEntity(inputData);

        // Validate that the requisition number is unique
        if (purchaseRequisitionRepository.findByRequisitionNumber(inputData.getRequisitionNumber()).isPresent()) {
            throw new IllegalArgumentException("Requisition number already exists.");
        }

        // Validate department and cost center existence
        Department department = departmentRepository.findByDepartmentId(inputData.getDepartment()) //check  is there any department that existed based on the creation.
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(inputData.getCostCenterId()) //check the cost center?
                .orElseThrow(() -> new IllegalArgumentException("Cost Center not found"));


        // Fetch Requester (Check if user exists, else create new)
        User requester = userRepository.findByPhoneNumber(inputData.getRequester().getPhone())
                .orElseGet(() -> { // If user does not exist, create a new one
                    User newUser = new User(
                            inputData.getRequester().getName(),
                            inputData.getRequester().getEmail(),
                            inputData.getRequester().getPhone(),
                            departmentRepository.findByDepartmentId(inputData.getRequester().getDepartmentId())
                                    .orElseThrow(() -> new IllegalArgumentException("Department not found")),
                            inputData.getRequester().getRole());
                    return userRepository.save(newUser); // Persist new user
                });



        Supplier supplier = supplierRepository.findBySupplierId(inputData.getSupplierId()).orElseThrow(()->new IllegalArgumentException("there is no supplier with supplier ID"+inputData.getSupplierId()));
        List<Inventory> inventoryList = supplier.getExistedItems();

        List<RequestedItem> requestedItems = inputData.getItems().stream()
                .map(item -> {
                    // Find matching inventory item
                    Inventory matchingInventory = inventoryList.stream()
                            .filter(inventory ->
                                    inventory.getItemId().equals(item.getItemId()) &&
                                            inventory.getItemName().equals(item.getItemName()) &&
                                            inventory.getUnitPrice().compareTo(item.getUnitPrice()) == 0 &&
                                            inventory.getItemCategory().equals(item.getItemCategory()) &&
                                            inventory.getExpiryDate().equals(item.getExpireDate()) &&
                                            inventory.getSpecification().equals(item.getSpecification())
                            )
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Requested item not found in supplier inventory: " + item.getItemId()
                            ));

                    // Check if requested quantity is available
                    if (item.getRequestedQuantity() > matchingInventory.getQuantityAvailable()) {
                        throw new IllegalArgumentException(
                                "Requested quantity (" + item.getRequestedQuantity() +
                                        ") exceeds available inventory (" + matchingInventory.getQuantityAvailable() +
                                        ") for item: " + item.getItemId()
                        );
                    }
                    return new RequestedItem(matchingInventory, item.getRequestedQuantity());
                })
                .collect(Collectors.toList());

        PurchaseRequisition newRequisition = purchaseRequisitionMaintenancesService.createPurchaseRequisition(
                inputData.getRequisitionNumber(),
                inputData.getRequisitionDate(),
                requester, department, savedCostCenter,
                requestedItems, inputData.getPriorityLevel(),
                inputData.getExpectedDeliveryDate(), inputData.getJustification());
        newRequisition.setSupplier(supplier);
        // Save requisition
        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(newRequisition);

        // Convert to Output DS
        CreateRequisitionOutputDS outputDS = new CreateRequisitionOutputDS(
                savedRequisition.getRequisitionId(),
                savedRequisition.getRequisitionNumber(),
                savedRequisition.getRequisitionDate(),
                savedRequisition.getItems(),
                savedRequisition.getRequestedBy().getFullName(),
                savedRequisition.getDepartment().getDepartmentId(),
                savedRequisition.getCostCenter().getCostCenterId(),
                savedRequisition.getTotalEstimatedCosts(),
                savedRequisition.getExpectedDeliveryDate(),
                savedRequisition.getSupplier().getSupplierId(),
                savedRequisition.getRequisitionStatus());

        // Present the output
        requisitionRequesterOutputBoundary.presentRequestedRequisitions(outputDS);

        return outputDS;
    }

    public void validateRequisitionEntity(CreateRequisitionInputDS inputData) {
        if (inputData == null) {
            throw new NullPointerException("input data can't be null");
        }
        if (inputData.getRequisitionNumber() == null || inputData.getRequisitionNumber().trim().isEmpty()) {
            throw new NullPointerException("Can't create requisition with null requisition Number.");
        }
        validateContactDetails(inputData.getRequester());
        if (inputData.getItems() == null || inputData.getItems().isEmpty()) {
            throw new NullPointerException("At least one item is required in the requisition.");
        }
        if (inputData.getRequisitionDate() == null || inputData.getRequisitionDate() == null) {
            throw new NullPointerException("Dates can't be null.");
        }
        if (inputData.getExpectedDeliveryDate().isBefore(inputData.getRequisitionDate())) {
            throw new IllegalArgumentException("Delivery date can't be before the requested date.");
        }
        validateItemEntity(inputData.getItems());
    }
    public void validateContactDetails(RequisitionContactDetailsInputDS contactDetails){
        if (contactDetails == null){
            throw new NullPointerException("requester must fill the profile. it can't be null");
        }
        if (contactDetails.getName() == null || contactDetails.getName().trim().isEmpty()){
            throw new NullPointerException("Name is required field can't be null or empty.");
        }
        if (contactDetails.getEmail() == null || contactDetails.getEmail().trim().isEmpty()){
            throw new NullPointerException("Email is required field for contacting.can't be null or empty.");
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!contactDetails.getEmail().matches(emailRegex)){
            throw new IllegalArgumentException("please Enter valid email address.based on the emailRegex = \"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\";\n");
        }
        if (contactDetails.getPhone() == null || contactDetails.getPhone().trim().isEmpty()){
            throw new NullPointerException(" phone number is required field. can't be null or empty.");
        }
        String phoneRegex = "^\\+\\d{1,3}\\d{4,14}$";
        if (!contactDetails.getPhone().matches(phoneRegex)){
            throw new IllegalArgumentException("Please Enter valid phone number with country code.");
        }
        if (contactDetails.getDepartmentId() == null || contactDetails.getDepartmentId().trim().isEmpty()){
            throw new NullPointerException("requester department is required field.can't be null or empty.");
        }
        if (contactDetails.getRole() == null || contactDetails.getRole().trim().isEmpty()){
            throw new NullPointerException("role of the requester is required field. can't be null or empty.");
        }
    }
    public void validateItemEntity(List<ItemsInputDS> items) {
        if (items == null || items.isEmpty()) {
            throw new NullPointerException("At least one item is required in the requisition.");
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

    public PurchaseRequisitionRepository getPurchaseRequisitionRepository() {
        return purchaseRequisitionRepository;
    }

    public PurchaseRequisitionMaintenancesService getPurchaseRequisitionMaintenancesService() {
        return purchaseRequisitionMaintenancesService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
