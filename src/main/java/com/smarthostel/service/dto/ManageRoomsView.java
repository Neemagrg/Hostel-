package com.smarthostel.service.dto;

import com.smarthostel.model.Allocation;
import com.smarthostel.model.Room;
import com.smarthostel.model.User;

import java.util.List;

public class ManageRoomsView {
    private final List<Room> rooms;
    private final List<User> studentsEligibleForAllocation;
    private final List<Allocation> pendingRequests;

    public ManageRoomsView(List<Room> rooms, List<User> studentsEligibleForAllocation, List<Allocation> pendingRequests) {
        this.rooms = rooms;
        this.studentsEligibleForAllocation = studentsEligibleForAllocation;
        this.pendingRequests = pendingRequests;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<User> getStudentsEligibleForAllocation() {
        return studentsEligibleForAllocation;
    }

    public List<Allocation> getPendingRequests() {
        return pendingRequests;
    }
}
