package com.bizeff.procurement.models.contracts;

import java.time.LocalDate;

public class ContractFile {
    private Long id; // Unique identifier for the file
    private String fileName;
    private String fileType; // PDF, DOCX, etc.
    private String fileUrl; // Cloud storage or local path
    private LocalDate uploadDate;

    public ContractFile(){}
    public ContractFile(String fileName,
                        String fileType,
                        String fileUrl,
                        LocalDate uploadDate)
    {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.uploadDate = uploadDate;
    }

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
}
