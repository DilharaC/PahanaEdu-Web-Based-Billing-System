package com.pahanaedu.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pahanaedu.model.AuditLog;
import com.pahanaedu.model.Staff;
import com.pahanaedu.service.AuditLogService;
import com.pahanaedu.service.StaffService;

/**
 * Servlet implementation class StaffController
 */
@WebServlet("/Staff")
public class StaffController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private StaffService staffService;

    @Override
    public void init() throws ServletException {
        staffService = StaffService.getInstance();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("list")) {
                listStaff(request, response);
            } else if (action.equals("add")) {
                showAddForm(request, response);
            } else if (action.equals("edit")) {
                showEditForm(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                addStaff(request, response);
            } else if ("update".equals(action)) {
                updateStaff(request, response);
            } else if ("delete".equals(action)) {
                deleteStaff(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listStaff(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Staff> staffList = staffService.getAllStaff();
        request.setAttribute("staffList", staffList);
        request.getRequestDispatcher("WEB-INF/view/listStaff.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/addStaff.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        Staff existingStaff = staffService.getStaffById(staffId);
        request.setAttribute("staff", existingStaff);
        request.getRequestDispatcher("WEB-INF/view/editStaff.jsp").forward(request, response);
    }

    private void addStaff(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Staff staff = extractStaffFromRequest(request);
        staffService.createStaff(staff);
        response.sendRedirect("Staff?action=list");
    }

    private void updateStaff(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        Staff updatedStaff = extractStaffFromRequest(request);
        updatedStaff.setStaffId(staffId);
        staffService.updateStaff(updatedStaff);
        response.sendRedirect("Staff?action=list");
    }

    private void deleteStaff(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        staffService.deleteStaff(staffId);
        response.sendRedirect("Staff?action=list");
    }

    private Staff extractStaffFromRequest(HttpServletRequest request) {
        Staff staff = new Staff();
        staff.setUsername(request.getParameter("username"));
        staff.setPassword(request.getParameter("password")); // ⚠ Should hash in production
        staff.setFullName(request.getParameter("fullName"));
        staff.setEmail(request.getParameter("email"));
        staff.setPhone(request.getParameter("phone"));
        staff.setNic(request.getParameter("nic"));
        staff.setJobTitle(request.getParameter("jobTitle"));
        staff.setRole(request.getParameter("role"));
        staff.setStatus(request.getParameter("status"));
        return staff;
    }
}