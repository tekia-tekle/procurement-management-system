package com.bizeff.procurement.webapi.utility;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleUrlMapping {

    public static Map<String, Set<Role>> getUrlToRoleMapping() {
        Map<String, Set<Role>> urlToRoleMap = new HashMap<>();

        // Get both existing maps
        Map<Role, Set<Permission>> roleToPermissions = RolePermissionServices.getRolePermissions();
        Map<Permission, String> permissionToUrl = PermissionUrlMapping.getPermissionToUrlMapping();

        // Combine both: URL → Roles
        for (Map.Entry<Role, Set<Permission>> roleEntry : roleToPermissions.entrySet()) {
            Role role = roleEntry.getKey();
            for (Permission permission : roleEntry.getValue()) {
                String url = permissionToUrl.get(permission);
                if (url != null) {
                    urlToRoleMap.computeIfAbsent(url, k -> new HashSet<>()).add(role);
                }
            }
        }

        return urlToRoleMap;
    }
}
