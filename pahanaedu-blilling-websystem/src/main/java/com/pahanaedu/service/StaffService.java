package com.pahanaedu.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.pahanaedu.dao.StaffDAO;
import com.pahanaedu.model.Staff;

public class StaffService {
    private StaffDAO staffDAO = new StaffDAO();

   

    public void createStaff(Staff staff) throws SQLException {
        staffDAO.addStaff(staff);
    }

    public Staff login(String username, String password) {
        Staff staff = staffDAO.getStaffByUsername(username);
        if (staff != null && BCrypt.checkpw(password, staff.getPassword())) {
            return staff;
        }
        return null;
    }

    public boolean checkPassword(int staffId, String currentPassword) {
        Staff staff = staffDAO.getStaffById(staffId);
        return staff != null && BCrypt.checkpw(currentPassword, staff.getPassword());
    }

    public boolean updatePassword(int staffId, String newPassword) {
        String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        return staffDAO.updatePasswordWithHash(staffId, hashed);
    }
    
    public List<Staff> getAllStaff() {
        return staffDAO.getAllStaff();
    }

    public Staff getStaffById(int staffId) {
        return staffDAO.getStaffById(staffId);
    }

    public boolean updateStaff(Staff staff) {
        return staffDAO.updateStaff(staff);
    }

    public boolean deleteStaff(int staffId) {
        return staffDAO.deleteStaff(staffId);
    }

   //forget password
    public String generatePasswordResetToken(String email) {
        Staff staff = staffDAO.findByEmail(email);
        if (staff == null) return null;

        String token = UUID.randomUUID().toString();

        // Set expiry time (e.g., 1 hour from now)
        Timestamp expiry = Timestamp.from(Instant.now().plus(1, ChronoUnit.HOURS));

        boolean updated = staffDAO.updateResetTokenWithExpiry(staff.getStaffId(), token, expiry);
        if (updated) {
            return token;
        }
        return null;
    }

    public boolean resetPasswordByToken(String token, String newPassword) {
        Staff staff = staffDAO.findByResetToken(token);
        if (staff == null) return false;

        boolean updatedPassword = staffDAO.updatePassword(staff.getStaffId(), newPassword);
        if (updatedPassword) {
            // Clear token after successful reset
            staffDAO.updateResetTokenWithExpiry(staff.getStaffId(), null, null);
            return true;
        }
        return false;
    }
}
    

   
    
