
package com.smarthostel.dao.impl;

import com.smarthostel.dao.DaoException;
import com.smarthostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AllocationDaoImpl implements AllocationDao {

    @Override
    public List<Allocation> findPendingRequestsWithDetails() {
        String sql = "SELECT a.*, u.name AS user_name, r.room_number FROM allocations a "
                + "JOIN users u ON a.user_id = u.user_id JOIN rooms r ON a.room_id = r.room_id "
                + "WHERE a.status = 'pending' ORDER BY a.allocation_date DESC";
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            List<Allocation> requests = new ArrayList<>();
            while (rs.next()) {
                Allocation a = new Allocation();
                a.setAllocationId(rs.getInt("allocation_id"));
                a.setUserName(rs.getString("user_name"));
                a.setRoomNumber(rs.getString("room_number"));
                a.setAllocationDate(rs.getDate("allocation_date"));
                requests.add(a);
            }
            return requests;
        } catch (SQLException e) {
            throw new DaoException("findPendingRequestsWithDetails failed", e);
        }
    }

    @Override
    public boolean hasPendingOrActive(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement check = conn.prepareStatement(
                     "SELECT 1 FROM allocations WHERE user_id = ? AND status IN ('pending', 'active')")) {
            check.setInt(1, userId);
            try (ResultSet rs = check.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DaoException("hasPendingOrActive failed", e);
        }
    }

    @Override
    public void insertPendingApplication(int userId, int roomId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO allocations (user_id, room_id, allocation_date, status) VALUES (?, ?, CURRENT_DATE, 'pending')")) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, roomId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("insertPendingApplication failed", e);
        }
    }

    @Override
    public Optional<String> findActiveRoomNumberForStudent(int userId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT r.room_number FROM allocations a JOIN rooms r ON a.room_id = r.room_id "
                             + "WHERE a.user_id = ? AND a.status = 'active'")) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getString("room_number"));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("findActiveRoomNumberForStudent failed", e);
        }
    }

    @Override
    public boolean hasActiveAllocation(Connection conn, int userId) {
        try (PreparedStatement active = conn.prepareStatement(
                "SELECT 1 FROM allocations WHERE user_id = ? AND status = 'active'")) {
            active.setInt(1, userId);
            try (ResultSet rs = active.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DaoException("hasActiveAllocation failed", e);
        }
    }

    @Override
    public boolean hasOtherActiveAllocation(Connection conn, int userId, int excludeAllocationId) {
        try (PreparedStatement block = conn.prepareStatement(
                "SELECT 1 FROM allocations WHERE user_id = ? AND status = 'active' AND allocation_id <> ?")) {
            block.setInt(1, userId);
            block.setInt(2, excludeAllocationId);
            try (ResultSet rs = block.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DaoException("hasOtherActiveAllocation failed", e);
        }
    }

    @Override
    public void deletePendingForUser(Connection conn, int userId) {
        try (PreparedStatement del = conn.prepareStatement(
                "DELETE FROM allocations WHERE user_id = ? AND status = 'pending'")) {
            del.setInt(1, userId);
            del.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("deletePendingForUser failed", e);
        }
    }

    @Override
    public void insertActive(Connection conn, int userId, int roomId) {
        try (PreparedStatement ins = conn.prepareStatement(
                "INSERT INTO allocations (user_id, room_id, allocation_date, status) VALUES (?, ?, CURDATE(), 'active')")) {
            ins.setInt(1, userId);
            ins.setInt(2, roomId);
            ins.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("insertActive failed", e);
        }
    }

    @Override
    public Optional<PendingAllocationInfo> findPendingAllocation(Connection conn, int allocationId) {
        try (PreparedStatement getInfo = conn.prepareStatement(
                "SELECT room_id, user_id FROM allocations WHERE allocation_id = ? AND status = 'pending'")) {
            getInfo.setInt(1, allocationId);
            try (ResultSet rs = getInfo.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(new PendingAllocationInfo(rs.getInt("room_id"), rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            throw new DaoException("findPendingAllocation failed", e);
        }
    }

    @Override
    public void updateAllocationStatus(Connection conn, int allocationId, String status) {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE allocations SET status = ? WHERE allocation_id = ?")) {
            ps.setString(1, status);
            ps.setInt(2, allocationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("updateAllocationStatus failed", e);
        }
    }

    @Override
    public void rejectOtherPendingForUser(Connection conn, int userId, int exceptAllocationId) {
        try (PreparedStatement rejectOthers = conn.prepareStatement(
                "UPDATE allocations SET status = 'rejected' WHERE user_id = ? AND status = 'pending' AND allocation_id <> ?")) {
            rejectOthers.setInt(1, userId);
            rejectOthers.setInt(2, exceptAllocationId);
            rejectOthers.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("rejectOtherPendingForUser failed", e);
        }
    }

    @Override
    public void rejectPending(Connection conn, int allocationId) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE allocations SET status = 'rejected' WHERE allocation_id = ? AND status = 'pending'")) {
            pstmt.setInt(1, allocationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("rejectPending failed", e);
        }
    }
}
