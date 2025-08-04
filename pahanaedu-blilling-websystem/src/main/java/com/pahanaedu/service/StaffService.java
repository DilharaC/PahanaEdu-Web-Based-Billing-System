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

    private final String SENDGRID_API_KEY = "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"; // Replace with real key
    private final String FROM_EMAIL = "bookshoppahanaedu@gmail.com"; // Must be verified sender

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

    public boolean sendPasswordResetLink(String email, String resetBaseUrl) {
        try {
            // 1. Find staff by email
            Staff staff = staffDAO.findByEmail(email);
            if (staff == null) {
                return false;
            }

            // 2. Generate token and save in DB
            String token = UUID.randomUUID().toString();
            staffDAO.updateResetToken(staff.getStaffId(), token);

            // 3. Create reset link
            String resetLink = resetBaseUrl + "?token=" + token;

            // 4. Prepare email
            String subject = "Password Reset Request";
            String body = "Hello " + staff.getFullName() + ",\n\n"
                        + "Click the link below to reset your password:\n"
                        + resetLink + "\n\n"
                        + "If you did not request this, please ignore this email.\n\n"
                        + "Regards,\nPahanaEdu";

            // 5. Send email
            return sendEmail(email, subject, body);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean sendEmail(String toEmail, String subject, String body) {
        try {
            URL url = new URL("https://api.sendgrid.com/v3/mail/send");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + SENDGRID_API_KEY);
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // Escape body to avoid JSON errors
            String safeBody = body.replace("\"", "\\\"");

            String json = "{"
                    + "\"personalizations\":[{\"to\":[{\"email\":\"" + toEmail + "\"}]}],"
                    + "\"from\":{\"email\":\"" + FROM_EMAIL + "\"},"
                    + "\"subject\":\"" + subject + "\","
                    + "\"content\":[{\"type\":\"text/plain\",\"value\":\"" + safeBody + "\"}]"
                    + "}";

            try (OutputStream os = con.getOutputStream()) {
                os.write(json.getBytes("UTF-8"));
            }

            int responseCode = con.getResponseCode();
            return responseCode == 202; // 202 = Accepted

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}