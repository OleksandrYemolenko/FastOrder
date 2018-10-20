package com.example.codersinlaw.fastorder;

public class DishItem extends Item {
    private String price;
    private String description;

    DishItem(String title, String imgURL, int id, String price, String description) {
        super(title, imgURL, id);
        this.price = price;
        this.description = description;
    }

    @Override
    public String getName() {
        return super.getName().substring(0, Math.min(9, super.getName().length()));
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
