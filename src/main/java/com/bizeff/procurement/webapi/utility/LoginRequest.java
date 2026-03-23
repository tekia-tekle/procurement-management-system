package com.bizeff.procurement.webapi.utility;

import java.util.List;

public class LoginRequest {
    private String username;
    private List<String> roles;

    // getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}
