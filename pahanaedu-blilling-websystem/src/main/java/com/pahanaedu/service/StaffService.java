package com.pahanaedu.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import com.pahanaedu.dao.StaffDAO;
import com.pahanaedu.model.Staff;

public class StaffService {

  
    private static StaffService instance;

  
    private StaffDAO staffDAO = new StaffDAO();

   
    private StaffService() {}

    
    public static StaffService getInstance() {
        if (instance == null) {
            synchronized (StaffService.class) {
                if (instance == null) {
                    instance = new StaffService();
                }
            }
        }
        return instance;
    }

   

    public void createStaff(Staff staff) throws SQLException {
        staffDAO.addStaff(staff);
    }

    public Staff login(String username, String password) {
        return staffDAO.login(username, password);
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

    // --- Forget password ---

    public String generatePasswordResetToken(String email) {
        Staff staff = staffDAO.findByEmail(email);
        if (staff == null) return null;

        String token = UUID.randomUUID().toString();
        Timestamp expiry = Timestamp.from(Instant.now().plus(1, ChronoUnit.HOURS));

        boolean updated = staffDAO.updateResetTokenWithExpiry(staff.getStaffId(), token, expiry);
        return updated ? token : null;
    }

    public boolean resetPasswordByToken(String token, String newPassword) {
        Staff staff = staffDAO.findByResetToken(token);
        if (staff == null) return false;

        boolean updatedPassword = staffDAO.updatePassword(staff.getStaffId(), newPassword);
        if (updatedPassword) {
            // Clear token
            staffDAO.updateResetTokenWithExpiry(staff.getStaffId(), null, null);
            return true;
        }
        return false;
    }
}
