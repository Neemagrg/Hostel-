package com.smarthostel.controller;

import com.smarthostel.service.ServiceFactory;
import com.smarthostel.service.VisitorService;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ManageVisitorsServlet")
public class ManageVisitorsServlet extends HttpServlet {

    private final VisitorService visitorService = ServiceFactory.getVisitorService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        try {
            request.setAttribute("visitors", visitorService.listAllForAdmin());
            request.getRequestDispatcher("manage-visitors.jsp").forward(request, response);
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
            visitorService.updateVisitorStatus(
                    Integer.parseInt(request.getParameter("visitorId")),
                    request.getParameter("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("ManageVisitorsServlet");
    }
}
