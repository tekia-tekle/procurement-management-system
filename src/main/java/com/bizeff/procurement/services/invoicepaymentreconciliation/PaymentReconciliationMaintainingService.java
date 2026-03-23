package com.bizeff.procurement.services.invoicepaymentreconciliation;


import com.bizeff.procurement.models.enums.ReconciliationStatus;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;
import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;
import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService.*;
import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.*;

public class PaymentReconciliationMaintainingService {
    private Map<String,PaymentReconciliation>paymentReconciliationMap = new HashMap<>();
    private Map<String, PurchaseOrder>purchaseOrderMap = new HashMap<>();
    private Map<String, Invoice> invoiceMap = new HashMap<>();
    private Map<String,DeliveryReceipt> deliveryReceiptMap = new HashMap<>();

    public PaymentReconciliation addReconciledRecord(Invoice invoice,
                                                     PurchaseOrder purchaseOrder,
                                                     DeliveryReceipt deliveryReceipt
                                                     )
    {
        validateInvoice(invoice);
        validatePurchaseOrder(purchaseOrder);
        validateDeliveryReceipts(deliveryReceipt);
        if (!purchaseOrderMap.containsKey(purchaseOrder.getOrderId()) &&
                !invoiceMap.containsKey(invoice.getInvoiceId()) &&
                !deliveryReceiptMap.containsKey(deliveryReceipt.getReceiptId()))
        {
            throw new IllegalArgumentException("there is atleast one objact that doesn't exist in their map");
        }
        BigDecimal expectedAmount = calculateExpectedAmount(deliveryReceipt,invoice);
        PaymentReconciliation newPaymentReconciliation = new PaymentReconciliation(
                invoice,
                purchaseOrder,
                deliveryReceipt,
                expectedAmount,
                deliveryReceipt.getTotalCost(), //actual paid amount
                invoice.getCurrency(),
                invoice.getPaymentDate(),
                deliveryReceipt.getDeliveryDate() // reconciliation date. i made reconciliation date will be the same with
        );

        if (newPaymentReconciliation.getActualPaidAmount().compareTo(newPaymentReconciliation.getInvoice().getTotalCosts()) > 0){
            newPaymentReconciliation.setReconciliationStatus(ReconciliationStatus.OVERPAID);
        }
        paymentReconciliationMap.put(newPaymentReconciliation.getPaymentId(), newPaymentReconciliation);

        return newPaymentReconciliation;
    }
    public PaymentReconciliation updateReconciledPayment
            (String paymentId,
             String newInvoiceId,
             String newOrderId,
             String newReceiptId
            ) {

        PaymentReconciliation existingPayment = getReconciledRecordById(paymentId);


        Invoice invoice = getInvoiceById(newInvoiceId);
        PurchaseOrder purchaseOrder = getPurchaseOrderById(newOrderId);
        DeliveryReceipt deliveryReceipt = getDeliveryReceiptById(newReceiptId);

        existingPayment.setInvoice(invoice);
        existingPayment.setPurchaseOrder(purchaseOrder);
        existingPayment.setDeliveryReceipt(deliveryReceipt);
        existingPayment.setActualPaidAmount(deliveryReceipt.getTotalCost());
        existingPayment.setPaymentDate(invoice.getPaymentDate());

        BigDecimal discrepancyAmount = existingPayment.getInvoice().getTotalCosts().subtract(existingPayment.getActualPaidAmount());

        existingPayment.setDiscrepancyAmount(discrepancyAmount);
        existingPayment.setReconciliationDate(deliveryReceipt.getDeliveryDate());

        return existingPayment;

    }
    public PaymentReconciliation getReconciledRecordById(String paymentId) {
        validateString(paymentId,"Payment Id");

        return Optional.ofNullable(paymentReconciliationMap.get(paymentId)).orElseThrow(()->new IllegalArgumentException("there is no PaymentReconciliation record with the payment id "+paymentId));
    }
    public List<PaymentReconciliation> getRecordsByDateRange(LocalDate startDate, LocalDate endDate) {

        validateDate(startDate,"Start Date");
        validateDate(endDate,"End Date");

        if (startDate.isAfter(endDate)){
            throw new IllegalArgumentException("there is missmatching among date, start date can't be after end date.");
        }
        return paymentReconciliationMap.values().stream().filter(pr -> pr.getReconciliationDate().isAfter(startDate) && pr.getReconciliationDate().isBefore(endDate)).collect(Collectors.toList());
    }
    public List<PaymentReconciliation> getReconciledRecordsBySupplier(String supplierId) {

        validateString(supplierId,"SupplierModel Id");

        return paymentReconciliationMap.values().stream().filter(pr->pr.getInvoice().getSupplier().getSupplierId().equals(supplierId)).collect(Collectors.toList());

    }
    public List<PaymentReconciliation> getReconciledRecordsByStatus(ReconciliationStatus status) {

        validateNotNull(status,"Reconciliation Status");

        return paymentReconciliationMap.values().stream().filter(pr -> pr.getReconciliationStatus().equals(status)).collect(Collectors.toList());
    }

