package com.smarthostel.model;

public class AdminDashboardStats {
    private int totalStudents;
    private int roomsWithVacancy;
    private int availableBeds;
    private int totalRooms;
    private int pendingComplaints;
    private int resolvedComplaints;
    private double totalDue;
    private int feesPaidCount;
    private int feesUnpaidCount;

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public int getRoomsWithVacancy() {
        return roomsWithVacancy;
    }

    public void setRoomsWithVacancy(int roomsWithVacancy) {
        this.roomsWithVacancy = roomsWithVacancy;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public int getPendingComplaints() {
        return pendingComplaints;
    }

    public void setPendingComplaints(int pendingComplaints) {
        this.pendingComplaints = pendingComplaints;
    }

    public int getResolvedComplaints() {
        return resolvedComplaints;
    }

    public void setResolvedComplaints(int resolvedComplaints) {
        this.resolvedComplaints = resolvedComplaints;
    }

    public double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(double totalDue) {
        this.totalDue = totalDue;
    }

    public int getFeesPaidCount() {
        return feesPaidCount;
    }

    public void setFeesPaidCount(int feesPaidCount) {
        this.feesPaidCount = feesPaidCount;
    }

    public int getFeesUnpaidCount() {
        return feesUnpaidCount;
    }

    public void setFeesUnpaidCount(int feesUnpaidCount) {
        this.feesUnpaidCount = feesUnpaidCount;
    }
}
