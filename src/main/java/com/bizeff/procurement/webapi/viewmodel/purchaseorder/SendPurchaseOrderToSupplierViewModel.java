package com.bizeff.procurement.webapi.viewmodel.purchaseorder;

public class SendPurchaseOrderToSupplierViewModel {
    private String orderId;
    private String supplierId;//
    private String dateOfSent;
    public SendPurchaseOrderToSupplierViewModel(){}
    public SendPurchaseOrderToSupplierViewModel(String orderId,
                                                String supplierId,
                                                String dateOfSent)
    {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.dateOfSent = dateOfSent;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
    public String getDateOfSent() {
        return dateOfSent;
    }
    public void setDateOfSent(String dateOfSent) {
        this.dateOfSent = dateOfSent;
    }

    @Override
    public String toString() {
        return "SendPurchaseOrderToSupplierViewModel{" +
                "orderId='" + orderId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", date of sent='" + dateOfSent + '\'' +
                '}';
    }
}
