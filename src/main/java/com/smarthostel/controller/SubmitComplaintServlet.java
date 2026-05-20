package com.smarthostel.controller;

import com.smarthostel.model.User;
import com.smarthostel.service.ComplaintService;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/SubmitComplaintServlet")
public class SubmitComplaintServlet extends HttpServlet {

    private final ComplaintService complaintService = ServiceFactory.getComplaintService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireStudent(request, response)) {
            return;
        }
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        try {
            request.setAttribute("complaints", complaintService.listForStudent(user.getUserId()));
            request.getRequestDispatcher("student-complaints.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Could not load complaints.");
            request.getRequestDispatcher("student-complaints.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireStudent(request, response)) {
            return;
        }
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        if (category == null || category.trim().isEmpty()) {
            category = "other";
        }
        try {
            complaintService.submitComplaint(user.getUserId(), category, description);
            response.sendRedirect("SubmitComplaintServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("SubmitComplaintServlet?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }
}
