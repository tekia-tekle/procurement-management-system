package com.bizeff.procurement.domaininterfaces.inputds.purchaseorder;

public class SendPurchaseOrderToSupplierInputDS {
    private PurchaseOrderContactDetailsInputDS purchaseOrderSenderDetail;
    private String supplierId;

    public SendPurchaseOrderToSupplierInputDS(PurchaseOrderContactDetailsInputDS purchaseOrderSenderDetail,
                                              String supplierId) {
        this.purchaseOrderSenderDetail = purchaseOrderSenderDetail;
        this.supplierId = supplierId;
    }

    public PurchaseOrderContactDetailsInputDS getPurchaseOrderSenderDetail() {
        return purchaseOrderSenderDetail;
    }
    public void setPurchaseOrderSenderDetail(PurchaseOrderContactDetailsInputDS purchaseOrderSenderDetail) {
        this.purchaseOrderSenderDetail = purchaseOrderSenderDetail;
    }
    public String getSupplierId() {
        return supplierId;
    }
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "SendPurchaseOrderToSupplierInputDS{" +
                "purchaseOrderSenderDetail=" + purchaseOrderSenderDetail +
                ", supplier Id ='" + supplierId + '\'' +
                '}';
    }
}