package com.bizeff.procurement.usecases.purchaseorder;

import com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder.CreatePurchaseOrderInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.CreatePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.CreatePurchaseOrderOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.CreatePurchaseOrderOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.DepartmentRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.services.purchaseorder.PurchaseOrderEnsuringServices;

import java.util.ArrayList;
import java.util.List;

import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.validateNotEmptyList;
import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.*;

public class CreatePurchaseOrderUseCase implements CreatePurchaseOrderInputBoundary {
    private PurchaseOrderRepository purchaseOrderRepository;
    private PurchaseRequisitionRepository purchaseRequisitionRepository;
    private DepartmentRepository departmentRepository;
    private SupplierRepository supplierRepository;
    private PurchaseOrderEnsuringServices purchaseOrderEnsuringServices;
    private CreatePurchaseOrderOutputBoundary createPurchaseOrderOutputBoundary;
    public CreatePurchaseOrderUseCase(PurchaseOrderRepository purchaseOrderRepository,
                                      PurchaseRequisitionRepository purchaseRequisitionRepository,
                                      DepartmentRepository departmentRepository,
                                      SupplierRepository supplierRepository,
                                      CreatePurchaseOrderOutputBoundary createPurchaseOrderOutputBoundary,
                                      PurchaseOrderEnsuringServices purchaseOrderEnsuringServices)
    {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseRequisitionRepository = purchaseRequisitionRepository;
        this.departmentRepository = departmentRepository;
        this.supplierRepository = supplierRepository;
        this.createPurchaseOrderOutputBoundary = createPurchaseOrderOutputBoundary;
        this.purchaseOrderEnsuringServices = purchaseOrderEnsuringServices;
    }
    @Override
    public CreatePurchaseOrderOutputDS createOrder(CreatePurchaseOrderInputDS input) {

        // Validate the input data structure
        validateInputDS(input);
        String createdBy = input.getPurchaseOrderContactDetailsInputDS().getName();
        Department department = departmentRepository.findByDepartmentId(input.getDepartmentId()).orElseThrow(() -> new IllegalArgumentException("Department not found"));

        Supplier supplier = supplierRepository.findBySupplierId(input.getSupplierId()).orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        List<PurchaseRequisition> requisitionList = new ArrayList<>();

        for (String requisitioId: input.getRequisitionIdLists()){
            PurchaseRequisition requisition = purchaseRequisitionRepository.findByRequisitionId(requisitioId).orElseThrow(()-> new IllegalArgumentException("Requisition not found"));
            requisitionList.add(requisition);
        }

        // Ensure that the purchase order can be created with the given requisition list
        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        for (PurchaseRequisition requisition: requisitionList){
            purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        }
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(),supplier);

        PurchaseOrder newPurchaseOrder = purchaseOrderEnsuringServices.createPurchaseOrder(department, requisitionList,input.getOrderDate(), supplier,input.getShippingMethod(),input.getDeliveryDate());

        // Save the purchase order to the repository
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(newPurchaseOrder);

        List<String> requisitionIds = new ArrayList<>();
        for (PurchaseRequisition requisition: savedPurchaseOrder.getRequisitionList()){
            String requisitionId =  requisition.getRequisitionId();
            requisitionIds.add(requisitionId);
        }

        CreatePurchaseOrderOutputDS createdPurchaseOrderDS = new CreatePurchaseOrderOutputDS
                (
                        savedPurchaseOrder.getOrderId(),
                        savedPurchaseOrder.getDepartment().getDepartmentId(),
                        savedPurchaseOrder.getOrderDate(),
                        requisitionIds,
                        savedPurchaseOrder.getSupplier().getSupplierId(),
                        savedPurchaseOrder.getDeliveryDate(),
                        savedPurchaseOrder.getShippingMethod(),
                        savedPurchaseOrder.getPurchaseOrderStatus(),
                        createdBy,
                        savedPurchaseOrder.getTotalCost(),
                        savedPurchaseOrder.isApproved(),
                        savedPurchaseOrder.isShipped()
                );

        createPurchaseOrderOutputBoundary.presentCreatedPurchaseOrder(createdPurchaseOrderDS);

        return createdPurchaseOrderDS;
    }
    public void validateInputDS(CreatePurchaseOrderInputDS input){
        if (input == null){
            throw new NullPointerException("input data structure can't be null.");
        }
        validateString(input.getDepartmentId(),"Department Id");
        validateNotNull(input.getPurchaseOrderContactDetailsInputDS(),"Purchase Order Contact detail");
        validateString(input.getPurchaseOrderContactDetailsInputDS().getName(),"Name");
        validateString(input.getPurchaseOrderContactDetailsInputDS().getEmailAddress(),"Email");
        validateString(input.getPurchaseOrderContactDetailsInputDS().getDepartmentId(),"Department Id");
        validateString(input.getPurchaseOrderContactDetailsInputDS().getPhoneNumber(),"Phone Number");
        validateString(input.getPurchaseOrderContactDetailsInputDS().getRole(),"Role");

        validateString(input.getSupplierId(),"SupplierId");
        validateNotEmptyList(input.getRequisitionIdLists(),"Requisition Id");
        validateDate(input.getOrderDate(),"Order Date");
        validateDate(input.getDeliveryDate(),"Delivery Date");
        validateString(input.getShippingMethod(),"Shipping Method");

    }
}
