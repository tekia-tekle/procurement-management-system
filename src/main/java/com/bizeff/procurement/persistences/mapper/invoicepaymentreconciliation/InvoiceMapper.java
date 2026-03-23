package com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation;

import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;
import com.bizeff.procurement.models.invoicepaymentreconciliation.InvoicedItem;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoiceEntity;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoicedItemEntity;
import com.bizeff.procurement.persistences.mapper.purchaseorder.PurchaseOrderMapper;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.UserMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.InventoryMapper;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.SupplierMapper;

import java.util.List;
import java.util.stream.Collectors;

public class InvoiceMapper {
    public static InvoiceEntity toEntity(Invoice invoice){
        if (invoice == null)return null;
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        if (invoice.getId() != null){
            invoiceEntity.setId(invoice.getId());
        }

        invoiceEntity.setInvoiceId(invoice.getInvoiceId());
        invoiceEntity.setInvoiceDate(invoice.getInvoiceDate());
        invoiceEntity.setDueDate(invoice.getDueDate());
        invoiceEntity.setSupplier(SupplierMapper.toEntity(invoice.getSupplier()));
        invoiceEntity.setPurchaseOrder(PurchaseOrderMapper.toEntity(invoice.getPurchaseOrder()));
        List<InvoicedItemEntity> invoicedItemEntities = invoice.getInvoiceItems().stream()
                .map(invoicedItem -> {
                    InvoicedItemEntity invoicedItemEntity = InvoicedItemMapper.toEntity(invoicedItem, InventoryMapper.toEntity(invoicedItem.getInventory()));
                    invoicedItemEntity.setInvoice(invoiceEntity);
                    return invoicedItemEntity;
                }).collect(Collectors.toList());

        invoiceEntity.setInvoiceItems(invoicedItemEntities);
        invoiceEntity.setDiscount(invoice.getDiscount());
        invoiceEntity.setShippingCost(invoice.getShippingCost());
        invoiceEntity.setTaxes(invoice.getTaxes());
        invoiceEntity.setTotalCosts(invoice.getTotalCosts());
        invoiceEntity.setCurrency(invoice.getCurrency());
        invoiceEntity.setPaymentDate(invoice.getPaymentDate());
        invoiceEntity.setCreatedBy(UserMapper.toEntity(invoice.getCreatedBy()));
        invoiceEntity.setDescription(invoice.getDescription());

        return invoiceEntity;
    }

    public static Invoice toModel(InvoiceEntity invoiceEntity){
        if (invoiceEntity == null) return null;
        Invoice invoice =  new Invoice();
        invoice.setId(invoiceEntity.getId());
        invoice.setInvoiceId(invoiceEntity.getInvoiceId());
        invoice.setInvoiceDate(invoiceEntity.getInvoiceDate());
        invoice.setDueDate(invoiceEntity.getDueDate());
        invoice.setSupplier(SupplierMapper.toModel(invoiceEntity.getSupplier()));
        invoice.setPurchaseOrder(PurchaseOrderMapper.toModel(invoiceEntity.getPurchaseOrder()));

        List<InvoicedItem> invoicedItemList = invoiceEntity.getInvoiceItems().stream()
                .map(invoicedItemEntity -> InvoicedItemMapper.toModel(invoicedItemEntity)).collect(Collectors.toList());

        invoice.setInvoiceItems(invoicedItemList);
        invoice.setDiscount(invoiceEntity.getDiscount());
        invoice.setShippingCost(invoiceEntity.getShippingCost());
        invoice.setTaxes(invoiceEntity.getTaxes());
        invoice.setTotalCosts(invoiceEntity.getTotalCosts());
        invoice.setCurrency(invoiceEntity.getCurrency());
        invoice.setPaymentDate(invoiceEntity.getPaymentDate());
        invoice.setCreatedBy(UserMapper.toDomain(invoiceEntity.getCreatedBy()));
        invoice.setDescription(invoiceEntity.getDescription());

        return invoice;
    }
}
