package com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement;
public class SupplierContactDetailsInputDS {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    public SupplierContactDetailsInputDS(String fullName,
                                         String email,
                                         String phone,
                                         String address){
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
}
