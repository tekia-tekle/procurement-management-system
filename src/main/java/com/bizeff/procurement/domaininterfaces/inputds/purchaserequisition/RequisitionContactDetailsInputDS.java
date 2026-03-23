package com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition;
public class RequisitionContactDetailsInputDS {
    private String name;
    private String email;
    private String departmentId;//department id
    private String role;
    private String phone;
    public RequisitionContactDetailsInputDS(String name,
                                            String email,
                                            String phone,
                                            String departmentId,
                                            String role)
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.departmentId = departmentId;
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public String getRole() {
        return role;
    }
    public String getPhone() {
        return phone;
    }
}