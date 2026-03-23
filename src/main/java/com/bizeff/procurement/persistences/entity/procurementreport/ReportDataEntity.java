package com.bizeff.procurement.persistences.entity.procurementreport;

import jakarta.persistence.*;

@Entity
@Table(name = "report_data")
public class ReportDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private TemplateBasedReportEntity templateBasedReport;

    @Column(name = "data_key", nullable = false)
    private String dataKey;

    @Column(name = "data_value", columnDefinition = "TEXT")
    private String dataValue;

    // Constructors
    public ReportDataEntity() {}
    public ReportDataEntity(TemplateBasedReportEntity templateBasedReport, String dataKey, String dataValue) {
        this.templateBasedReport = templateBasedReport;
        this.dataKey = dataKey;
        this.dataValue = dataValue;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public TemplateBasedReportEntity getTemplateBasedReport() {
        return templateBasedReport;
    }
    public void setTemplateBasedReport(TemplateBasedReportEntity templateBasedReport) {
        this.templateBasedReport = templateBasedReport;
    }
    public String getDataKey() {
        return dataKey;
    }
    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }
    public String getDataValue() {
        return dataValue;
    }
    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
    // toString
    @Override
    public String toString() {
        return "ReportDataEntity{" +
                "id=" + id +
                ", dataKey='" + dataKey + '\'' +
                ", dataValue='" + dataValue + '\'' +
                '}';
    }
}
