package com.example;

import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private List<Item> items;

    public Order(int id, int customerId, List<Item> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
