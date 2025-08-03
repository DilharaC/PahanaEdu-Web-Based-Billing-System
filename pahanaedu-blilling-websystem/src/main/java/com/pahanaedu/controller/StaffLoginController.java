package com.pahanaedu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahanaedu.model.Staff;
import com.pahanaedu.service.StaffService;

/**
 * Servlet implementation class StaffLoginController
 */
@WebServlet("/StaffLogin")
public class StaffLoginController extends HttpServlet {

    private StaffService staffService = new StaffService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Show change password form if action=changePassword
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        if ("changePassword".equalsIgnoreCase(action)) {
            if (session == null || session.getAttribute("staff") == null) {
                response.sendRedirect("StaffLogin.jsp");
                return;
            }
            request.getRequestDispatcher("WEB-INF/view/ChangePassword.jsp").forward(request, response);
        } else {
            // default to login page
            response.sendRedirect("StaffLogin.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("changePassword".equalsIgnoreCase(action)) {
            // Change password logic
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("staff") == null) {
                response.sendRedirect("StaffLogin.jsp");
                return;
            }

            Staff staff = (Staff) session.getAttribute("staff");
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "New password and confirmation do not match.");
                request.getRequestDispatcher("WEB-INF/view/ChangePassword.jsp").forward(request, response);
                return;
            }

            boolean validCurrent = staffService.checkPassword(staff.getStaffId(), currentPassword);

            if (!validCurrent) {
                request.setAttribute("error", "Current password is incorrect.");
                request.getRequestDispatcher("WEB-INF/view/ChangePassword.jsp").forward(request, response);
                return;
            }

            boolean updated = staffService.updatePassword(staff.getStaffId(), newPassword);

            if (updated) {
                request.setAttribute("success", "Password changed successfully.");
                request.getRequestDispatcher("WEB-INF/view/ChangePassword.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to change password. Try again.");
                request.getRequestDispatcher("WEB-INF/view/ChangePassword.jsp").forward(request, response);
            }

        } else {
            // Existing login logic
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            Staff staff = staffService.login(username, password);

            if (staff != null) {
                HttpSession session = request.getSession();
                session.setAttribute("staff", staff);
                session.setAttribute("role", staff.getRole());
                session.setAttribute("fullName", staff.getFullName());

                if ("admin".equalsIgnoreCase(staff.getRole())) {
                    response.sendRedirect("AdminDashboard?action=dashboard");
                } else {
                    response.sendRedirect("StaffDashboard?action=dashboard");
                }
            } else {
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("StaffLogin.jsp").forward(request, response);
            }
        }
    }
}