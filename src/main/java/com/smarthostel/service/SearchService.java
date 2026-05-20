package com.smarthostel.service;

import com.smarthostel.dao.RoomDao;
import com.smarthostel.dao.UserDao;
import com.smarthostel.model.Room;
import com.smarthostel.model.User;
import com.smarthostel.service.dto.SearchResults;

import java.util.ArrayList;
import java.util.List;

public class SearchService {

    private final UserDao userDao;
    private final RoomDao roomDao;

    public SearchService(UserDao userDao, RoomDao roomDao) {
        this.userDao = userDao;
        this.roomDao = roomDao;
    }

    public SearchResults search(String nameParam, String roomNumberParam, String listAvailableParam) {
        List<User> byName = new ArrayList<>();
        List<User> byRoom = new ArrayList<>();
        List<Room> availableRooms = new ArrayList<>();

        if (nameParam != null && !nameParam.trim().isEmpty()) {
            byName.addAll(userDao.searchStudentsByNameOrEmail(nameParam));
        }
        if (roomNumberParam != null && !roomNumberParam.trim().isEmpty()) {
            byRoom.addAll(userDao.searchStudentsByAllocatedRoom(roomNumberParam));
        }
        if ("1".equals(listAvailableParam) || "true".equalsIgnoreCase(listAvailableParam)) {
            availableRooms.addAll(roomDao.findWithVacancyOrdered());
        }

        return new SearchResults(
                byName,
                byRoom,
                availableRooms,
                nameParam == null ? "" : nameParam,
                roomNumberParam == null ? "" : roomNumberParam,
                listAvailableParam);
    }
}
