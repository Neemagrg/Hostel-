package com.smarthostel.dao.impl;

import com.smarthostel.dao.DaoException;
import com.smarthostel.dao.DashboardDao;
import com.smarthostel.model.AdminDashboardStats;
import com.smarthostel.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDaoImpl implements DashboardDao {

    @Override
    public AdminDashboardStats loadStats() {
        AdminDashboardStats stats = new AdminDashboardStats();
        try (Connection conn = DBConnection.getConnection()) {
            try (ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM users WHERE role='student'")) {
                if (rs.next()) {
                    stats.setTotalStudents(rs.getInt(1));
                }
            }
            try (ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT COUNT(*) FROM rooms WHERE occupied < capacity")) {
                if (rs.next()) {
                    stats.setRoomsWithVacancy(rs.getInt(1));
                }
            }
            try (ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT COALESCE(SUM(capacity - occupied), 0) FROM rooms")) {
                if (rs.next()) {
                    stats.setAvailableBeds(rs.getInt(1));
                }
            }
            try (ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM rooms")) {
                if (rs.next()) {
                    stats.setTotalRooms(rs.getInt(1));
                }
            }
            try (ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT COUNT(*) FROM complaints WHERE status='pending'")) {
                if (rs.next()) {
                    stats.setPendingComplaints(rs.getInt(1));
                }
            }
            try (ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT COUNT(*) FROM complaints WHERE status='resolved'")) {
                if (rs.next()) {
                    stats.setResolvedComplaints(rs.getInt(1));
                }
            }
            try (ResultSet rs = conn.createStatement().executeQuery("SELECT COALESCE(SUM(due_amount), 0) FROM fees")) {
                if (rs.next()) {
                    stats.setTotalDue(rs.getDouble(1));
                }
            }
            try (ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM fees WHERE status='paid'")) {
                if (rs.next()) {
                    stats.setFeesPaidCount(rs.getInt(1));
                }
            }
            try (ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT COUNT(*) FROM fees WHERE status IN ('unpaid','partial')")) {
                if (rs.next()) {
                    stats.setFeesUnpaidCount(rs.getInt(1));
                }
            }
            return stats;
        } catch (SQLException e) {
            throw new DaoException("loadStats failed", e);
        }
    }
}