    public void deleteReconciledPayment(String paymentId) {
        if (paymentId == null || paymentId.trim().isEmpty()){
            throw new NullPointerException("payment id can't be null or empty");
        }

        boolean removed = paymentReconciliationMap.values().removeIf(payment -> payment.getPaymentId().equals(paymentId));
        if (!removed) {
            throw new IllegalArgumentException("Payment ID not found: " + paymentId);
        }
    }

    /** Get Invoice By Id. */
    public Invoice getInvoiceById(String invoiceId){
        validateString(invoiceId,"Invoice Id");
        return Optional.ofNullable(invoiceMap.get(invoiceId)).orElseThrow(()->new IllegalArgumentException("there is no invoice created before with the invoice id "+ invoiceId));
    }

    /** Get Delivery Receipts By Id. */
    public DeliveryReceipt getDeliveryReceiptById(String receiptId){
        validateString(receiptId,"Receipt Id");

        return Optional.ofNullable(deliveryReceiptMap.get(receiptId)).orElseThrow(()->new IllegalArgumentException("there is no delivery Receipt created before with the receipt id "+ receiptId));

    }
    public PurchaseOrder getPurchaseOrderById(String orderId){
        validateString(orderId, "Order Id");
        return Optional.ofNullable(purchaseOrderMap.get(orderId)).orElseThrow(()->new IllegalArgumentException("there is no purchase order created before with the order id "+ orderId));
    }
    public  BigDecimal calculateExpectedAmount(DeliveryReceipt receipt, Invoice invoice) {
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
    public Map<String, PaymentReconciliation> getPaymentReconciliationMap() {
        return paymentReconciliationMap;
    }

    public Map<String, PurchaseOrder> getPurchaseOrderMap() {
        return purchaseOrderMap;
    }

    public Map<String, Invoice> getInvoiceMap() {
        return invoiceMap;
    }

    public Map<String, DeliveryReceipt> getDeliveryReceiptMap() {
        return deliveryReceiptMap;
    }
    /** Find reconciliations with payment discrepancies */
    public List<PaymentReconciliation> findMismatchedAmounts() {
        return paymentReconciliationMap.values().stream()
                .filter(rec -> rec.getActualPaidAmount().compareTo(rec.getInvoice().getTotalCosts()) != 0)
                .collect(Collectors.toList());
    }

    /** Find reconciliations where payment was made after due date */
    public List<PaymentReconciliation> findLatePayments() {
        return paymentReconciliationMap.values().stream()
                .filter(rec -> rec.getInvoice().getPaymentDate().isAfter(rec.getInvoice().getDueDate()))
                .collect(Collectors.toList());
    }

    /** Generate a summary by supplier: total paid */
    public Map<String, BigDecimal> summarizeTotalPaidBySupplier() {
        Map<String, BigDecimal> summary = new HashMap<>();
        for (PaymentReconciliation rec : paymentReconciliationMap.values()) {
            String supplierName = rec.getInvoice().getSupplier().getSupplierId();
            BigDecimal amount = rec.getActualPaidAmount();

            summary.put(supplierName, summary.getOrDefault(supplierName, BigDecimal.ZERO).add(amount));
        }
        return summary;
    }


    /** ======= Validation Methodes ===== */
    public void validateInvoice(Invoice invoice){
        validateString(invoice.getInvoiceId(),"Invoice Id");
        validateDate(invoice.getInvoiceDate(), " Invoice Date");
        validateNotNull(invoice.getSupplier(),"SupplierModel");
        validatePurchaseOrder(invoice.getPurchaseOrder());
        validateDate(invoice.getDueDate(),"Due Date");
        validateDate(invoice.getPaymentDate(), "Payment Date");
        validatePositiveValue(invoice.getTotalCosts(), "Invoice Total Costs");
        validateNotNull(invoice.getCreatedBy(),"User");

    }
    public void validateDeliveryReceipts(DeliveryReceipt deliveryReceipts){
        validateString(deliveryReceipts.getReceiptId(),"Receipts Id");
        validateNotNull(deliveryReceipts.getSupplier(),"SupplierModel");
        validatePurchaseOrder(deliveryReceipts.getPurchaseOrder());
        validateNotEmptyList(deliveryReceipts.getReceivedItems(),"Received Items");
        validatePositiveValue(deliveryReceipts.getTotalCost(),"deliveryReceipts TotalCosts");
        validateNotNull(deliveryReceipts.getReceivedBy(), "User");
        validateDate(deliveryReceipts.getDeliveryDate(), "Delivered Date.");
    }
}