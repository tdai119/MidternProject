/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanament.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import studentmanament.DbConnection;
import studentmanament.entity.LoginRecord;
import studentmanament.entity.User;

/**
 *
 * @author ACER
 */
public class UserService {

    public boolean addUser(User user) {
        boolean isAddedSuccessfully = false;
        String sql = "INSERT INTO users (username, password_hash, profile_picture, name, age, phone_number, status, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, (SELECT role_id FROM roles WHERE role_name = ?))";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPasswordHash());
            pst.setBytes(3, user.getProfilePicture());
            pst.setString(4, user.getName());
            pst.setInt(5, user.getAge());
            pst.setString(6, user.getPhoneNumber());
            pst.setString(7, user.getStatus());
            pst.setString(8, user.getRoleName());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                isAddedSuccessfully = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return isAddedSuccessfully;
    }

    public boolean checkUsernameExist(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return false;
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
            return false;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, age = ?, phone_number = ?, status = ?, role_id = (SELECT role_id FROM roles WHERE role_name = ?) WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, user.getName());
            pst.setInt(2, user.getAge());
            pst.setString(3, user.getPhoneNumber());
            pst.setString(4, user.getStatus());
            pst.setString(5, user.getRoleName());
            pst.setInt(6, user.getUserId());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
            return false;
        }
    }

    public byte[] getUserProfilePicture(int userId) {
        String sql = "SELECT profile_picture FROM users WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("profile_picture");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return null;
    }

    public boolean updateProfilePicture(int userId, byte[] imageBytes) {
        String sql = "UPDATE users SET profile_picture = ? WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setBytes(1, imageBytes);
            pst.setInt(2, userId);

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
            return false;
        }
    }

    public List<LoginRecord> getLoginHistoryForUser(int userId) {
        List<LoginRecord> history = new ArrayList<>();
        String sql = "SELECT login_timestamp, logout_timestamp FROM login_history WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Timestamp loginTimestamp = rs.getTimestamp("login_timestamp");
                    Timestamp logoutTimestamp = rs.getTimestamp("logout_timestamp");
                    LoginRecord record = new LoginRecord(loginTimestamp, logoutTimestamp);
                    history.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return history;
    }
    
    public String getUserRole(int userId) {
        String sql = "SELECT role_name FROM roles WHERE role_id = (SELECT role_id FROM users WHERE user_id = ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return null; // Return null if the user's role is not found or in case of an error
    }
    
    public String getName(int userId) {
        String sql = "SELECT name FROM users WHERE user_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return null; 
    }

}
