package com.example.codersinlaw.fastorder;

public class DishItem extends Item {
    private String price;
    private String description;

    DishItem(String title, String imgURL, int id, String price, String description) {
        super(title, imgURL, id);
        this.price = price;
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
