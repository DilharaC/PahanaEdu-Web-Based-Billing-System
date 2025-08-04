package com.pahanaedu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.pahanaedu.service.StaffService;

@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private StaffService staffService;

    @Override
    public void init() throws ServletException {
        staffService = new StaffService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // Always show the form if no action or action=showForm
        if (action == null || action.equals("showForm")) {
            request.getRequestDispatcher("/WEB-INF/view/forgot_password.jsp").forward(request, response);
        } else {
            // If unknown action → just show the form instead of 404
            request.getRequestDispatcher("/WEB-INF/view/forgot_password.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("submitForm")) {
            String email = request.getParameter("email");

            String resetBaseUrl = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + request.getContextPath() + "/reset-password";

            boolean success = staffService.sendPasswordResetLink(email, resetBaseUrl);

            if (success) {
                request.setAttribute("message", "✅ Reset link sent to your email.");
            } else {
                request.setAttribute("error", "❌ Email not found or error occurred.");
            }

            // Show the same form again with success/error message
            request.getRequestDispatcher("/WEB-INF/view/forgot_password.jsp").forward(request, response);

        } else {
            // If unknown action, just reload the form
            request.getRequestDispatcher("/WEB-INF/view/forgot_password.jsp").forward(request, response);
        }
    }
}