package com.example.shopease.models;

public class Product {
    private String id;
    private String title;
    private double price;
    private String imageBase64;
    private String description;
    private String category;
    private String sellerName;
    private int quantity;
    private double deliveryFee;

    // Default constructor (required for Firebase)
    public Product() {
    }

    public Product(String id, String title, double price, String imageBase64,
                   String description, String category, String sellerName, int quantity, double deliveryFee) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageBase64 = imageBase64;
        this.description = description;
        this.category = category;
        this.sellerName = sellerName;
        this.quantity = quantity;
        this.deliveryFee = deliveryFee;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getImageBase64() { return imageBase64; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getSellerName() { return sellerName; }
    public int getQuantity() { return quantity; }
    public double getDeliveryFee() { return deliveryFee; }

    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setPrice(double price) { this.price = price; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setSellerName(String sellerName) { this.sellerName = sellerName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }
}
