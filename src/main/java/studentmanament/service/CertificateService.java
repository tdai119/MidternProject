/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanament.service;

/**
 *
 * @author ACER
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import studentmanament.DbConnection;
import studentmanament.entity.Certificate;

public class CertificateService {

    public List<Certificate> getCertificatesForStudent(int studentId) {
        List<Certificate> certificates = new ArrayList<>();
        String sql = "SELECT * FROM certificates WHERE student_id = ?";

        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, studentId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int certificateId = rs.getInt("certificate_id");
                    String certificateName = rs.getString("certificate_name");
                    LocalDate issueDate = rs.getDate("issue_date").toLocalDate();
                    LocalDate expiryDate = null;
                    if (rs.getDate("expiry_date") != null) {
                        expiryDate = rs.getDate("expiry_date").toLocalDate();
                    }
                    String details = rs.getString("details");

                    certificates.add(new Certificate(certificateId, studentId, certificateName, issueDate, expiryDate, details));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return certificates;
    }

    public boolean addCertificate(Certificate certificate) {
        String sql = "INSERT INTO certificates (student_id, certificate_name, issue_date, expiry_date, details) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, certificate.getStudentId());
            pst.setString(2, certificate.getCertificateName());
            pst.setString(3, certificate.getIssueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pst.setString(4, certificate.getExpiryDate() != null ? certificate.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null);
            pst.setString(5, certificate.getDetails());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
            return false;
        }
    }

    public boolean updateCertificate(Certificate certificate) {
        String sql = "UPDATE certificates SET certificate_name = ?, issue_date = ?, expiry_date = ?, details = ? WHERE certificate_id = ?";

        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, certificate.getCertificateName());
            pst.setString(2, certificate.getIssueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            pst.setString(3, certificate.getExpiryDate() != null ? certificate.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null);
            pst.setString(4, certificate.getDetails());
            pst.setInt(5, certificate.getCertificateId());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
            return false;
        }
    }

    public boolean deleteCertificate(int certificateId) {
        String sql = "DELETE FROM certificates WHERE certificate_id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, certificateId);

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, handle exception more gracefully
            return false;
        }
    }

    public int countCertificatesForStudent(int studentId) {
        String sql = "SELECT COUNT(*) FROM certificates WHERE student_id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, studentId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return 0; // Return 0 if there's an exception or no certificates
    }

}
