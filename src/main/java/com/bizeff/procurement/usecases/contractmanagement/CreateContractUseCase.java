package com.bizeff.procurement.usecases.contractmanagement;

import com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement.CreateContractInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.contractsmanagement.ContractsContactDetailsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.contractsmanagement.CreateContractInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.CreateContractOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.CreateContractOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.enums.ContractStatus;
import com.bizeff.procurement.models.enums.DeliveryTerms;
import com.bizeff.procurement.models.enums.PaymentTerms;
import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.services.contract.StoreAndTrackContractServices;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.*;

public class CreateContractUseCase implements CreateContractInputBoundary {
    private ContractRepository contractRepository;
    private StoreAndTrackContractServices storeAndTrackContractServices;
    private SupplierRepository supplierRepository;
    private PurchaseOrderRepository purchaseOrderRepository;
    private CreateContractOutputBoundary createContractOutputBoundary;
    public CreateContractUseCase(ContractRepository contractRepository,
                                 SupplierRepository supplierRepository,
                                 PurchaseOrderRepository purchaseOrderRepository,
                                 StoreAndTrackContractServices storeAndTrackContractServices,
                                 CreateContractOutputBoundary createContractOutputBoundary)
    {
        this.contractRepository = contractRepository;
        this.supplierRepository = supplierRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.storeAndTrackContractServices = storeAndTrackContractServices;
        this.createContractOutputBoundary = createContractOutputBoundary;
    }
    @Override
    public CreateContractOutputDS createContracts(CreateContractInputDS inputData) {
        validateInputDS(inputData);

        String contractManger = inputData.getManagersDetails().getName();
        Supplier supplier = supplierRepository.findBySupplierId(inputData.getSupplierId()).orElseThrow(()->new IllegalArgumentException("SupplierModel Doesn't found."));

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(),supplier);
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>();

        for (String orderId: inputData.getPurchaseOrderIdLists()){
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findByOrderId(orderId).orElseThrow(()-> new IllegalArgumentException("purchase order doesn't found."));
            if (!purchaseOrder.getSupplier().getSupplierId().equals(supplier.getSupplierId())){ //mismatching in the linking of supplier and purchase order.
                throw new IllegalArgumentException("the purchase order with id "+ purchaseOrder.getOrderId() + " is not linked with "+ supplier.getSupplierId());
            }
            storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
            purchaseOrderList.add(purchaseOrder);
        }
        List<PurchaseOrder> approvedPurchaseOrders = new ArrayList<>();
        for (PurchaseOrder purchaseOrder: purchaseOrderList){
            if (purchaseOrder.getPurchaseOrderStatus() != PurchaseOrderStatus.APPROVED) {
                throw new IllegalArgumentException("Purchase order with ID " + purchaseOrder.getOrderId() + " is not approved.");
            }
            approvedPurchaseOrders.add(purchaseOrder);
        }
        PaymentTerms paymentTerms = PaymentTerms.valueOf(inputData.getPaymentTerms().name());
        DeliveryTerms deliveryTerms = DeliveryTerms.valueOf(inputData.getDeliveryTerms().name());

        Contract contract = storeAndTrackContractServices.createContract(
                inputData.getContractTitle(), supplier, inputData.getStartDate(),
                inputData.getEndDate(), paymentTerms, deliveryTerms, inputData.getTotalCosts(),
                inputData.isRenewable(),approvedPurchaseOrders,inputData.getAttachments(),LocalDate.now());

        Contract savedContract = contractRepository.save(contract);

        ContractStatus contractStatus = ContractStatus.valueOf(savedContract.getStatus().name());

        List<String> purchaseOrderIds = new ArrayList<>();
        for (PurchaseOrder purchaseOrder: savedContract.getPurchaseOrders()){
            String orderId = purchaseOrder.getOrderId();
            purchaseOrderIds.add(orderId);
        }

        CreateContractOutputDS output = new CreateContractOutputDS(
                savedContract.getContractId(),
                savedContract.getContractTitle(),
                savedContract.getStartDate(),
                savedContract.getEndDate(),
                savedContract.getPaymentTerms(),
                savedContract.getDeliveryTerms(),
                contractStatus,
                savedContract.isRenewable(),
                savedContract.getSupplier().getSupplierId(),
                purchaseOrderIds,
                savedContract.getAttachments(),
                savedContract.getCreatedDate(),
                contractManger);

        createContractOutputBoundary.presentContracts(output);

        return output;
    }

    public void validateInputDS(CreateContractInputDS inputData){
        if (inputData == null){
            throw new NullPointerException("input can't be null value.");
        }
        validateContractManagerDetails(inputData.getManagersDetails());
        validateString(inputData.getSupplierId(),"Supplier ID");
        validateString(inputData.getContractTitle(),"Contract Title");
        validateDateNotInPast(inputData.getStartDate(),"Start Date");
        validateDateNotInPast(inputData.getEndDate(),"End Date");
        validatePositiveValue(inputData.getTotalCosts(),"Contract Total Cost");
        validateNotNull(inputData.getPaymentTerms(),"Payment Terms");
        validateNotNull(inputData.getDeliveryTerms(),"Delivery Terms");
        validateNotEmptyList(inputData.getPurchaseOrderIdLists(),"Purchase order Id Lists");
        validateNotEmptyList(inputData.getAttachments(),"Contract Attachements");

    }
    public static void validateContractManagerDetails(ContractsContactDetailsInputDS contactDetailsInputDS){
        if (contactDetailsInputDS == null){
            throw new IllegalArgumentException("contact detail inputDS can't be null.");
        }
        if (contactDetailsInputDS.getName() == null || contactDetailsInputDS.getName().trim().isEmpty()){
            throw new IllegalArgumentException("name couldn't be null or empty.");
        }
        if (contactDetailsInputDS.getEmailAddress() == null || contactDetailsInputDS.getEmailAddress().trim().isEmpty()){
            throw new IllegalArgumentException("Email address can't be empty or null");
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!contactDetailsInputDS.getEmailAddress().matches(emailRegex)){
            throw new IllegalArgumentException("please Enter valid email address.based on the emailRegex = \"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\";\n");
        }

        if (contactDetailsInputDS.getPhoneNumber() == null || contactDetailsInputDS.getPhoneNumber().trim().isEmpty()){
            throw new IllegalArgumentException("Phone number can't be null or empty.");
        }

        String phoneRegex = "^\\+\\d{1,3}\\d{4,14}$";
        if (!contactDetailsInputDS.getPhoneNumber().matches(phoneRegex)){
            throw new IllegalArgumentException("Please Enter valid phone number with country code.");
        }

        if (contactDetailsInputDS.getRole() == null || contactDetailsInputDS.getRole().trim().isEmpty()){
            throw new IllegalArgumentException("Role can't be null or Empty.");
        }

    }
    public static void validateNotEmptyList(List<?> list, String fieldName) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty.");
        }
    }
    public static void validateDateNotInPast(LocalDate date, String fieldName) {
        validateDate(date,fieldName);
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(fieldName + " must not be in the past.");
        }
    }
    public static void validatePositiveValue(BigDecimal value, String fieldName) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(fieldName + " must be a positive value.");
        }
    }
}
