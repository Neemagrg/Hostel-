package com.smarthostel.controller;

import com.smarthostel.model.User;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.service.StudentPortalService;
import com.smarthostel.service.dto.StudentDashboardSummary;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/StudentDashboardServlet")
public class StudentDashboardServlet extends HttpServlet {

    private final StudentPortalService studentPortalService = ServiceFactory.getStudentPortalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireStudent(request, response)) {
            return;
        }
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        String msg = request.getParameter("msg");
        if (msg != null) {
            request.setAttribute("flashMsg", msg);
        }

        try {
            StudentDashboardSummary summary = studentPortalService.loadDashboardSummary(user.getUserId());
            request.setAttribute("roomNumber", summary.getAllocatedRoomLabel());
            request.setAttribute("feeStatus", summary.getFeeStatus());
            request.setAttribute("dueAmount", summary.getDueAmount());
            request.getRequestDispatcher("student-dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("student-dashboard.jsp").forward(request, response);
        }
    }
}
