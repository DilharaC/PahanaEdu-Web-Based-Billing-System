package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pahanaedu.model.Staff;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.pahanaedu.dao.DBConnectionFactory;

public class StaffDAO {

    public void addStaff(Staff staff) throws SQLException {
        String sql = "INSERT INTO staff (username, password, full_name, email, phone, nic, job_title, role, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staff.getUsername());
            ps.setString(2, staff.getPassword()); // ⚠ Hash in real project
            ps.setString(3, staff.getFullName());
            ps.setString(4, staff.getEmail());
            ps.setString(5, staff.getPhone());
            ps.setString(6, staff.getNic());
            ps.setString(7, staff.getJobTitle());
            ps.setString(8, staff.getRole());
            ps.setString(9, staff.getStatus());

            ps.executeUpdate();
        }
    }

    public Staff validateLogin(String username, String password) {
        Staff staff = null;
        String sql = "SELECT * FROM staff WHERE username=? AND password=? AND status='active'";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password); // ⚠ In production, use password hashing

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staff = new Staff();
                    staff.setStaffId(rs.getInt("staff_id"));
                    staff.setUsername(rs.getString("username"));
                    staff.setFullName(rs.getString("full_name"));
                    staff.setEmail(rs.getString("email"));
                    staff.setPhone(rs.getString("phone"));  // Added
                    staff.setNic(rs.getString("nic"));      // Added
                    staff.setRole(rs.getString("role"));
                    staff.setStatus(rs.getString("status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    // Login: get staff by username and password
    public Staff login(String username, String password) {
        Staff staff = null;
        String sql = "SELECT * FROM staff WHERE username = ? AND password = ? AND status = 'active'";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);  // In production, hash the password and compare hashes!
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staff = new Staff();
                    staff.setStaffId(rs.getInt("staff_id"));
                    staff.setUsername(rs.getString("username"));
                    staff.setPassword(rs.getString("password"));
                    staff.setFullName(rs.getString("full_name"));
                    staff.setEmail(rs.getString("email"));
                    staff.setPhone(rs.getString("phone"));  // Added
                    staff.setNic(rs.getString("nic"));      // Added
                    staff.setRole(rs.getString("role"));
                    staff.setStatus(rs.getString("status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return staff;
    }

    // Get staff by ID (for checking password or profile)
    public Staff getStaffById(int staffId) {
        Staff staff = null;
        String sql = "SELECT * FROM staff WHERE staff_id = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staff = new Staff();
                    staff.setStaffId(rs.getInt("staff_id"));
                    staff.setUsername(rs.getString("username"));
                    staff.setPassword(rs.getString("password"));
                    staff.setFullName(rs.getString("full_name"));
                    staff.setEmail(rs.getString("email"));
                    staff.setPhone(rs.getString("phone"));  // Added
                    staff.setNic(rs.getString("nic"));      // Added
                    staff.setRole(rs.getString("role"));
                    staff.setStatus(rs.getString("status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return staff;
    }

    // Update password for staff
    public boolean updatePassword(int staffId, String newPassword) {
        String sql = "UPDATE staff SET password = ? WHERE staff_id = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);  // Hash in production!
            ps.setInt(2, staffId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
   

    public Staff findByEmail(String email) {
        Staff staff = null;
        String sql = "SELECT * FROM staff WHERE email = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staff = extractStaff(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    // Update reset token in DB
    public boolean updateResetToken(int staffId, String token) {
        String sql = "UPDATE staff SET reset_token = ? WHERE staff_id = ?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.setInt(2, staffId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Helper to extract Staff object from ResultSet
//    private Staff extractStaff(ResultSet rs) throws SQLException {
//        Staff staff = new Staff();
//        staff.setStaffId(rs.getInt("staff_id"));
//        staff.setUsername(rs.getString("username"));
//        staff.setPassword(rs.getString("password"));
//        staff.setFullName(rs.getString("full_name"));
//        staff.setEmail(rs.getString("email"));
//        staff.setPhone(rs.getString("phone"));
//        staff.setNic(rs.getString("nic"));
//        staff.setJobTitle(rs.getString("job_title"));
//        staff.setRole(rs.getString("role"));
//        staff.setStatus(rs.getString("status"));
//        return staff;
//    }
// forget password 
    public Staff findByResetToken(String token) {
        Staff staff = null;
        String sql = "SELECT * FROM staff WHERE reset_token = ? AND reset_token_expiry > CURRENT_TIMESTAMP";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staff = extractStaff(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }
    public boolean updateResetTokenWithExpiry(int staffId, String token, Timestamp expiry) {
        String sql = "UPDATE staff SET reset_token = ?, reset_token_expiry = ? WHERE staff_id = ?";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            if (expiry != null) {
                ps.setTimestamp(2, expiry);
            } else {
                ps.setNull(2, java.sql.Types.TIMESTAMP);
            }
            ps.setInt(3, staffId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                staffList.add(extractStaff(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public boolean updateStaff(Staff staff) {
        String sql = "UPDATE staff SET username=?, password=?, full_name=?, email=?, phone=?, nic=?, job_title=?, role=?, status=? WHERE staff_id=?";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staff.getUsername());
            ps.setString(2, staff.getPassword()); // ⚠ Hash in production
            ps.setString(3, staff.getFullName());
            ps.setString(4, staff.getEmail());
            ps.setString(5, staff.getPhone());
            ps.setString(6, staff.getNic());
            ps.setString(7, staff.getJobTitle());
            ps.setString(8, staff.getRole());
            ps.setString(9, staff.getStatus());
            ps.setInt(10, staff.getStaffId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStaff(int staffId) {
        String sql = "DELETE FROM staff WHERE staff_id = ?";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, staffId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // === Helper method (already exists in your code, reusing it) ===
    private Staff extractStaff(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setStaffId(rs.getInt("staff_id"));
        staff.setUsername(rs.getString("username"));
        staff.setPassword(rs.getString("password"));
        staff.setFullName(rs.getString("full_name"));
        staff.setEmail(rs.getString("email"));
        staff.setPhone(rs.getString("phone"));
        staff.setNic(rs.getString("nic"));
        staff.setJobTitle(rs.getString("job_title"));
        staff.setRole(rs.getString("role"));
        staff.setStatus(rs.getString("status"));
        return staff;
    }
}
