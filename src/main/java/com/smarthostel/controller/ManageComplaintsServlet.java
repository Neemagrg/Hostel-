package com.smarthostel.controller;

import com.smarthostel.service.ComplaintService;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ManageComplaintsServlet")
public class ManageComplaintsServlet extends HttpServlet {

    private final ComplaintService complaintService = ServiceFactory.getComplaintService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        try {
            request.setAttribute("complaints", complaintService.listAllForAdmin());
            request.getRequestDispatcher("manage-complaints.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        try {
            complaintService.updateComplaintStatus(
                    Integer.parseInt(request.getParameter("complaintId")),
                    request.getParameter("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("ManageComplaintsServlet");
    }
}
