/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanament.entity;

/**
 *
 * @author ACER
 */
public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private byte[] profilePicture;
    private String name;
    private Integer age;
    private String phoneNumber;
    private String status;
    private String roleName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public User(int userId, String username, String passwordHash, byte[] profilePicture, String name, Integer age, String phoneNumber, String status, String roleName) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.profilePicture = profilePicture;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.roleName = roleName;
    }

    
}

