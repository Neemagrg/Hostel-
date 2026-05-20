package com.smarthostel.service.dto;

import com.smarthostel.model.Room;
import com.smarthostel.model.User;

import java.util.List;

public class SearchResults {
    private final List<User> resultsByName;
    private final List<User> resultsByRoom;
    private final List<Room> availableRooms;
    private final String paramName;
    private final String paramRoomNumber;
    private final String listAvailableParam;

    public SearchResults(List<User> resultsByName, List<User> resultsByRoom, List<Room> availableRooms,
                         String paramName, String paramRoomNumber, String listAvailableParam) {
        this.resultsByName = resultsByName;
        this.resultsByRoom = resultsByRoom;
        this.availableRooms = availableRooms;
        this.paramName = paramName;
        this.paramRoomNumber = paramRoomNumber;
        this.listAvailableParam = listAvailableParam;
    }

    public List<User> getResultsByName() {
        return resultsByName;
    }

    public List<User> getResultsByRoom() {
        return resultsByRoom;
    }

    public List<Room> getAvailableRooms() {
        return availableRooms;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamRoomNumber() {
        return paramRoomNumber;
    }

    public String getListAvailableParam() {
        return listAvailableParam;
    }
}
