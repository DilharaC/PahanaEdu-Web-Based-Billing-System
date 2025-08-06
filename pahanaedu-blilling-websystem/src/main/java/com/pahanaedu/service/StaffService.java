package com.pahanaedu.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.UUID;

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
        return staff != null && staff.getPassword().equals(currentPassword);
    }

    public boolean updatePassword(int staffId, String newPassword) {
        return staffDAO.updatePassword(staffId, newPassword);
    }

   
    }

   
    
