package com.bizeff.procurement.webapi.presenter.purchaseorder;

import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.SendPurchaseOrderToSupplierOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.SendPurchaseOrderToSupplierOutPutDS;
import com.bizeff.procurement.webapi.viewmodel.purchaseorder.SendPurchaseOrderToSupplierViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SendPurchaseOrderToSupplierPresenter implements SendPurchaseOrderToSupplierOutputBoundary {

    private List<SendPurchaseOrderToSupplierViewModel> sendPurchaseOrderToSupplierViewModels;

    public SendPurchaseOrderToSupplierPresenter(List<SendPurchaseOrderToSupplierViewModel> sendPurchaseOrderToSupplierViewModels) {
        this.sendPurchaseOrderToSupplierViewModels = sendPurchaseOrderToSupplierViewModels;
    }

    @Override
    public void presentElectronicallyTransferredPurchaseOrders(List<SendPurchaseOrderToSupplierOutPutDS> outPutDS) {
        List<SendPurchaseOrderToSupplierViewModel> viewModels = new ArrayList<>();
        for (SendPurchaseOrderToSupplierOutPutDS ds : outPutDS) {
            SendPurchaseOrderToSupplierViewModel viewModel = new SendPurchaseOrderToSupplierViewModel(
                    ds.getOrderId(),
                    ds.getSupplierId(),
                    ds.getDateOfSent().toString()
            );
            viewModels.add(viewModel);
        }
        this.sendPurchaseOrderToSupplierViewModels = viewModels;
    }
    public List<SendPurchaseOrderToSupplierViewModel> getSendPurchaseOrderToSupplierViewModels() {
        return sendPurchaseOrderToSupplierViewModels;
    }

    @Override
    public String toString() {
        return "SendPurchaseOrderToSupplierPresenter{" +
                "sendPurchaseOrderToSupplierViewModels=" + sendPurchaseOrderToSupplierViewModels +
                '}';
    }

}