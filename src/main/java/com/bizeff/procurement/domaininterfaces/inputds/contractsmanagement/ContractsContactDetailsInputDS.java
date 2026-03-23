package com.bizeff.procurement.domaininterfaces.inputds.contractsmanagement;

public class ContractsContactDetailsInputDS {
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String role;
    public ContractsContactDetailsInputDS(String name, String emailAddress, String phoneNumber, String role) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
    public String getName() {
        return name;
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
}
