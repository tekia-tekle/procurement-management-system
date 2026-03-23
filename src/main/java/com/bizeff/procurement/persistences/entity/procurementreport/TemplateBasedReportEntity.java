package com.bizeff.procurement.persistences.entity.procurementreport;

import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "template_based_reports")
public class TemplateBasedReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_template_id", nullable = false)
    private ReportTemplateEntity reportTemplate;

    @ElementCollection
    @CollectionTable(name = "report_procurement_activities",
            joinColumns = @JoinColumn(name = "report_id"))
    @Enumerated(EnumType.STRING)
    private List<ProcurementActivity> procurementActivities;

    @OneToMany(mappedBy = "templateBasedReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportDataEntity> reportDataList = new ArrayList<>();

    // Constructors
    public TemplateBasedReportEntity() {}


    // Helper method to convert Object values to String for persistence
    public void addReportData(ReportDataEntity data) {
        data.setTemplateBasedReport(this);
        this.reportDataList.add(data);
    }

    public void populateDataFromMap(Map<String, Object> dataMap) {
        if (dataMap != null) {
            dataMap.forEach((key, value) -> {
                ReportDataEntity data = new ReportDataEntity();
                data.setDataKey(key);
                data.setDataValue(value != null ? value.toString() : null);
                addReportData(data);
            });
        }
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportTemplateEntity getReportTemplate() {
        return reportTemplate;
    }

    public void setReportTemplate(ReportTemplateEntity reportTemplate) {
        this.reportTemplate = reportTemplate;
    }

    public List<ProcurementActivity> getProcurementActivities() {
        return procurementActivities;
    }

    public void setProcurementActivities(List<ProcurementActivity> procurementActivities) {
        this.procurementActivities = procurementActivities;
    }
    public List<ReportDataEntity> getReportDataList() { return reportDataList; }
    public void setReportDataList(List<ReportDataEntity> reportDataList) { this.reportDataList = reportDataList; }
}