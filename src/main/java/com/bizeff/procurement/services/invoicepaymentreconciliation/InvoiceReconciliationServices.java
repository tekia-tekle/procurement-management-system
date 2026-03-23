package com.bizeff.procurement.services.invoicepaymentreconciliation;

import com.bizeff.procurement.models.enums.DeliveryStatus;
import com.bizeff.procurement.models.invoicepaymentreconciliation.*;
import com.bizeff.procurement.models.purchaseorder.OrderedItem;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.validatePositiveValue;
import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.*;
import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.*;

public class InvoiceReconciliationServices {

    private Map<String, PaymentReconciliation> paymentReconciliationMap;
    private Map<String, Invoice> invoiceMap;
    private Map<String, DeliveryReceipt> deliveryReceiptMap;
    private Map<String, PurchaseOrder> purchaseOrderMap;

    public InvoiceReconciliationServices() {
        this.paymentReconciliationMap = new HashMap<>();
        this.invoiceMap = new HashMap<>();
        this.deliveryReceiptMap = new HashMap<>();
        this.purchaseOrderMap = new HashMap<>();
    }

    public Invoice addInvoice(Invoice invoice) {
        validateInvoice(invoice);
        invoiceMap.put(invoice.getInvoiceId(), invoice);
        return invoice;
    }

    public PurchaseOrder addPurchaseOrder(PurchaseOrder purchaseOrder) {
        validatePurchaseOrder(purchaseOrder);
        purchaseOrderMap.put(purchaseOrder.getOrderId(), purchaseOrder);
        return purchaseOrder;
    }

    public DeliveryReceipt addDeliveryReceipt(DeliveryReceipt deliveryReceipt) {
        validateDeliveryReceipts(deliveryReceipt);

        boolean isPartial = deliveryReceipt.getReceivedItems().stream()
                .anyMatch(receivedItem -> {
                    String itemId = receivedItem.getInventory().getItemId();
                    Optional<OrderedItem> poItemOpt = deliveryReceipt.getPurchaseOrder().getOrderedItems().stream()
//                            .flatMap(req -> req.getItems().stream())
                            .filter(item -> item.getInventory().getItemId().equals(itemId))
                            .findFirst();

                    return poItemOpt.map(poItem -> receivedItem.getDeliveredQuantity() < poItem.getOrderedQuantity())
                            .orElse(false);
                });

        deliveryReceipt.setDeliveryStatus(isPartial ? DeliveryStatus.PARTIAL : DeliveryStatus.DELIVERED);

        deliveryReceiptMap.put(deliveryReceipt.getReceiptId(), deliveryReceipt);
        return deliveryReceipt;
    }

