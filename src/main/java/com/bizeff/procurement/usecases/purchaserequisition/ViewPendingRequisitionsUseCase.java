package com.bizeff.procurement.usecases.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition.ViewPendingRequisitionsInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.ViewPendingRequisitionsOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.ViewPendingRequisitionsOutputData;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.models.enums.RequisitionStatus;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionTrackingStatusService;

import java.util.ArrayList;
import java.util.List;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateString;

public class ViewPendingRequisitionsUseCase implements ViewPendingRequisitionsInputBoundary {
    private PurchaseRequisitionRepository purchaseRequisitionRepository;
    private ViewPendingRequisitionsOutputBoundary viewPendingRequisitionsOutputBoundary;
    private PurchaseRequisitionTrackingStatusService purchaseRequisitionTrackingStatusServices;
    public ViewPendingRequisitionsUseCase(PurchaseRequisitionRepository purchaseRequisitionRepository,
                                          ViewPendingRequisitionsOutputBoundary viewPendingRequisitionsOutputBoundary,
                                          PurchaseRequisitionTrackingStatusService purchaseRequisitionTrackingStatusServices)
    {
        this.purchaseRequisitionRepository = purchaseRequisitionRepository;
        this.viewPendingRequisitionsOutputBoundary = viewPendingRequisitionsOutputBoundary;
        this.purchaseRequisitionTrackingStatusServices = purchaseRequisitionTrackingStatusServices;
    }
    @Override
    public List<ViewPendingRequisitionsOutputData> viewAllPendingRequisition(String departmentId) {
        // Validate departmentId
        validateString(departmentId, "Department ID");
        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        if (requisitions.isEmpty()) {
            throw new IllegalArgumentException("No purchase requisitions found in the repository.");
        }
        List<PurchaseRequisition> departmentWiseRequisitions = purchaseRequisitionRepository.findByDepartmentEntityDepartmentId(departmentId);

        List<ViewPendingRequisitionsOutputData> outputDS = new ArrayList<>();

        for (PurchaseRequisition requisition : departmentWiseRequisitions) {
            Department department = requisition.getDepartment();
            purchaseRequisitionTrackingStatusServices.getDepartments().add(department);
            purchaseRequisitionTrackingStatusServices.addCostCenterToDepartment(department.getDepartmentId(), requisition.getCostCenter());
            purchaseRequisitionTrackingStatusServices.getAllRequisitions().put(requisition.getRequisitionId(),requisition);

            // Track status and update requisition
            PurchaseRequisition trackedRequisition = purchaseRequisitionTrackingStatusServices.trackRequisitionStatus(requisition.getRequisitionId());
            // Save the updated requisition
            PurchaseRequisition savedRequisition = purchaseRequisitionRepository.update(trackedRequisition);

            // If it's still pending, add to output
            if (savedRequisition.getRequisitionStatus() == RequisitionStatus.PENDING) {

                ViewPendingRequisitionsOutputData viewPendingRequisitionsOutputData = new ViewPendingRequisitionsOutputData(
                        savedRequisition.getRequisitionId(),
                        savedRequisition.getRequisitionNumber(),
                        savedRequisition.getRequisitionStatus(),
                        savedRequisition.getRequisitionDate(),
                        savedRequisition.getPriorityLevel(),
                        savedRequisition.getExpectedDeliveryDate());

                outputDS.add(viewPendingRequisitionsOutputData);
            }
        }

        viewPendingRequisitionsOutputBoundary.presentPendingRequisition(outputDS);

        return outputDS;
    }

}