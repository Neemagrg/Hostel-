package com.smarthostel.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public final class AuthUtil {

    private AuthUtil() {}

    public static User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("user");
    }

    public static boolean requireLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (getUser(request) == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return false;
        }
        return true;
    }

    public static boolean requireAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!requireLogin(request, response)) {
            return false;
        }
        User u = getUser(request);
        if (u == null || !"admin".equals(u.getRole())) {
            response.sendRedirect(request.getContextPath() + "/StudentDashboardServlet");
            return false;
        }
        return true;
    }

    public static boolean requireStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!requireLogin(request, response)) {
            return false;
        }
        User u = getUser(request);
        if (u == null || !"student".equals(u.getRole())) {
            response.sendRedirect(request.getContextPath() + "/AdminDashboardServlet");
            return false;
        }
        return true;
    }
}
