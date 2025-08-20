package com.pahanaedu.controller;

import com.pahanaedu.model.Staff;
import com.pahanaedu.service.StaffService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/StaffLogin")
public class StaffLoginController extends HttpServlet {

	private StaffService staffService = StaffService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        if ("changePassword".equalsIgnoreCase(action)) {
            if (session == null || session.getAttribute("staff") == null) {
                response.sendRedirect(request.getContextPath() + "/StaffLogin.jsp");
                return;
            }
            request.getRequestDispatcher("/WEB-INF/view/ChangePassword.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/StaffLogin.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("changePassword".equalsIgnoreCase(action)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("staff") == null) {
                response.sendRedirect(request.getContextPath() + "/StaffLogin.jsp");
                return;
            }

            Staff staff = (Staff) session.getAttribute("staff");
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "New password and confirmation do not match.");
                request.getRequestDispatcher("/WEB-INF/view/ChangePassword.jsp").forward(request, response);
                return;
            }

            boolean validCurrent = staffService.checkPassword(staff.getStaffId(), currentPassword);
            if (!validCurrent) {
                request.setAttribute("error", "Current password is incorrect.");
                request.getRequestDispatcher("/WEB-INF/view/ChangePassword.jsp").forward(request, response);
                return;
            }

            boolean updated = staffService.updatePassword(staff.getStaffId(), newPassword);
            if (updated) {
                request.setAttribute("success", "Password changed successfully.");
            } else {
                request.setAttribute("error", "Failed to change password. Try again.");
            }
            request.getRequestDispatcher("/WEB-INF/view/ChangePassword.jsp").forward(request, response);

        } else {
            // Login logic
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            Staff staff = staffService.login(username, password);
            if (staff != null) {
                HttpSession session = request.getSession();
                session.setAttribute("staff", staff);
                session.setAttribute("role", staff.getRole());
                session.setAttribute("fullName", staff.getFullName());
                
                session.setAttribute("staffId", staff.getStaffId());       // for BillController
                session.setAttribute("staffName", staff.getFullName());    // optional, for display

                String role = staff.getRole() != null ? staff.getRole().trim().toLowerCase() : "";

                if ("admin".equals(role)) {
                    response.sendRedirect(request.getContextPath() + "/AdminDashboard?action=dashboard");
                } else {
                    response.sendRedirect(request.getContextPath() + "/StaffDashboard?action=dashboard");
                }
            } else {
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("/StaffLogin.jsp").forward(request, response);
            }
        }
    }
}
