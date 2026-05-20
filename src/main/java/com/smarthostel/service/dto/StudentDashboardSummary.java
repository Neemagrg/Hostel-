package com.smarthostel.service.dto;

public class StudentDashboardSummary {
    private final String allocatedRoomLabel;
    private final String feeStatus;
    private final double dueAmount;

    public StudentDashboardSummary(String allocatedRoomLabel, String feeStatus, double dueAmount) {
        this.allocatedRoomLabel = allocatedRoomLabel;
        this.feeStatus = feeStatus;
        this.dueAmount = dueAmount;
    }

    public String getAllocatedRoomLabel() {
        return allocatedRoomLabel;
    }

    public String getFeeStatus() {
        return feeStatus;
    }

    public double getDueAmount() {
        return dueAmount;
    }
}
