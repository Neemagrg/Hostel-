package com.smarthostel.service;

import com.smarthostel.dao.DashboardDao;
import com.smarthostel.model.AdminDashboardStats;

public class DashboardService {

    private final DashboardDao dashboardDao;

    public DashboardService(DashboardDao dashboardDao) {
        this.dashboardDao = dashboardDao;
    }

    public AdminDashboardStats loadAdminDashboardStats() {
        return dashboardDao.loadStats();
    }
}
