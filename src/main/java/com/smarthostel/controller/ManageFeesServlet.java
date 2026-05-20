package com.smarthostel.controller;

import com.smarthostel.service.FeeService;
import com.smarthostel.service.ServiceFactory;
import com.smarthostel.service.dto.FeeManageView;
import com.smarthostel.util.AuthUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ManageFeesServlet")
public class ManageFeesServlet extends HttpServlet {

    private final FeeService feeService = ServiceFactory.getFeeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        try {
            FeeManageView view = feeService.loadFeeManagementPage();
            request.setAttribute("fees", view.getFees());
            request.setAttribute("allStudents", view.getStudentOptions());
            request.getRequestDispatcher("manage-fees.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("manage-fees.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AuthUtil.requireAdmin(request, response)) {
            return;
        }
        try {
            feeService.upsertFeeRecord(
                    Integer.parseInt(request.getParameter("userId")),
                    Double.parseDouble(request.getParameter("totalAmount")),
                    Double.parseDouble(request.getParameter("paidAmount")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("ManageFeesServlet");
    }
}
