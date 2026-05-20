package com.smarthostel.controller;

import com.smarthostel.service.SearchService;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.service.dto.SearchResults;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

    private final SearchService searchService = ServiceFactory.getSearchService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        try {
            SearchResults results = searchService.search(
                    request.getParameter("name"),
                    request.getParameter("roomNumber"),
                    request.getParameter("listAvailable"));
            request.setAttribute("resultsByName", results.getResultsByName());
            request.setAttribute("resultsByRoom", results.getResultsByRoom());
            request.setAttribute("availableRooms", results.getAvailableRooms());
            request.setAttribute("paramName", results.getParamName());
            request.setAttribute("paramRoomNumber", results.getParamRoomNumber());
            request.setAttribute("listAvailable", results.getListAvailableParam());
            request.getRequestDispatcher("admin-search.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("resultsByName", java.util.Collections.emptyList());
            request.setAttribute("resultsByRoom", java.util.Collections.emptyList());
            request.setAttribute("availableRooms", java.util.Collections.emptyList());
            request.setAttribute("paramName", request.getParameter("name") == null ? "" : request.getParameter("name"));
            request.setAttribute("paramRoomNumber", request.getParameter("roomNumber") == null ? "" : request.getParameter("roomNumber"));
            request.setAttribute("listAvailable", request.getParameter("listAvailable"));
            request.setAttribute("searchError", e.getMessage());
            request.getRequestDispatcher("admin-search.jsp").forward(request, response);
        }
    }
}
