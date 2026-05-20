package com.smarthostel.controller;

import com.smarthostel.model.AdminDashboardStats;
import com.smarthostel.service.DashboardService;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {

    private final DashboardService dashboardService = ServiceFactory.getDashboardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        try {
            AdminDashboardStats stats = dashboardService.loadAdminDashboardStats();
            request.setAttribute("totalStudents", stats.getTotalStudents());
            request.setAttribute("roomsWithVacancy", stats.getRoomsWithVacancy());
            request.setAttribute("availableBeds", stats.getAvailableBeds());
            request.setAttribute("totalRooms", stats.getTotalRooms());
            request.setAttribute("pendingComplaints", stats.getPendingComplaints());
            request.setAttribute("resolvedComplaints", stats.getResolvedComplaints());
            request.setAttribute("totalDue", stats.getTotalDue());
            request.setAttribute("feesPaidCount", stats.getFeesPaidCount());
            request.setAttribute("feesUnpaidCount", stats.getFeesUnpaidCount());
            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("dashboardError", "Could not load statistics: " + e.getMessage());
            request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
        }
    }
}
