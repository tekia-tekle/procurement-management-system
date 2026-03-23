package com.bizeff.procurement.webapi.utility;

import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class RolePermissionServices {
    private static Map<Role, Set<Permission>> ROLE_PERMISSIONS = new HashMap<>();
    static {
        // Requester Permissions
        ROLE_PERMISSIONS.put(Role.REQUESTER, Set.of(Permission.REQUISITION_CREATE));

        // Manager Permissions
        ROLE_PERMISSIONS.put(Role.MANAGER, Set.of(Permission.REQUISITION_TRACK, Permission.PURCHASE_ORDER_APPROVE, Permission.CONTRACTDETAILS_VIEW,Permission.SUPPLIERPERFORMANCE_REVIEW, Permission.CUSTOMIZEDDASHBOARD_CREATE, Permission.CONTRACT_NOTIFY));

        // Procurement Clerk Permissions
        ROLE_PERMISSIONS.put(Role.PROCUREMENT_CLERK, Set.of(Permission.REQUISITION_VIEWPENDING_STATUS, Permission.PURCHASE_ORDER_CREATE));

        // Procurement Officer Permissions
        ROLE_PERMISSIONS.put(Role.PROCUREMENT_OFFICER, Set.of(Permission.Supplier_ADD, Permission.APPROVEDPURCHASEORDERREPORT_GENERATE));

        // Procurement Manager Permissions
        ROLE_PERMISSIONS.put(Role.PROCUREMENT_MANAGER, Set.of(Permission.CONTRACT_CREATE, Permission.SUPPLIER_PERFORMANCE_VIEW, Permission.PAYMENTRECONCILATIONHISTORY_VIEWTOTAL,Permission.PAYMENTRECONCILATIONHISTORY_VIEWPAYMENTDATEBASED,
                Permission.PAYMENTRECONCILATIONHISTORY_VIEWSTATUS_BASED, Permission.PAYMENTRECONCILATIONHISTORY_VIEWSUPPLIER_BASED,Permission.PERFORMANCEREPORT_GENERATE));

        // Accounts Officer Permissions
        ROLE_PERMISSIONS.put(Role.ACCOUNT_OFFICER, Set.of(Permission.INVOICE_RECONCILE));

        // Auditor Permissions
        ROLE_PERMISSIONS.put(Role.AUDITOR, Set.of(Permission.REPORTDATA_EXPORT));

        // Vendor Permissions
        ROLE_PERMISSIONS.put(Role.VENDOR, Set.of(Permission.SUPPLIER_PROFILE_UPDATE, Permission.PURCHASEORDER_RECEIVE, Permission.PAYMENTAPPROVE_NOTIFICATION, Permission.SUPPLIERPERFORMANCEMETRICS_VIEW));

    }
    public static Set<Permission> getPermissionsForRoles(Role role) {
        return ROLE_PERMISSIONS.getOrDefault(role, Collections.emptySet());
    }
    public static boolean hasPermission(Role role, Permission permission) {
        return ROLE_PERMISSIONS.getOrDefault(role, Collections.emptySet())
                .contains(permission);
    }
    public static List<Role> getRolesWithPermissions(Permission permission) {
        return ROLE_PERMISSIONS.entrySet().stream()
                .filter(entry -> entry.getValue().contains(permission))
                .map(Map.Entry::getKey)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public static Map<Role, Set<Permission>> getRolePermissions() {
        return ROLE_PERMISSIONS;
    }
}