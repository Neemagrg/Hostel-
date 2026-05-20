package com.smarthostel.dao.mapper;

import com.smarthostel.model.Room;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RoomMapper {

    private RoomMapper() {}

    public static Room mapRow(ResultSet rs) throws SQLException {
        return new Room(
                rs.getInt("room_id"),
                rs.getString("room_number"),
                rs.getInt("capacity"),
                rs.getInt("occupied"),
                rs.getString("status"));
    }
}
