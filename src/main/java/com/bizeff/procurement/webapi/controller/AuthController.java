package com.bizeff.procurement.webapi.controller;

import com.bizeff.procurement.webapi.utility.JwtService;
import com.bizeff.procurement.webapi.utility.LoginRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        String token = jwtService.generateToken(request.getUsername(), request.getRoles());

        return Map.of(
                "token", token,
                "username", request.getUsername(),
                "roles", String.join(",", request.getRoles())
        );
    }
    @PostMapping("/validate")
    public Map<String, Object> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid authorization header");
        }

        String token = authHeader.substring(7);

        if (!jwtService.isValid(token)) {
            throw new RuntimeException("Invalid token");
        }

        return Map.of(
                "username", jwtService.extractUsername(token),
                "roles", jwtService.extractRoles(token),
                "authorities", jwtService.extractAuthorities(token),
                "valid", true
        );
    }

}
