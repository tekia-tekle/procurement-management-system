package com.bizeff.procurement.webapi.utility;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class PermissionUrlMapping {

    public static Map<Permission, String> getPermissionToUrlMapping() {
        Map<Permission, String> map = new HashMap<>();

        // Requisition
        map.put(Permission.REQUISITION_CREATE, "/purchaserequisition/create");
        map.put(Permission.REQUISITION_TRACK, "/purchaserequisition/track");
        map.put(Permission.REQUISITION_VIEWPENDING_STATUS, "/purchaserequisition/veiwpending");

        // Purchase Orders
        map.put(Permission.PURCHASE_ORDER_CREATE, "/purchaseorder/create");
        map.put(Permission.PURCHASE_ORDER_APPROVE, "/purchaseorder/approve");

        // Supplier & Contract
        map.put(Permission.Supplier_ADD, "/supplier/add");
        map.put(Permission.CONTRACT_CREATE, "/contract/create");
        map.put(Permission.CONTRACTDETAILS_VIEW, "/contract/{contractId}/viewcontractdetails");
        map.put(Permission.CONTRACT_NOTIFY, "/contract/notify");

        // Reports
        map.put(Permission.APPROVEDPURCHASEORDERREPORT_GENERATE, "/procurementreport/generate_approved_purchaseorder");
        map.put(Permission.REPORTDATA_EXPORT, "/procurementreport/export_procurementdata");

        // Performance
        map.put(Permission.PERFORMANCEREPORT_GENERATE,"/supplierperformance/generateReport");
        map.put(Permission.SUPPLIER_PERFORMANCE_VIEW, "/supplier/view-performance");
        map.put(Permission.SUPPLIERPERFORMANCE_REVIEW,"/supplierperformance/review");
        map.put(Permission.SUPPLIERPERFORMANCEMETRICS_VIEW, "/supplierperformance/viewMetrics");

        // Finance
        map.put(Permission.INVOICE_RECONCILE, "/paymentreconciliation/reconcile");
        map.put(Permission.PAYMENTRECONCILATIONHISTORY_VIEWTOTAL, "/paymentreconciliation/viewhistory/total");
        map.put(Permission.PAYMENTRECONCILATIONHISTORY_VIEWPAYMENTDATEBASED, "/paymentreconciliation/viewhistory/paymentDateBased");
        map.put(Permission.PAYMENTRECONCILATIONHISTORY_VIEWSUPPLIER_BASED, "/paymentreconciliation/viewhistory/supplierBased");
        map.put(Permission.PAYMENTRECONCILATIONHISTORY_VIEWSTATUS_BASED, "/paymentreconciliation/viewhistory/statusBased");
        map.put(Permission.PAYMENTAPPROVE_NOTIFICATION, "/paymentreconciliation/notify");

        // Vendor
        map.put(Permission.SUPPLIER_PROFILE_UPDATE, "/supplier/update");
        map.put(Permission.PURCHASEORDER_RECEIVE, "/purchaseorder/generate-electronically");

        // Dashboard
        map.put(Permission.CUSTOMIZEDDASHBOARD_CREATE, "/procurementreport/create_customized_dashboard");

        return map;
    }
}
