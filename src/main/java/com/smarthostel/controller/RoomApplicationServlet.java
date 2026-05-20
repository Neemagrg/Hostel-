
package com.smarthostel.controller;

import com.smarthostel.dao.DaoException;
import com.smarthostel.service.RoomAllocationService;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/RoomApplicationServlet")
public class RoomApplicationServlet extends HttpServlet {

    private final RoomAllocationService roomAllocationService = ServiceFactory.getRoomAllocationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireStudent(request, response)) {
            return;
        }
        try {
            request.setAttribute("rooms", roomAllocationService.listRoomsAvailableForStudentApplication());
            request.getRequestDispatcher("student-room.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Could not load rooms.");
            request.getRequestDispatcher("student-room.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireStudent(request, response)) {
            return;
        }
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        try {
            roomAllocationService.submitStudentRoomApplication(user.getUserId(), roomId);
            response.sendRedirect("StudentDashboardServlet?msg=" + java.net.URLEncoder.encode(
                    "Application submitted successfully.", "UTF-8"));
        } catch (DaoException e) {
            response.sendRedirect("RoomApplicationServlet?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("RoomApplicationServlet?error=" + java.net.URLEncoder.encode(
                    "Error: " + e.getMessage(), "UTF-8"));
        }
    }
}
