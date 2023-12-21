/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanament.service;

import studentmanament.entity.Student;

/**
 *
 * @author ACER
 */
import studentmanament.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudentService {

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, date_of_birth, gender, phone_number, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, student.getName());
            pst.setString(2, formatDate(student.getDateOfBirth())); // Format LocalDate to String
            pst.setString(3, student.getGender());
            pst.setString(4, student.getPhoneNumber());
            pst.setString(5, student.getEmail());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
            return false;
        }
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, date_of_birth = ?, gender = ?, phone_number = ?, email = ? WHERE student_id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, student.getName());
            pst.setString(2, formatDate(student.getDateOfBirth())); // Format LocalDate to String
            pst.setString(3, student.getGender());
            pst.setString(4, student.getPhoneNumber());
            pst.setString(5, student.getEmail());
            pst.setInt(6, student.getStudentId());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
            return false;
        }
    }

    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, studentId);
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
            return false;
        }
    }

    public String getStudentEmail(int studentId) {
        String sql = "SELECT email FROM students WHERE student_id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, studentId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return null; // Return null or throw an exception if the email is not found
    }


}
