package com.example.producttracker.model;

public class Product {
    private String type;
    private String imageUrl;
    private double price;
    private int quantity;

    public Product(String type, String imageUrl, double price, int quantity) {
        this.type = type;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}