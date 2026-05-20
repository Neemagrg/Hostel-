package com.smarthostel.model;

import java.util.Date;

public class Allocation {
    private int allocationId;
    private int userId;
    private String userName;
    private int roomId;
    private String roomNumber;
    private Date allocationDate;
    private String status;

    public Allocation() {}

    // Getters and Setters
    public int getAllocationId() { return allocationId; }
    public void setAllocationId(int allocationId) { this.allocationId = allocationId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public Date getAllocationDate() { return allocationDate; }
    public void setAllocationDate(Date allocationDate) { this.allocationDate = allocationDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
