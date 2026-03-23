package com.bizeff.procurement.usecases.contractmanagement;

import com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement.ViewContractDetailsInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.CreatePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.ViewContractDetailOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.ViewContractDetailOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.DepartmentRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.services.contract.StoreAndTrackContractServices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.validateNotEmptyList;
import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.validatePurchaseOrder;
import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.*;

// this is the use case for viewing contract details when creating a purchase order.
public class ViewContractDetailsUseCase implements ViewContractDetailsInputBoundary {
    private ContractRepository contractRepository;
    private DepartmentRepository departmentRepository;
    private PurchaseRequisitionRepository requisitionRepository;
    private SupplierRepository supplierRepository;
    private PurchaseOrderRepository purchaseOrderRepository;
    private StoreAndTrackContractServices storeAndTrackContractServices;
    private ViewContractDetailOutputBoundary viewContractDetailOutputBoundary;
    public ViewContractDetailsUseCase(
            ContractRepository contractRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            SupplierRepository supplierRepository,
            PurchaseRequisitionRepository requisitionRepository,
            DepartmentRepository departmentRepository,
            StoreAndTrackContractServices storeAndTrackContractServices,
            ViewContractDetailOutputBoundary viewContractDetailOutputBoundary
    ) {
        this.contractRepository = contractRepository;
        this.supplierRepository = supplierRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.requisitionRepository = requisitionRepository;
        this.departmentRepository = departmentRepository;
        this.storeAndTrackContractServices = storeAndTrackContractServices;
        this.viewContractDetailOutputBoundary = viewContractDetailOutputBoundary;
    }
    @Override
    public ViewContractDetailOutputDS viewContractDetailWhenCreatingPurchaseOrder(String contractId, CreatePurchaseOrderInputDS inputDS) {
        validateString(contractId, "Contract Id");
        validateInputDS(inputDS);

        Contract savedContract = contractRepository.findByContractId(contractId).orElseThrow(() -> new IllegalArgumentException("No contract found with ID: " + contractId));
        Supplier savedSupplier = supplierRepository.findBySupplierId(inputDS.getSupplierId()).orElseThrow(() -> new IllegalArgumentException("No supplier found with ID: " + inputDS.getSupplierId()));

        Department department = departmentRepository.findByDepartmentId(inputDS.getDepartmentId()).orElseThrow(()-> new IllegalArgumentException("No department found with ID: " + inputDS.getDepartmentId()));

        List<String> requisitionIds = inputDS.getRequisitionIdLists();
        List<PurchaseRequisition> requisitionList = new ArrayList<>();
        for (String requisitionId: requisitionIds){
            PurchaseRequisition requisition = requisitionRepository.findByRequisitionId(requisitionId).orElseThrow(()-> new IllegalArgumentException("there is no requisition that is saved before with the requisitionId:"+ requisitionId));
            requisitionList.add(requisition);
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,requisitionList,inputDS.getOrderDate(),savedSupplier, inputDS.getShippingMethod(),inputDS.getDeliveryDate());
        validatePurchaseOrder(purchaseOrder);

        if (purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId()).isPresent()) {
            throw new IllegalArgumentException("Purchase Order with ID " + purchaseOrder.getOrderId() + " already exists.");
        }

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        Supplier supplier =  savedContract.getSupplier();
        List<PurchaseOrder> purchaseOrders = savedContract.getPurchaseOrders();
        storeAndTrackContractServices.getContract().put(savedContract.getContractId(), savedContract);
        Contract contract = storeAndTrackContractServices.getContractById(savedContract.getContractId());

        BigDecimal existingPurchaseOrdersCost = purchaseOrders.stream().map(PurchaseOrder::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        String message = null;
        boolean canAttach = true;
        if (!contract.getStatus().equals(ContractStatus.ACTIVE)){
            message = "contract status must be active to add a purchase order.";
            canAttach = false;
        }
        if (!savedSupplier.getSupplierId().equals(supplier.getSupplierId())){
            message = "The supplier of the purchase order must match the supplier of the contract.";
            canAttach = false;
        }
        if (purchaseOrders.stream().anyMatch(po -> po.getOrderId().equals(savedPurchaseOrder.getOrderId()))) {
            message = "Purchase Order with ID " + savedPurchaseOrder.getOrderId() + " already exists in the contract.";
            canAttach = false;

        }
        if (existingPurchaseOrdersCost.add(savedPurchaseOrder.getTotalCost()).compareTo(contract.getTotalCost()) > 0) {
            message = "The total cost of the purchase orders exceeds the contract's total cost.";
            canAttach = false;
        }
        if (savedPurchaseOrder.getDeliveryDate().isBefore(contract.getStartDate()) || savedPurchaseOrder.getDeliveryDate().isAfter(contract.getEndDate())) {
            message = "The delivery date of the purchase order must be within the contract's start and end dates.";
            canAttach = false;
        }

        if (canAttach){
            storeAndTrackContractServices.getPurchaseOrderMap().put(savedPurchaseOrder.getOrderId(), savedPurchaseOrder);
            message = "Purchase Order with ID " + savedPurchaseOrder.getOrderId() + " added to the contract.";
            contract.addPurchaseOrder(savedPurchaseOrder);
            contractRepository.update(contract);
        }
        else {
            contractRepository.update(contract);
        }

        List<String> purchaseOrderIds = new ArrayList<>();
        for (PurchaseOrder po : contract.getPurchaseOrders()) {
            purchaseOrderIds.add(po.getOrderId());
        }

        ViewContractDetailOutputDS contractDetailOutput = new ViewContractDetailOutputDS(
                contract.getContractId(),
                contract.getContractTitle(),
                contract.getStartDate(),
                contract.getEndDate(),
                contract.isRenewable(),
                contract.getPaymentTerms(),
                contract.getDeliveryTerms(),
                contract.getStatus(),
                contract.getCreatedDate(),
                contract.getSupplier().getSupplierId(),
                purchaseOrderIds,
                contract.getAttachments());
        contractDetailOutput.setMessage(message);
        viewContractDetailOutputBoundary.presentContractDetails(contractDetailOutput);
        return contractDetailOutput;
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