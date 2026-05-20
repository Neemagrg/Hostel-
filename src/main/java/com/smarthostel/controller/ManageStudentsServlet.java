package com.smarthostel.controller;

import com.smarthostel.service.ServiceFactory;
import com.smarthostel.service.StudentManagementService;
import com.smarthostel.service.dto.ManageStudentsView;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ManageStudentsServlet")
public class ManageStudentsServlet extends HttpServlet {

    private final StudentManagementService studentManagementService = ServiceFactory.getStudentManagementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        String q = request.getParameter("q");
        String editUserId = request.getParameter("editUserId");
        try {
            ManageStudentsView view = studentManagementService.loadManageStudentsPage(q, editUserId);
            request.setAttribute("students", view.getStudents());
            request.setAttribute("searchQuery", view.getSearchQuery());
            request.setAttribute("editStudent", view.getEditStudent());
            request.getRequestDispatcher("manage-students.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Could not load students: " + e.getMessage());
            request.setAttribute("students", java.util.Collections.emptyList());
            request.getRequestDispatcher("manage-students.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        String action = request.getParameter("action");
        try {
            if ("add".equals(action)) {
                studentManagementService.addStudent(
                        request.getParameter("name"),
                        request.getParameter("email"),
                        request.getParameter("phone"),
                        request.getParameter("password"),
                        request.getParameter("course"),
                        request.getParameter("yearOfStudy"));
            } else if ("edit".equals(action)) {
                studentManagementService.updateStudent(
                        Integer.parseInt(request.getParameter("userId")),
                        request.getParameter("name"),
                        request.getParameter("email"),
                        request.getParameter("phone"),
                        request.getParameter("course"),
                        request.getParameter("yearOfStudy"));
            } else if ("delete".equals(action)) {
                studentManagementService.deleteStudent(Integer.parseInt(request.getParameter("userId")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("ManageStudentsServlet");
    }
}
