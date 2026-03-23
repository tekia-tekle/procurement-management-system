package com.bizeff.procurement.usecases.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition.TrackRequisitionInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.RequisitionContactDetailsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.TrackRequisitionInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.TrackRequisitionOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.TrackRequisitionOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.InventoryRepository;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.purchaserequisition.RequestedItem;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionCatalogValidationService;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionTrackingStatusService;

import java.util.NoSuchElementException;

public class TrackPurchaseRequisitionUseCase implements TrackRequisitionInputBoundary {
    private PurchaseRequisitionRepository purchaseRequisitionRepository;
    private PurchaseRequisitionCatalogValidationService validatePurchaseRequisitionWithCatalogServices;
    private PurchaseRequisitionTrackingStatusService purchaseRequisitionTrackingStatusServices;

    private TrackRequisitionOutputBoundary reviewRequisitionOutputBoundary;
    private InventoryRepository inventoryRepository;
    public TrackPurchaseRequisitionUseCase(
            PurchaseRequisitionRepository purchaseRequisitionRepository,
            PurchaseRequisitionCatalogValidationService validateRevalidatePurchaseRequisitionWithCatalogServices,
            PurchaseRequisitionTrackingStatusService purchaseRequisitionTrackingStatusServices,
            TrackRequisitionOutputBoundary reviewRequisitionOutputBoundary,
            InventoryRepository inventoryRepository)
    {
        this.purchaseRequisitionRepository = purchaseRequisitionRepository;
        this.validatePurchaseRequisitionWithCatalogServices = validateRevalidatePurchaseRequisitionWithCatalogServices;
        this.purchaseRequisitionTrackingStatusServices = purchaseRequisitionTrackingStatusServices;
        this.reviewRequisitionOutputBoundary = reviewRequisitionOutputBoundary;
        this.inventoryRepository = inventoryRepository;
    }
    @Override
    public TrackRequisitionOutputDS trackRequisition(TrackRequisitionInputDS inputData) {
        validateEntity(inputData);
        String managerName = inputData.getManager().getName();
        PurchaseRequisition requisition = purchaseRequisitionRepository.findByRequisitionId(inputData.getRequisitionId()).orElseThrow(()-> new NoSuchElementException("there is no Purchase Requisition with the given requisitionNumber which is "+ inputData.getRequisitionId()));

        Department department = requisition.getDepartment();
        purchaseRequisitionTrackingStatusServices.getDepartments().add(department);
        for (RequestedItem item: requisition.getItems()){
            Inventory inventory = inventoryRepository.findByItemId(item.getInventory().getItemId()).orElseThrow(()->new IllegalArgumentException("there is no inventory in the invntory repository with id"+item.getInventory().getItemId())); //check each item if exist in the
            validatePurchaseRequisitionWithCatalogServices.addItemToCatalog(inventory.getItemId(),inventory.getItemName(), inventory.getQuantityAvailable(), inventory.getUnitPrice(), inventory.getItemCategory(), inventory.getExpiryDate(), inventory.getSpecification()); // adding to the map in order to  use later for validate requested item with the item in the category.

        }

        validatePurchaseRequisitionWithCatalogServices.validateRequisitionFieldsWithCatalogeData(requisition); //validate the requisition with existed catelog field data.

        purchaseRequisitionTrackingStatusServices.getAllRequisitions().put(requisition.getRequisitionId(), requisition);

        PurchaseRequisition trackedRequisition = purchaseRequisitionTrackingStatusServices.trackRequisitionStatus(inputData.getRequisitionId()); //track the requisition status based on budget.
        trackedRequisition.setUpdatedDate(inputData.getTrackedDate());

        trackedRequisition = purchaseRequisitionRepository.update(trackedRequisition); //save the updated requisition status in the repository.

        String comments = " ";
        if (trackedRequisition.getRequisitionStatus().equals(RequisitionStatus.PENDING)){
            comments = "the Requisition needs additional reviewing time to approve. it needs additional budget.";
        }
        if (trackedRequisition.getRequisitionStatus().equals(RequisitionStatus.REJECTED)){
            comments = "the requisition is rejected";
        }
        if (trackedRequisition.getRequisitionStatus().equals(RequisitionStatus.APPROVED)){
            comments = "the requisition is Approved";
        }

        TrackRequisitionOutputDS trackedOutput = new TrackRequisitionOutputDS(trackedRequisition.getRequisitionId(),trackedRequisition.getRequisitionNumber(),trackedRequisition.getRequisitionStatus(),managerName, trackedRequisition.getUpdatedDate(),comments);

        reviewRequisitionOutputBoundary.presentApprovedRequisitions(trackedOutput);

        return trackedOutput;
    }
    public void validateEntity(TrackRequisitionInputDS inputData) {
        if (inputData == null) {
            throw new NullPointerException("input data can't be null");
        }
        validateContactDetails(inputData.getManager());
        if (inputData.getRequisitionId() == null || inputData.getRequisitionId().trim().isEmpty()) {
            throw new NullPointerException("requisition number is required field");
        }
    }
    public void validateContactDetails(RequisitionContactDetailsInputDS contactDetails){
        if (contactDetails == null){
            throw new NullPointerException("manager must fill the profile. it can't be null");
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
        if (contactDetails.getRole() == null || contactDetails.getRole().trim().isEmpty()){
            throw new NullPointerException("role is required field. can't be null or empty.");
        }
    }
}
