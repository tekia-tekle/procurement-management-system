package com.bizeff.procurement.persistences.entity.suppliermanagement;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Embeddable
public class EmbeddedContactDetails {
    @NotNull
    @Column(name = "Full_Name",nullable = false)
    private String fullName;//vendor name

    @Email
    @Column(name = "Email",nullable = false, unique = true)
    @NotNull
    private String email;

    @Pattern(regexp = "^\\+\\d{1,3}\\d{4,14}$")
    @NotNull
    @Column(name = "Phone_Number",nullable = false, unique = true)
    private  String phoneNumber;
    @NotNull
    @Column(name = "Address",nullable = false)
    private String address;// location of the vendor where you will be.
    public EmbeddedContactDetails() {

    }
    public EmbeddedContactDetails(String fullName,String email,String phoneNumber, String address){
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "EmbeddedContactDetails{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
