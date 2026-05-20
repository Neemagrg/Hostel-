package com.smarthostel.dao.dto;

public final class PendingAllocationInfo {
    private final int roomId;
    private final int userId;

    public PendingAllocationInfo(int roomId, int userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getUserId() {
        return userId;
    }
}
