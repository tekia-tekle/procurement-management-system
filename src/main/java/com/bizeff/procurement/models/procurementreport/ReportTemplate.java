package com.bizeff.procurement.models.procurementreport;

import com.bizeff.procurement.models.purchaserequisition.User;

import java.time.LocalDate;
import java.util.List;
public class ReportTemplate {
    private Long id;
    private String templateId;
    private String templateName;
    private String templateDescription;
    private List<String> selectedFields; // fields to be included in the template.
    private LocalDate createdAt;
    private User createdBy;
    public ReportTemplate() {

    }

    public ReportTemplate(String templateId,
                          String templateName,
                          String templateDescription,
                          List<String> selectedFields,
                          LocalDate createdAt,
                          User createdBy) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.selectedFields = selectedFields;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    //Getter and setter
    public Long getId() {
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
    public User getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    @Override
    public String toString() {
        return "ReportTemplate{" +
                "templateId='" + templateId + '\'' +
                ", templateName='" + templateName + '\'' +
                ", templateDescription='" + templateDescription + '\'' +
                ", selectedFields=" + selectedFields +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                '}';
    }
}
