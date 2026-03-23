package com.bizeff.procurement.persistences.entity.purchaserequisition;

import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveryReceiptEntity;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoiceEntity;
import com.bizeff.procurement.persistences.entity.procurementreport.ProcurementReportEntity;
import com.bizeff.procurement.persistences.entity.procurementreport.ReportTemplateEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "User_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Email
    @Column(name = "Email_Address",nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^\\+\\d{1,3}\\d{4,14}$")
    @Column(name = "phone_number", nullable = false,unique = true)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",nullable = false)
    private DepartmentEntity department;

    @OneToMany(mappedBy = "requestedBy",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PurchaseRequisitionEntity> purchaseRequisition;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<InvoiceEntity>invoice;
    @OneToMany(mappedBy = "receivedBy",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DeliveryReceiptEntity> deliveryReceipt;

    @OneToMany(mappedBy = "createdBy",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ReportTemplateEntity> reportTemplate;
    @OneToMany(mappedBy = "createdBy",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProcurementReportEntity> procurementReport;
    @Column(nullable = false)
    private String role;

    // Constructors
    public UserEntity() {
    }
    @PrePersist
    public void generateUserId() {
        if (this.userId == null) {
            this.userId = IdGenerator.generateId("UR");
        }
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
    public DepartmentEntity getDepartment() {
        return department;
    }
    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public List<PurchaseRequisitionEntity> getPurchaseRequisition() {
        return purchaseRequisition;
    }

    public void setPurchaseRequisition(List<PurchaseRequisitionEntity> purchaseRequisition) {
        this.purchaseRequisition = purchaseRequisition;
    }

    public List<InvoiceEntity> getInvoice() {
        return invoice;
    }

    public void setInvoice(List<InvoiceEntity> invoice) {
        this.invoice = invoice;
    }

    public List<DeliveryReceiptEntity> getDeliveryReceipt() {
        return deliveryReceipt;
    }

    public void setDeliveryReceipt(List<DeliveryReceiptEntity> deliveryReceipt) {
        this.deliveryReceipt = deliveryReceipt;
    }
    public List<ReportTemplateEntity> getReportTemplate() {
        return reportTemplate;
    }
    public void setReportTemplate(List<ReportTemplateEntity> reportTemplate) {
        this.reportTemplate = reportTemplate;
    }
    public List<ProcurementReportEntity> getProcurementReport() {
        return procurementReport;
    }
    public void setProcurementReport(List<ProcurementReportEntity> procurementReport) {
        this.procurementReport = procurementReport;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", department=" + department +
                ", purchaseRequisition=" + purchaseRequisition +
                ", invoice=" + invoice +
                ", deliveryReceipt=" + deliveryReceipt +
                ", reportTemplate=" + reportTemplate +
                ", procurementReport=" + procurementReport +
                ", role='" + role + '\'' +
                '}';
    }
}