package com.bizeff.procurement.domaininterfaces.inputds.purchaseorder;
public class PurchaseOrderContactDetailsInputDS {
    private String name;
    private String departmentId;
    private String emailAddress;
    private String phoneNumber;
    private String role;

    public PurchaseOrderContactDetailsInputDS(String name,
                                              String departmentId,
                                              String emailAddress,
                                              String phoneNumber,
                                              String role)
    {
        this.name = name;
        this.departmentId = departmentId;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
