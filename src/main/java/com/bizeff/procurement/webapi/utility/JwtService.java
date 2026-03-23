package com.bizeff.procurement.webapi.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(
            "bizeff-secret-key-for-jwt-authentication-2025".getBytes()
    );
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours

    private final RolePermissionServices rolePermissionService;

    public JwtService(RolePermissionServices rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    // Generate token with username + list of roles + permissions
    public String generateToken(String username, List<String> roles) {
        Set<String> allAuthorities = new HashSet<>();

        // Add roles and their permissions
        for (String roleName : roles) {
            try {
                Role role = Role.valueOf(roleName);
                allAuthorities.add(role.name());

                // Add permissions for this role
                Set<Permission> permissions = rolePermissionService.getPermissionsForRoles(role);
                for (Permission permission : permissions) {
                    allAuthorities.add(permission.name());
                }
            } catch (IllegalArgumentException e) {
                // Skip invalid roles
            }
        }

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .claim("authorities", new ArrayList<>(allAuthorities))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate token signature and expiration
    public boolean isValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract list of roles
    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    // Extract all authorities (roles + permissions)
    public List<String> extractAuthorities(String token) {
        return extractAllClaims(token).get("authorities", List.class);
    }

    // Internal method to parse claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}