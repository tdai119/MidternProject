/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanament.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import studentmanament.DbConnection;
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

}
