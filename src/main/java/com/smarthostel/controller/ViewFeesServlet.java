package com.smarthostel.controller;

import com.smarthostel.model.Fee;
import com.smarthostel.model.User;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.service.StudentPortalService;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/ViewFeesServlet")
public class ViewFeesServlet extends HttpServlet {

    private final StudentPortalService studentPortalService = ServiceFactory.getStudentPortalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireStudent(request, response)) {
            return;
        }
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        try {
            Optional<Fee> feeOpt = studentPortalService.loadFeeForStudent(user.getUserId());
            feeOpt.ifPresent(fee -> request.setAttribute("fee", fee));
            request.getRequestDispatcher("student-fees.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("student-fees.jsp").forward(request, response);
        }
    }
}
