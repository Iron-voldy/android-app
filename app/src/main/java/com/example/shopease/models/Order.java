package com.example.shopease.models;

public class Order {
    private String orderId;
    private String userId;
    private String status;
    private long timestamp;
    private double totalAmount;

    // Default constructor (required for Firebase)
    public Order() {
    }

    // Correct Constructor
    public Order(String orderId, String userId, String status, long timestamp, double totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.timestamp = timestamp;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
