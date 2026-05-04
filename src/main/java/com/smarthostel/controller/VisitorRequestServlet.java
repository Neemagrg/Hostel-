package com.smarthostel.controller;

import com.smarthostel.service.ServiceFactory;
import com.smarthostel.service.VisitorService;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/VisitorRequestServlet")
public class VisitorRequestServlet extends HttpServlet {

    private final VisitorService visitorService = ServiceFactory.getVisitorService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireStudent(request, response)) {
            return;
        }
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        try {
            request.setAttribute("visitors", visitorService.listForStudent(user.getUserId()));
            request.getRequestDispatcher("student-visitors.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("student-visitors.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireStudent(request, response)) {
            return;
        }
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        try {
            visitorService.submitVisitorRequest(
                    user.getUserId(),
                    request.getParameter("visitorName"),
                    request.getParameter("visitDate"),
                    request.getParameter("purpose"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("VisitorRequestServlet");
    }
}
