package com.smarthostel.model;

public class Room {
    private int roomId;
    private String roomNumber;
    private int capacity;
    private int occupied;
    private String status;

    public Room() {}

    public Room(int roomId, String roomNumber, int capacity, int occupied, String status) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.occupied = occupied;
        this.status = status;
    }

    // Getters and Setters
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getOccupied() { return occupied; }
    public void setOccupied(int occupied) { this.occupied = occupied; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public boolean isAvailable() {
        return occupied < capacity;
    }
}
