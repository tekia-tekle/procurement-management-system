package com.bizeff.procurement.persistences.entity.procurementreport;

import com.bizeff.procurement.persistences.entity.purchaserequisition.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "report_template")
public class ReportTemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "template_id", unique = true, nullable = false)
    private String templateId;
    @Column(name = "template_name", nullable = false)
    private String templateName;
    @Column(name = "template_description")
    private String templateDescription;
    @ElementCollection
    @CollectionTable(
            name = "report_template_fields",
            joinColumns = @JoinColumn(name = "report_template_id")
    )
    @Column(name = "field_name")
    private List<String> selectedFields;
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity createdBy;

    @OneToMany(mappedBy = "reportTemplate",fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    private List<TemplateBasedReportEntity> templateBasedReports;
    public ReportTemplateEntity(){}
    //getter and setter.
    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public List<String> getSelectedFields() {
        return selectedFields;
    }

    public void setSelectedFields(List<String> selectedFields) {
        this.selectedFields = selectedFields;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }
}
