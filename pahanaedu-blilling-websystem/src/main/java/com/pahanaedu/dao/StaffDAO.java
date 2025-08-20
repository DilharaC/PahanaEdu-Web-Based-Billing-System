package com.pahanaedu.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import com.pahanaedu.model.Staff;

public class StaffDAO {

    public StaffDAO() {}

    // === Add new staff ===
    public void addStaff(Staff staff) throws SQLException {
        String sql = "INSERT INTO staff (username, password, full_name, email, phone, nic, job_title, role, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staff.getUsername());
            String hashedPassword = BCrypt.hashpw(staff.getPassword(), BCrypt.gensalt());
            ps.setString(2, hashedPassword);
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

    // === Secure login with BCrypt ===
    public Staff login(String username, String password) {
        Staff staff = null;
        String sql = "SELECT * FROM staff WHERE username = ? AND status = 'active'";

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");

                    if (BCrypt.checkpw(password, hashedPassword)) {
                        staff = extractStaff(rs);
                        staff.setPassword(null); // 🚫 Don’t expose hash outside DAO
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // TODO: replace with proper logging
        }
        return staff;
    }

    // === Get staff by ID ===
    public Staff getStaffById(int staffId) {
        String sql = "SELECT * FROM staff WHERE staff_id = ?";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Staff staff = extractStaff(rs);
                    staff.setPassword(null);
                    return staff;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // === Update password (raw) ===
    public boolean updatePassword(int staffId, String newPassword) {
        String sql = "UPDATE staff SET password = ? WHERE staff_id = ?";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            ps.setString(1, hashedPassword);
            ps.setInt(2, staffId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // === Find staff by email ===
    public Staff findByEmail(String email) {
        String sql = "SELECT * FROM staff WHERE email = ?";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Staff staff = extractStaff(rs);
                    staff.setPassword(null);
                    return staff;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // === Reset token methods ===
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

    public Staff findByResetToken(String token) {
        String sql = "SELECT * FROM staff WHERE reset_token = ? AND reset_token_expiry > CURRENT_TIMESTAMP";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Staff staff = extractStaff(rs);
                    staff.setPassword(null);
                    return staff;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateResetTokenWithExpiry(int staffId, String token, Timestamp expiry) {
        String sql = "UPDATE staff SET reset_token = ?, reset_token_expiry = ? WHERE staff_id = ?";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            if (expiry != null) {
                ps.setTimestamp(2, expiry);
            } else {
                ps.setNull(2, Types.TIMESTAMP);
            }
            ps.setInt(3, staffId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // === Get all staff ===
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Staff staff = extractStaff(rs);
                staff.setPassword(null);
                staffList.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

    // === Update staff ===
    public boolean updateStaff(Staff staff) {
        String sql;
        boolean hashPassword = false;

        if (staff.getPassword() != null && !staff.getPassword().isEmpty()) {
            hashPassword = true;
            sql = "UPDATE staff SET username=?, password=?, full_name=?, email=?, phone=?, nic=?, job_title=?, role=?, status=? WHERE staff_id=?";
        } else {
            sql = "UPDATE staff SET username=?, full_name=?, email=?, phone=?, nic=?, job_title=?, role=?, status=? WHERE staff_id=?";
        }

        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int idx = 1;
            ps.setString(idx++, staff.getUsername());

            if (hashPassword) {
                String hashedPassword = BCrypt.hashpw(staff.getPassword(), BCrypt.gensalt());
                ps.setString(idx++, hashedPassword);
            }

            ps.setString(idx++, staff.getFullName());
            ps.setString(idx++, staff.getEmail());
            ps.setString(idx++, staff.getPhone());
            ps.setString(idx++, staff.getNic());
            ps.setString(idx++, staff.getJobTitle());
            ps.setString(idx++, staff.getRole());
            ps.setString(idx++, staff.getStatus());
            ps.setInt(idx, staff.getStaffId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // === Delete staff ===
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

    // === Get staff by username ===
    public Staff getStaffByUsername(String username) {
        String sql = "SELECT * FROM staff WHERE username = ? AND status='active'";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Staff staff = extractStaff(rs);
                    staff.setPassword(null);
                    return staff;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // === Update password with already-hashed value ===
    public boolean updatePasswordWithHash(int staffId, String hashedPassword) {
        String sql = "UPDATE staff SET password = ? WHERE staff_id = ?";
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hashedPassword);
            ps.setInt(2, staffId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // === Helper method ===
    private Staff extractStaff(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setStaffId(rs.getInt("staff_id"));
        staff.setUsername(rs.getString("username"));
        staff.setFullName(rs.getString("full_name"));
        staff.setEmail(rs.getString("email"));
        staff.setPhone(rs.getString("phone"));
        staff.setNic(rs.getString("nic"));
        staff.setJobTitle(rs.getString("job_title"));
        staff.setRole(rs.getString("role"));
        staff.setStatus(rs.getString("status"));
        // 🚫 Do not set password hash into Staff
        return staff;
    }
}
