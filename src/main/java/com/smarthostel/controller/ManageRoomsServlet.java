package com.smarthostel.controller;

import com.smarthostel.dao.DaoException;
import com.smarthostel.service.RoomAllocationService;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.service.dto.ManageRoomsView;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@WebServlet("/ManageRoomsServlet")
public class ManageRoomsServlet extends HttpServlet {

    private final RoomAllocationService roomAllocationService = ServiceFactory.getRoomAllocationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        try {
            ManageRoomsView view = roomAllocationService.loadManageRoomsPage();
            request.setAttribute("rooms", view.getRooms());
            request.setAttribute("students", view.getStudentsEligibleForAllocation());
            request.setAttribute("requests", view.getPendingRequests());
            request.getRequestDispatcher("manage-rooms.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("rooms", Collections.emptyList());
            request.setAttribute("students", Collections.emptyList());
            request.setAttribute("requests", Collections.emptyList());
            request.setAttribute("error", "Could not load rooms: " + e.getMessage());
            request.getRequestDispatcher("manage-rooms.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        String action = request.getParameter("action");
        try {
            if ("add".equals(action)) {
                roomAllocationService.addRoom(
                        request.getParameter("roomNumber"),
                        Integer.parseInt(request.getParameter("capacity")));
            } else if ("allocate".equals(action)) {
                roomAllocationService.manualAllocate(
                        Integer.parseInt(request.getParameter("roomId")),
                        Integer.parseInt(request.getParameter("userId")));
            } else if ("approve".equals(action)) {
                roomAllocationService.approvePendingAllocation(Integer.parseInt(request.getParameter("allocId")));
            } else if ("reject".equals(action)) {
                roomAllocationService.rejectPendingAllocation(Integer.parseInt(request.getParameter("allocId")));
            }
        } catch (DaoException | IllegalArgumentException e) {
            e.printStackTrace();
            response.sendRedirect("ManageRoomsServlet?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ManageRoomsServlet?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
            return;
        }
        response.sendRedirect("ManageRoomsServlet");
    }
}
