package com.bizeff.procurement.usecases.purchaseorder;

import com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder.SendPurchaseOrderToSupplierInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.SendPurchaseOrderToSupplierInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.SendPurchaseOrderToSupplierOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.SendPurchaseOrderToSupplierOutPutDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.services.purchaseorder.PurchaseOrderEnsuringServices;

import java.util.ArrayList;
import java.util.List;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateString;

public class SendPurchaseOrderToSupplierUseCase implements SendPurchaseOrderToSupplierInputBoundary {

    private PurchaseOrderRepository purchaseOrderRepository;
    private SupplierRepository supplierRepository;
    private PurchaseOrderEnsuringServices purchaseOrderEnsuringServices;
    private SendPurchaseOrderToSupplierOutputBoundary sendPurchaseOrderToSupplierOutputBoundary;

    public SendPurchaseOrderToSupplierUseCase(
            PurchaseOrderRepository purchaseOrderRepository,
            SupplierRepository supplierRepository,
            PurchaseOrderEnsuringServices purchaseOrderEnsuringServices,
            SendPurchaseOrderToSupplierOutputBoundary sendPurchaseOrderToSupplierOutputBoundary
    ) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.purchaseOrderEnsuringServices = purchaseOrderEnsuringServices;
        this.sendPurchaseOrderToSupplierOutputBoundary = sendPurchaseOrderToSupplierOutputBoundary;
    }

    @Override
    public List<SendPurchaseOrderToSupplierOutPutDS> sendPurchaseOrderToSupplier(SendPurchaseOrderToSupplierInputDS input){
        validateInputDS(input);
        Supplier supplier = supplierRepository.findBySupplierId(input.getSupplierId()).orElseThrow(() -> new IllegalArgumentException("Supplier not found with the given ID."));

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findBySupplierId(supplier.getSupplierId());
        if (purchaseOrders == null || purchaseOrders.isEmpty()) {
            throw new IllegalArgumentException("No purchase orders found for the given supplier.");
        }

        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            purchaseOrderEnsuringServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);
        }
        List<PurchaseOrder> purchaseOrderList = purchaseOrderEnsuringServices.findBySupplierId(supplier.getSupplierId());
        if (purchaseOrderList == null || purchaseOrderList.isEmpty()) {
            throw new IllegalArgumentException("No purchase orders found for the given supplier.");
        }

        List<SendPurchaseOrderToSupplierOutPutDS> outputDSList = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrderList) {
            if (!purchaseOrder.getPurchaseOrderStatus().equals(PurchaseOrderStatus.APPROVED)) {
                throw new IllegalArgumentException("Purchase order is not approved. Cannot be sent to supplier.");
            }
            purchaseOrder.setApproved(true);
            SendPurchaseOrderToSupplierOutPutDS outputDS = new SendPurchaseOrderToSupplierOutPutDS(
                    purchaseOrder.getOrderId(),
                    purchaseOrder.getSupplier().getSupplierId(),
                    purchaseOrder.getOrderDate());
            outputDSList.add(outputDS);
        }
        sendPurchaseOrderToSupplierOutputBoundary.presentElectronicallyTransferredPurchaseOrders(outputDSList);
        return outputDSList;
    }
    public void validateInputDS(SendPurchaseOrderToSupplierInputDS input){
        if (input == null){
            throw new NullPointerException("input data is required. we can't create purchase order with null elements.");
        }

        if (input.getPurchaseOrderSenderDetail() == null){
            throw new IllegalArgumentException("purchaseOrder's vendorPersonDetail is required field");
        }
        validateString(input.getPurchaseOrderSenderDetail().getName(),"Name");
        validateString(input.getPurchaseOrderSenderDetail().getEmailAddress(),"Email");
        validateString(input.getPurchaseOrderSenderDetail().getDepartmentId(),"Department");
        validateString(input.getPurchaseOrderSenderDetail().getPhoneNumber(),"Phone Number");
        validateString(input.getPurchaseOrderSenderDetail().getRole(),"Role");
        validateString(input.getSupplierId(),"Supplier Id");
    }

}
