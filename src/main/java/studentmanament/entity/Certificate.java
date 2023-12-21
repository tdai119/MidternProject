/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanament.entity;

/**
 *
 * @author ACER
 */
import java.time.LocalDate;

public class Certificate {
    private int certificateId;
    private int studentId;
    private String certificateName;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String details;

    // Constructor
    public Certificate(int certificateId, int studentId, String certificateName, 
                       LocalDate issueDate, LocalDate expiryDate, String details) {
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.certificateName = certificateName;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.details = details;
    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    
}
