package com.smarthostel.model;

public class Fee {
    private int feeId;
    private int userId;
    private String userName;
    private double totalAmount;
    private double paidAmount;
    private double dueAmount;
    private String status;

    public Fee() {}

    // Getters and Setters
    public int getFeeId() { return feeId; }
    public void setFeeId(int feeId) { this.feeId = feeId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }
    public double getDueAmount() {
        return dueAmount;
    }
    public void setDueAmount(double dueAmount) { this.dueAmount = dueAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
