package com.example.codersinlaw.fastorder;

class OrderItem {
    private String title;
    private int price;

    public OrderItem(String title, int price) {
        this.title = title;
        this.price = price;
    }

    public String getName() {
        return title;
    }
    public int getPrice() {
        return price;
    }
}
