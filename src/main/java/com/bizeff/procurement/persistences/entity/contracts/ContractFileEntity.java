package com.bizeff.procurement.persistences.entity.contracts;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Contract_File")
public class ContractFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "File_Name",nullable = false)
    private String fileName;
    @Column(name = "file_Type",nullable = false)
    private String fileType; // PDF, DOCX, etc.
    @Column(name = "file_Url",nullable = false,unique = true)
    private String fileUrl; // Cloud storage or local path
    @Column(name = "UPloaded_Date",nullable = false)
    private LocalDate uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id",nullable = false)
    private ContractEntity contract;
    public ContractFileEntity(){}

    // Getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() { return fileType; }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileUrl() { return fileUrl; }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public LocalDate getUploadDate() { return uploadDate; }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }

    @Override
    public String toString() {
        return "ContractFileEntity{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", uploadDate=" + uploadDate +
                '}';
    }
}
