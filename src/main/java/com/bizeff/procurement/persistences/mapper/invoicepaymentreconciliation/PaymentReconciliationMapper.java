package com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation;

import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.PaymentReconciliationEntity;
import com.bizeff.procurement.persistences.mapper.purchaseorder.PurchaseOrderMapper;

public class PaymentReconciliationMapper {
    public static PaymentReconciliationEntity toEntity(PaymentReconciliation paymentReconciliation) {
        if (paymentReconciliation == null) return null;

        PaymentReconciliationEntity entity = new PaymentReconciliationEntity();

        entity.setPaymentId(paymentReconciliation.getPaymentId());
        entity.setInvoice(InvoiceMapper.toEntity(paymentReconciliation.getInvoice()));
        entity.setPurchaseOrder(PurchaseOrderMapper.toEntity(paymentReconciliation.getPurchaseOrder()));
        entity.setDeliveryReceipt(DeliveryReceiptsMapper.toEntity(paymentReconciliation.getDeliveryReceipt()));
        entity.setInvoiceAmount(paymentReconciliation.getInvoiceAmount());
        entity.setExpectedAmount(paymentReconciliation.getExpectedAmount());
        entity.setActualPaidAmount(paymentReconciliation.getActualPaidAmount());
        entity.setDiscrepancyAmount(paymentReconciliation.getDiscrepancyAmount());
        entity.setCurrency(paymentReconciliation.getCurrency());
        entity.setPaymentDate(paymentReconciliation.getPaymentDate());
        entity.setReconciliationDate(paymentReconciliation.getReconciliationDate());
        entity.setReconciliationStatus(paymentReconciliation.getReconciliationStatus());

        return entity;
    }

    public static PaymentReconciliation toModel(PaymentReconciliationEntity paymentReconciliationEntity) {
        if (paymentReconciliationEntity == null) {
            return null;
        }
        PaymentReconciliation paymentReconciliation =  new PaymentReconciliation();

        paymentReconciliation.setPaymentId(paymentReconciliationEntity.getPaymentId());
        paymentReconciliation.setInvoice(InvoiceMapper.toModel(paymentReconciliationEntity.getInvoice()));
        paymentReconciliation.setPurchaseOrder(PurchaseOrderMapper.toModel(paymentReconciliationEntity.getPurchaseOrder()));
        paymentReconciliation.setDeliveryReceipt(DeliveryReceiptsMapper.toModel(paymentReconciliationEntity.getDeliveryReceipt()));
        paymentReconciliation.setInvoiceAmount(paymentReconciliationEntity.getInvoiceAmount());
        paymentReconciliation.setExpectedAmount(paymentReconciliationEntity.getExpectedAmount());
        paymentReconciliation.setActualPaidAmount(paymentReconciliationEntity.getActualPaidAmount());
        paymentReconciliation.setDiscrepancyAmount(paymentReconciliationEntity.getDiscrepancyAmount());
        paymentReconciliation.setCurrency(paymentReconciliationEntity.getCurrency());
        paymentReconciliation.setPaymentDate(paymentReconciliationEntity.getPaymentDate());
        paymentReconciliation.setReconciliationDate(paymentReconciliationEntity.getReconciliationDate());
        paymentReconciliation.setReconciliationStatus(paymentReconciliationEntity.getReconciliationStatus());

        return paymentReconciliation;
    }
}
