package com.bizeff.procurement.domaininterfaces.outputds.purchaseorder;

import java.time.LocalDate;

public class SendPurchaseOrderToSupplierOutPutDS {
    private String orderId;
    private String supplierId;//
    private LocalDate dateOfSent;

    public SendPurchaseOrderToSupplierOutPutDS(String orderId,
                                               String supplierId,
                                               LocalDate dateOfSent)
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

    public LocalDate getDateOfSent() {
        return dateOfSent;
    }

    public void setDateOfSent(LocalDate dateOfSent) {
        this.dateOfSent = dateOfSent;
    }


    @Override
    public String toString() {
        return "SendPurchaseOrderToSupplierOutPutDS{" +
                "orderId='" + orderId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", date of sent =" + dateOfSent +
                '}';
    }
}