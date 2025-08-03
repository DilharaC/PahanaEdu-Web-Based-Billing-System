package com.pahanaedu.service;

import java.sql.SQLException;
import com.pahanaedu.dao.StaffDAO;
import com.pahanaedu.model.Staff;

public class StaffService {
    private StaffDAO staffDAO = new StaffDAO();

    public void createStaff(Staff staff) throws SQLException {
        staffDAO.addStaff(staff);
    }
    public Staff login(String username, String password) {
        return staffDAO.validateLogin(username, password);
    }
    public boolean checkPassword(int staffId, String currentPassword) {
        Staff staff = staffDAO.getStaffById(staffId);
        if (staff != null) {
            return staff.getPassword().equals(currentPassword);  // Plaintext comparison, hash in real app!
        }
        return false;
    }

    // Update staff password
    public boolean updatePassword(int staffId, String newPassword) {
        return staffDAO.updatePassword(staffId, newPassword);
    }
}
