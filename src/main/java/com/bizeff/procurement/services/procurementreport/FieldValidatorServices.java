package com.bizeff.procurement.services.procurementreport;

import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FieldValidatorServices {

    private static final Map<ProcurementActivity, Set<String>> ALLOWED_FIELDS = new HashMap<>();

    static {
        ALLOWED_FIELDS.put(ProcurementActivity.PURCHASE_REQUISITION, Set.of(
                "totalRequisitions",
                "totalSpendingAmount",
                "requisitionsByPriority",
                "requisitionsByDepartment",
                "requisitionsByStatus",
                "requisitionDetails",
                "requisitionItems"
        ));

        ALLOWED_FIELDS.put(ProcurementActivity.SUPPLIER_MANAGEMENT, Set.of(
                "totalSupplier",
                "activeSupplier",
                "suppliersByCategory",
                "supplierDetails",
                "existedItems"
        ));

        ALLOWED_FIELDS.put(ProcurementActivity.PURCHASE_ORDER, Set.of(
                "totalOrders",
                "totalSpendingAmount",
                "ordersByStatus",
                "ordersByDepartment",
                "totalSpendingBySupplier",
                "ordersBySupplier",
                "totalSpendingByDepartment",
                "orderDetails",
                "orderItems"
        ));

        ALLOWED_FIELDS.put(ProcurementActivity.CONTRACT_MANAGEMENT, Set.of(
                "totalContracts",
                "totalContractCosts",
                "contractsByStatus",
                "totalSpendingBySupplier",
                "contractsBySupplier",
                "contractDetails"
        ));

        ALLOWED_FIELDS.put(ProcurementActivity.INVOICE_MANAGEMENT, Set.of(
                "totalInvoices",
                "totalInvoiceAmount",
                "totalInvoiceAmountBySupplier",
                "invoicesBySupplier",
                "invoiceDetails",
                "invoiceItems"
        ));

        ALLOWED_FIELDS.put(ProcurementActivity.DELIVERY_RECEIPT, Set.of(
                "totalDeliveryReceipts",
                "totalDeliveryReceiptAmount",
                "totalDeliveryReceiptItems",
                "totalDeliveryReceiptItemsBySupplier",
                "totalDeliveryReceiptItemsByStatus",
                "totalDeliveryReceiptAmountBySupplier",
                "deliveryReceiptDetails",
                "deliveryReceiptItems"
        ));

        ALLOWED_FIELDS.put(ProcurementActivity.PAYMENT_RECONCILIATION, Set.of(
                "totalInvoicePayments",
                "totalActuallyPaidAmount",
                "totalExpectedAmount",
                "totalDiscrepancyAmount",
                "totalPaymentReconciliationStatusCount",
                "paymentReconciliationDetails"
        ));

        ALLOWED_FIELDS.put(ProcurementActivity.SUPPLIER_PERFORMANCE, Set.of(
                "totalSupplierPerformances",
                "suppliersByCurrentPerformanceRate",
                "supplierPerformanceDetails",
                "supplierPerformanceBySupplier",
                "supplierQualitativePerformanceMetricsBySupplier",
                "supplierQuantitativePerformanceMetricsBySupplier"
        ));
    }

    public static void validateFields(ProcurementActivity procurementActivity, List<String> requestedFields) {
        Set<String> allowed = ALLOWED_FIELDS.get(procurementActivity);
        for (String field : requestedFields) {
            if (!allowed.contains(field)) {
                throw new IllegalArgumentException(
                        "Field '" + field + "' is not valid for report type: " + procurementActivity
                );
            }
        }
    }

    public static Map<ProcurementActivity, Set<String>> getAllowedFields() {
        return ALLOWED_FIELDS;
    }
    public static Set<String> getFields(ProcurementActivity procurementActivity) {
        return ALLOWED_FIELDS.get(procurementActivity);
    }
}
