package com.example;

public class OrderItem {
    private int orderId;
    private int itemId;

    public OrderItem(int orderId, int itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
