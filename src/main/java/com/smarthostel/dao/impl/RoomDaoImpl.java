package com.smarthostel.dao.impl;

import com.smarthostel.dao.DaoException;
import com.smarthostel.dao.RoomDao;
import com.smarthostel.dao.mapper.RoomMapper;
import com.smarthostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements RoomDao {

    @Override
    public List<Room> findAllOrdered() {
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM rooms ORDER BY room_number")) {
            List<Room> rooms = new ArrayList<>();
            while (rs.next()) {
                rooms.add(RoomMapper.mapRow(rs));
            }
            return rooms;
        } catch (SQLException e) {
            throw new DaoException("findAllOrdered failed", e);
        }
    }

    @Override
    public List<Room> findWithVacancyOrdered() {
        try (Connection conn = DBConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery(
                     "SELECT * FROM rooms WHERE occupied < capacity ORDER BY room_number")) {
            List<Room> rooms = new ArrayList<>();
            while (rs.next()) {
                rooms.add(RoomMapper.mapRow(rs));
            }
            return rooms;
        } catch (SQLException e) {
            throw new DaoException("findWithVacancyOrdered failed", e);
        }
    }

    @Override
    public void insertRoom(String roomNumber, int capacity) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO rooms (room_number, capacity, occupied, status) VALUES (?, ?, 0, 'available')")) {
            pstmt.setString(1, roomNumber);
            pstmt.setInt(2, capacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("insertRoom failed", e);
        }
    }

    @Override
    public boolean tryIncrementOccupied(Connection conn, int roomId) {
        try (PreparedStatement upd = conn.prepareStatement(
                "UPDATE rooms SET occupied = occupied + 1 WHERE room_id = ? AND occupied < capacity")) {
            upd.setInt(1, roomId);
            return upd.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("tryIncrementOccupied failed", e);
        }
    }

    @Override
    public void syncRoomStatus(Connection conn, int roomId) {
        try (PreparedStatement s = conn.prepareStatement("SELECT capacity, occupied FROM rooms WHERE room_id = ?")) {
            s.setInt(1, roomId);
            try (ResultSet rs = s.executeQuery()) {
                if (!rs.next()) {
                    return;
                }
                int cap = rs.getInt("capacity");
                int occ = rs.getInt("occupied");
                String st = occ >= cap ? "full" : "available";
                try (PreparedStatement u = conn.prepareStatement("UPDATE rooms SET status = ? WHERE room_id = ?")) {
                    u.setString(1, st);
                    u.setInt(2, roomId);
                    u.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("syncRoomStatus failed", e);
        }
    }
}
