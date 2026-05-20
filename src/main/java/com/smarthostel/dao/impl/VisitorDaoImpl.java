
package com.smarthostel.dao.impl;

import com.smarthostel.dao.DaoException;
import com.smarthostel.dao.VisitorDao;
import com.smarthostel.dao.mapper.VisitorMapper;
import com.smarthostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitorDaoImpl implements VisitorDao {

    @Override
    public List<Visitor> findAllWithStudentNames() {
        String sql = "SELECT v.*, u.name AS student_name FROM visitors v "
                + "JOIN users u ON v.user_id = u.user_id ORDER BY v.visit_date DESC";
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            List<Visitor> list = new ArrayList<>();
            while (rs.next()) {
                list.add(VisitorMapper.mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException("findAllWithStudentNames failed", e);
        }
    }

    @Override
    public List<Visitor> findForStudent(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM visitors WHERE user_id = ? ORDER BY visit_date DESC")) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Visitor> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(VisitorMapper.mapRow(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DaoException("findForStudent failed", e);
        }
    }

    @Override
    public void insert(int userId, String visitorName, String visitDate, String purpose) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO visitors (user_id, visitor_name, visit_date, purpose) VALUES (?, ?, ?, ?)")) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, visitorName);
            pstmt.setString(3, visitDate);
            pstmt.setString(4, purpose);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("insert visitor failed", e);
        }
    }

    @Override
    public void updateStatus(int visitorId, String status) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE visitors SET status = ? WHERE visitor_id = ?")) {
            pstmt.setString(1, status);
            pstmt.setInt(2, visitorId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("update visitor status failed", e);
        }
    }
}