    public PaymentReconciliation reconcileInvoice(String invoiceId, String orderId, String receiptId, LocalDate reconciliationDate) {
        /** Validating the id. */
        validateString(invoiceId,"Invoice Id");
        validateString(orderId,"Order Id");
        validateString(receiptId,"Receipt Id");
        validateDateNotInPast(reconciliationDate, "Reconciliation Date");

        /** getting ById .*/

        Invoice invoice = getInvoiceById(invoiceId);
        PurchaseOrder purchaseOrder = getPurchaseOrderById(orderId);
        DeliveryReceipt deliveryReceipt = getDeliveryReceiptById(receiptId);

        /** Validating Each other among po, invoice, delivery receipt whether they are in the same supplier or not. */

        validateInvoiceAndPurchaseOrder(invoice,purchaseOrder);
        validateInvoiceAndDelivery(invoice,deliveryReceipt);

        if (invoice.getTotalCosts().compareTo(purchaseOrder.getTotalCost()) < 0) {
            List<String> deliveredItemIds = deliveryReceipt.getReceivedItems().stream().map(deliveredItem -> deliveredItem.getInventory().getItemId()).collect(Collectors.toList());

            for (String orderedItemId : getOrderItemIds(purchaseOrder)) {
                if (!deliveredItemIds.contains(orderedItemId)) {
                    throw new IllegalArgumentException("All ordered items must be delivered when invoice has a discount. Missing item: " + orderedItemId);
                }
            }
        }

        BigDecimal expectedAmount = calculateExpectedAmount(deliveryReceipt, invoice);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation(invoice, purchaseOrder, deliveryReceipt, expectedAmount,deliveryReceipt.getTotalCost(), invoice.getCurrency(), invoice.getPaymentDate(), reconciliationDate);

        // Register and return
        paymentReconciliationMap.put(paymentReconciliation.getPaymentId(), paymentReconciliation);
        return paymentReconciliation;
    }
    private  BigDecimal calculateExpectedAmount(DeliveryReceipt receipt, Invoice invoice) {
        BigDecimal invoiceSubtotal = invoice.getSubtotal();
        // Match only items delivered and invoiced
        BigDecimal deliverySubtotal = receipt.getReceivedItems().stream()
                .filter(delItem -> invoice.getInvoiceItems().stream().anyMatch(invItem -> invItem.getInventory().getItemId().equals(delItem.getInventory().getItemId())))
                .map(item -> item.getTotalPrice() != null ? item.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (invoiceSubtotal.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        BigDecimal ratio = deliverySubtotal.divide(invoiceSubtotal, 4, BigDecimal.ROUND_HALF_UP);
        return deliverySubtotal
                .add(invoice.getShippingCost().multiply(ratio))
                .add(invoice.getTaxes().multiply(ratio))
                .subtract(invoice.getDiscount().multiply(ratio));
    }
    private List<String> getOrderItemIds(PurchaseOrder po) {
        return po.getRequisitionList().stream()
                .flatMap(req -> req.getItems().stream())
                .map(requestedItem -> requestedItem.getInventory().getItemId())
                .collect(Collectors.toList());
    }

    private void validateInvoiceAndPurchaseOrder(Invoice invoice, PurchaseOrder po) {

        if (!invoice.getPurchaseOrder().getOrderId().equals(po.getOrderId())) {
            throw new IllegalArgumentException("Invoice does not match the given Purchase Order.");
        }
        if (!invoice.getSupplier().getSupplierId().equals(po.getSupplier().getSupplierId())){
            throw new IllegalArgumentException("the supplier in the invoice is not the same with the supplier in the purchase order.");
        }
        List<String> orderItemIds = getOrderItemIds(po);

        //  Invoice must not contain items not in the PO
        for (InvoicedItem invoiceItem : invoice.getInvoiceItems()) {
            if (!orderItemIds.contains(invoiceItem.getInventory().getItemId())) {
                throw new IllegalArgumentException("Invoice contains item not ordered. ID: " + invoiceItem.getInventory().getItemId());
            }
        }

    }
    private void validateInvoiceAndDelivery(Invoice invoice, DeliveryReceipt deliveryReceipt) {
        if (!invoice.getPurchaseOrder().getOrderId().equals(deliveryReceipt.getPurchaseOrder().getOrderId())) {
            throw new IllegalArgumentException("Delivery Receipt does not match the Invoice's Purchase Order.");
        }
        if (!invoice.getSupplier().getSupplierId().equals(deliveryReceipt.getSupplier().getSupplierId())){
            throw new IllegalArgumentException("the supplier in the invocie doesn't match with the supplier in the delivery receipts.");
        }
        List<String> invoiceItemIds = invoice.getInvoiceItems().stream()
                .map(invoicedItem -> invoicedItem.getInventory().getItemId())
                .collect(Collectors.toList());

        for (DeliveredItem deliveredItem : deliveryReceipt.getReceivedItems()) {
            if (!invoiceItemIds.contains(deliveredItem.getInventory().getItemId())) {
                throw new IllegalArgumentException("Delivery contains item not invoiced. ID: " + deliveredItem.getInventory().getItemId());
            }
        }

        // Quantities in delivery can be less than invoiced (partial), but not more
        for (DeliveredItem deliveryItem : deliveryReceipt.getReceivedItems()) {
            String itemId = deliveryItem.getInventory().getItemId();
            int invoiceQty = invoice.getInvoiceItems().stream()
                    .filter(item -> item.getInventory().getItemId().equals(itemId))
                    .mapToInt(InvoicedItem::getInvoicedQuantity)
                    .sum();

            if (deliveryItem.getDeliveredQuantity() > invoiceQty) {
                throw new IllegalArgumentException("Over-delivery not allowed for item " + itemId +
                        ". Invoiced: " + invoiceQty +
                        ", Delivered: " + deliveryItem.getDeliveredQuantity());
            }
        }
    }
    public void validateInvoice(Invoice invoice){
        validateDate(invoice.getInvoiceDate(), " Invoice Date");
        validateNotNull(invoice.getSupplier(),"SupplierModel");
        validatePurchaseOrder(invoice.getPurchaseOrder());
        validateDate(invoice.getDueDate(),"Due Date");
        validateDate(invoice.getPaymentDate(), "Payment Date");
        validatePositiveValue(invoice.getDiscount(), "Discount");
        validatePositiveValue(invoice.getShippingCost(), "Shipping Costs");
        validatePositiveValue(invoice.getTaxes(), "taxes");
        validateNotNull(invoice.getCreatedBy(),"User");

    }
    public void validateDeliveryReceipts(DeliveryReceipt deliveryReceipts){
        validateNotNull(deliveryReceipts.getSupplier(),"SupplierModel");
        validatePurchaseOrder(deliveryReceipts.getPurchaseOrder());
        validateNotEmptyList(deliveryReceipts.getReceivedItems(),"Received Items");
        validatePositiveValue(deliveryReceipts.getTotalCost(),"deliveryReceipts TotalCosts");
        validateDate(deliveryReceipts.getDeliveryDate(), "Delivered Date.");
    }
    public Invoice getInvoiceById(String invoiceId) {
        if (invoiceId == null || invoiceId.isBlank()) {
            throw new IllegalArgumentException("Invoice ID cannot be null or empty.");
        }
        return Optional.ofNullable(invoiceMap.get(invoiceId))
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + invoiceId));
    }

    public PurchaseOrder getPurchaseOrderById(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty.");
        }
        return Optional.ofNullable(purchaseOrderMap.get(orderId))
                .orElseThrow(() -> new IllegalArgumentException("Purchase Order not found: " + orderId));
    }

    public DeliveryReceipt getDeliveryReceiptById(String receiptId) {
        if (receiptId == null || receiptId.isBlank()) {
            throw new IllegalArgumentException("Receipt ID cannot be null or empty.");
        }
        return Optional.ofNullable(deliveryReceiptMap.get(receiptId))
                .orElseThrow(() -> new IllegalArgumentException("Delivery Receipt not found: " + receiptId));
    }

    public Map<String, PaymentReconciliation> getPaymentReconciliationMap() {
        return paymentReconciliationMap;
    }

    public Map<String, Invoice> getInvoiceMap() {
        return invoiceMap;
    }

    public Map<String, DeliveryReceipt> getDeliveryReceiptMap() {
        return deliveryReceiptMap;
    }

    public Map<String, PurchaseOrder> getPurchaseOrderMap() {
        return purchaseOrderMap;
    }
}