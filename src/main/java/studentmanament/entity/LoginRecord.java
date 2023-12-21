/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanament.entity;

import java.sql.Timestamp;

/**
 *
 * @author ACER
 */
public class LoginRecord {
    private Timestamp loginTimestamp;
    private Timestamp logoutTimestamp;

    public LoginRecord(Timestamp loginTimestamp, Timestamp logoutTimestamp) {
        this.loginTimestamp = loginTimestamp;
        this.logoutTimestamp = logoutTimestamp;
    }

    // Getters
    public Timestamp getLoginTimestamp() {
        return loginTimestamp;
    }

    public Timestamp getLogoutTimestamp() {
        return logoutTimestamp;
    }

    
}
