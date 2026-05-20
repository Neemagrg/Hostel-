

package com.smarthostel.dao.impl;

import com.smarthostel.dao.DaoException;
import com.smarthostel.dao.mapper.ComplaintMapper;
import com.smarthostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDaoImpl implements ComplaintDao {

    @Override
    public List<Complaint> findAllWithStudentNames() {
        String sql = "SELECT c.*, u.name AS user_name FROM complaints c "
                + "JOIN users u ON c.user_id = u.user_id ORDER BY c.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            List<Complaint> list = new ArrayList<>();
            while (rs.next()) {
                list.add(ComplaintMapper.mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException("findAllWithStudentNames failed", e);
        }
    }

    @Override
    public List<Complaint> findForStudent(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM complaints WHERE user_id = ? ORDER BY created_at DESC")) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Complaint> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(ComplaintMapper.mapRow(rs));
                }
                return list;
            }
        } catch (SQLException e) {
            throw new DaoException("findForStudent failed", e);
        }
    }

    @Override
    public void insert(int userId, String category, String description) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO complaints (user_id, category, description, status) VALUES (?, ?, ?, 'pending')")) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, category);
            pstmt.setString(3, description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("insert complaint failed", e);
        }
    }

    @Override
    public void updateStatus(int complaintId, String status) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE complaints SET status = ? WHERE complaint_id = ?")) {
            pstmt.setString(1, status);
            pstmt.setInt(2, complaintId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("update complaint status failed", e);
        }
    }
}
