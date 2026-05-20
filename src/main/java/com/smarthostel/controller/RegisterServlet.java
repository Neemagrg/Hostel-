package com.smarthostel.controller;

import com.smarthostel.dao.DaoException;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.service.StudentManagementService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private final StudentManagementService studentManagementService = ServiceFactory.getStudentManagementService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String course = request.getParameter("course");
        String yearOfStudy = request.getParameter("yearOfStudy");

        try {
            studentManagementService.register(name, email, phone, password, course, yearOfStudy);
            response.sendRedirect("login.jsp?success=" + java.net.URLEncoder.encode(
                    "Account created successfully. You can log in now.", "UTF-8"));
        } catch (DaoException e) {
            Throwable cause = e.getCause();
            String msg = e.getMessage();
            if (cause != null && cause.getMessage() != null && cause.getMessage().contains("Duplicate entry")) {
                request.setAttribute("error", "Email already exists!");
            } else if (msg != null && msg.contains("Duplicate entry")) {
                request.setAttribute("error", "Email already exists!");
            } else {
                request.setAttribute("error", "Database error: " + msg);
            }
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) {
                request.setAttribute("error", "Email already exists!");
            } else {
                request.setAttribute("error", "Database error: " + e.getMessage());
            }
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
