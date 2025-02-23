package com.example.shopease.models;

public class CartItem {
    private String id;
    private String title;
    private double price;
    private int quantity;
    private String imageBase64;
    private double deliveryFee;
    private String category;

    // Default constructor (Required for Firebase)
    public CartItem() {
    }

    public CartItem(String id, String title, double price, int quantity, String imageBase64, double deliveryFee, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.imageBase64 = imageBase64;
        this.deliveryFee = deliveryFee;
        this.category = category;
    }

    // ✅ Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getImageBase64() { return imageBase64; }
    public double getDeliveryFee() { return deliveryFee; }
    public String getCategory() { return category; }

    // ✅ Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }
    public void setCategory(String category) { this.category = category; }
}
