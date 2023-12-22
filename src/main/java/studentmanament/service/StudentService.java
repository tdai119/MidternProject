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
import java.util.ArrayList;
import java.util.List;

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

    public List<Student> findStudents(String name, LocalDate dob, String phone, String gender, Integer id) {
        List<Student> students = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM students");

        // List to hold parameters for the query
        List<Object> parameters = new ArrayList<>();

        // Check if any condition is provided
        boolean isConditionProvided = name != null && !name.isEmpty() || dob != null || phone != null && !phone.isEmpty() || gender != null && !gender.isEmpty() || id != null;

        if (isConditionProvided) {
            sql.append(" WHERE ");
            boolean isFirstCondition = true;

            if (name != null && !name.isEmpty()) {
                sql.append("name LIKE ?");
                parameters.add("%" + name + "%");
                isFirstCondition = false;
            }
            if (dob != null) {
                if (!isFirstCondition) {
                    sql.append(" AND ");
                }
                sql.append("date_of_birth = ?");
                parameters.add(dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                isFirstCondition = false;
            }
            if (phone != null && !phone.isEmpty()) {
                if (!isFirstCondition) {
                    sql.append(" AND ");
                }
                sql.append("phone_number = ?");
                parameters.add(phone);
                isFirstCondition = false;
            }
            if (gender != null && !gender.isEmpty()) {
                if (!isFirstCondition) {
                    sql.append(" AND ");
                }
                sql.append("gender = ?");
                parameters.add(gender);
                isFirstCondition = false;
            }
            if (id != null) {
                if (!isFirstCondition) {
                    sql.append(" AND ");
                }
                sql.append("student_id = ?");
                parameters.add(id);
            }
        }

        // Execute the query
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql.toString())) {

            int index = 1;
            for (Object param : parameters) {
                pst.setObject(index++, param);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int dbId = rs.getInt("student_id");
                    String dbName = rs.getString("name");
                    LocalDate dbDob = rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null;
                    String dbPhone = rs.getString("phone_number");
                    String dbGender = rs.getString("gender");
                    String dbEmail = rs.getString("email");

                    Student student = new Student(dbId, dbName, dbDob, dbPhone, dbGender, dbEmail);
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        return students;
    }

    public List<Student> findAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int dbId = rs.getInt("student_id");
                String dbName = rs.getString("name");
                LocalDate dbDob = rs.getDate("date_of_birth") != null ? rs.getDate("date_of_birth").toLocalDate() : null;
                String dbPhone = rs.getString("phone_number");
                String dbGender = rs.getString("gender");
                String dbEmail = rs.getString("email");

                Student student = new Student(dbId, dbName, dbDob, dbPhone, dbGender, dbEmail);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        return students;
    }

}
