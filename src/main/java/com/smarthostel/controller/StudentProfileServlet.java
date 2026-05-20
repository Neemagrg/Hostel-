package com.smarthostel.controller;

import com.smarthostel.dao.DaoException;
import com.smarthostel.model.User;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.service.StudentPortalService;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/StudentProfileServlet")
public class StudentProfileServlet extends HttpServlet {

    private final StudentPortalService studentPortalService = ServiceFactory.getStudentPortalService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireStudent(request, response)) {
            return;
        }
        User sessionUser = AuthUtil.getUser(request);
        Optional<User> profileOpt = Optional.empty();

        try {
            profileOpt = studentPortalService.loadStudentProfile(sessionUser.getUserId());
        } catch (DaoException e) {
            request.setAttribute(
                    "warn",
                    "Could not refresh profile from the database. Showing details from your login session. "
                            + "If this persists, ensure MySQL is running and apply sql/migrate_v2.sql if needed.");
        }

        User profileForView = profileOpt.orElse(sessionUser);
        request.setAttribute("profile", profileForView);
        request.setAttribute("profileFromDb", profileOpt.isPresent());

        String allocatedRoom = "Not assigned";
        try {
            allocatedRoom = studentPortalService.loadAllocatedRoomLabel(sessionUser.getUserId());
        } catch (Exception ignored) {
            // non-fatal
        }
        request.setAttribute("allocatedRoom", allocatedRoom);

        request.getRequestDispatcher("student-profile.jsp").forward(request, response);
    }
}
