package com.bizeff.procurement.domaininterfaces.inputds.procurementReport;

public class ReporterContactDetail {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;

    //default constructor
    public ReporterContactDetail() {

    }

    //parameterized constructor
    public ReporterContactDetail(String fullName, String email, String phoneNumber, String role) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    //getters and setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //toString method
    @Override
    public String toString() {
        return "ReporterContactDetail [fullName=" + fullName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", role=" + role + "]";
    }
}
