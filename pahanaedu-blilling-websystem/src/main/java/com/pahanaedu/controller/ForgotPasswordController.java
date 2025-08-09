package com.pahanaedu.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import com.pahanaedu.service.StaffService;

@WebServlet("/ForgotPassword")
public class ForgotPasswordController extends HttpServlet {

    private StaffService staffService = new StaffService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        if (token != null) {
            System.out.println("[DEBUG] Received token in GET: " + token);
            request.setAttribute("token", token);
            request.getRequestDispatcher("WEB-INF/view/ResetPassword.jsp").forward(request, response);
        } else {
            System.out.println("[DEBUG] Showing ForgotPassword form.");
            request.getRequestDispatcher("WEB-INF/view/ForgotPassword.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token == null) {
            // Process forgot password request (send email with reset token link)
            String email = request.getParameter("email");
            System.out.println("[DEBUG] Forgot password requested for email: " + email);

            String resetToken = staffService.generatePasswordResetToken(email);
            System.out.println("[DEBUG] Generated reset token: " + resetToken);

            if (resetToken != null) {
                try {
                	sendResetPasswordEmail(request, email, resetToken);
                    System.out.println("[DEBUG] Reset password email sent successfully.");
                    request.setAttribute("message", "Reset link sent to your email.");
                } catch (Exception e) {
                    System.err.println("[ERROR] Failed to send reset email:");
                    e.printStackTrace();
                    request.setAttribute("error", "Failed to send reset email. Please try again later.");
                }
            } else {
                System.out.println("[DEBUG] Email not found in system: " + email);
                request.setAttribute("error", "Email not found.");
            }
            request.getRequestDispatcher("WEB-INF/view/ForgotPassword.jsp").forward(request, response);
        } else {
            // Process reset password submission
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            System.out.println("[DEBUG] Reset password with token: " + token);
            System.out.println("[DEBUG] New password: " + newPassword);
            System.out.println("[DEBUG] Confirm password: " + confirmPassword);

            if (!newPassword.equals(confirmPassword)) {
                System.out.println("[DEBUG] Passwords do not match.");
                request.setAttribute("error", "Passwords do not match.");
                request.setAttribute("token", token);
                request.getRequestDispatcher("WEB-INF/view/ResetPassword.jsp").forward(request, response);
                return;
            }

            boolean success = staffService.resetPasswordByToken(token, newPassword);
            System.out.println("[DEBUG] Password reset success: " + success);

            if (success) {
                request.setAttribute("message", "Password reset successfully. You may now login.");
                request.getRequestDispatcher("StaffLogin.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Invalid or expired token.");
                request.setAttribute("token", token);
                request.getRequestDispatcher("WEB-INF/view/ResetPassword.jsp").forward(request, response);
            }
        }
    }

    private void sendResetPasswordEmail(HttpServletRequest request, String toEmail, String resetToken) {
        final String fromEmail = "bookshoppahanaedu@gmail.com";  // your Gmail address
        final String appPassword = "tzhp wbpl zxof qzwy";        // your Gmail app password

        // Build app URL dynamically (scheme + server + port + context path)
        String appUrl = request.getScheme() + "://" + request.getServerName() 
                        + (request.getServerPort() == 80 || request.getServerPort() == 443 ? "" : ":" + request.getServerPort())
                        + request.getContextPath();

        String resetLink = appUrl + "/ForgotPassword?token=" + resetToken;

        String subject = "Password Reset Request";
        String body = "Hello,\n\nPlease click the link below to reset your password:\n"
                      + resetLink + "\n\nThis link will expire in 1 hour.\n\nRegards,\nSupport Team";

        System.out.println("[DEBUG] Preparing to send email to: " + toEmail);
        System.out.println("[DEBUG] Reset link: " + resetLink);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("[DEBUG] Reset password email sent to: " + toEmail);
        } catch (MessagingException e) {
            System.err.println("[ERROR] Exception while sending email:");
            e.printStackTrace();
        }
    }
}