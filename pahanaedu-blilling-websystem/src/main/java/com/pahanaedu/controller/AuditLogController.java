package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahanaedu.dao.AuditLogDAO;
import com.pahanaedu.dao.DBConnectionFactory;
import com.pahanaedu.model.AuditLog;

@WebServlet("/AuditLog")
public class AuditLogController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private AuditLogDAO auditLogDAO;

    @Override
    public void init() throws ServletException {
       
    	 auditLogDAO = new AuditLogDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staff") == null) {
            response.sendRedirect(request.getContextPath() + "/StaffLogin.jsp");
            return;
        }

        try (Connection conn = DBConnectionFactory.getConnection()) { // Singleton-safe connection
            // Retrieve all audit logs
            List<AuditLog> auditLogs = auditLogDAO.getAllLogs(conn);
            request.setAttribute("auditLogs", auditLogs); // pass to JSP
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error loading audit logs", e);
        }

        // Forward to auditLog.jsp
        request.getRequestDispatcher("/WEB-INF/view/auditLog.jsp").forward(request, response);
    }
}