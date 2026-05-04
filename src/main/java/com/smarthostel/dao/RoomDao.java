package com.smarthostel.dao;

import java.sql.Connection;
import java.util.List;

public interface RoomDao {

    List<Room> findAllOrdered();

    List<Room> findWithVacancyOrdered();

    void insertRoom(String roomNumber, int capacity);

    /** Increments occupied when occupied &lt; capacity. Returns false if room full or missing. */
    boolean tryIncrementOccupied(Connection conn, int roomId);

    void syncRoomStatus(Connection conn, int roomId);
}
