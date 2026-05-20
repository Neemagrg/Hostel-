
package com.smarthostel.dao.impl;

import com.smarthostel.dao.DaoException;
import com.smarthostel.dao.FeeDao;
import com.smarthostel.dao.mapper.FeeMapper;
import com.smarthostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeeDaoImpl implements FeeDao {

    @Override
    public List<Fee> findAllWithStudentNames() {
        String sql = "SELECT f.*, u.name AS user_name FROM fees f JOIN users u ON f.user_id = u.user_id ORDER BY u.name";
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            List<Fee> fees = new ArrayList<>();
            while (rs.next()) {
                fees.add(FeeMapper.mapRow(rs));
            }
            return fees;
        } catch (SQLException e) {
            throw new DaoException("findAllWithStudentNames failed", e);
        }
    }

    @Override
    public Optional<Fee> findByUserId(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM fees WHERE user_id = ?")) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(FeeMapper.mapRow(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("findByUserId failed", e);
        }
    }

    @Override
    public void upsert(int userId, double totalAmount, double paidAmount, double dueAmount, String status) {
        try (Connection conn = DBConnection.getConnection()) {
            boolean exists = false;
            try (PreparedStatement check = conn.prepareStatement("SELECT fee_id FROM fees WHERE user_id = ?")) {
                check.setInt(1, userId);
                try (ResultSet rs = check.executeQuery()) {
                    exists = rs.next();
                }
            }
            if (exists) {
                try (PreparedStatement pstmt = conn.prepareStatement(
                        "UPDATE fees SET total_amount=?, paid_amount=?, due_amount=?, status=? WHERE user_id=?")) {
                    pstmt.setDouble(1, totalAmount);
                    pstmt.setDouble(2, paidAmount);
                    pstmt.setDouble(3, dueAmount);
                    pstmt.setString(4, status);
                    pstmt.setInt(5, userId);
                    pstmt.executeUpdate();
                }
            } else {
                try (PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO fees (user_id, total_amount, paid_amount, due_amount, status) VALUES (?,?,?,?,?)")) {
                    pstmt.setInt(1, userId);
                    pstmt.setDouble(2, totalAmount);
                    pstmt.setDouble(3, paidAmount);
                    pstmt.setDouble(4, dueAmount);
                    pstmt.setString(5, status);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("upsert fee failed", e);
        }
    }
}
