package com.example.codersinlaw.fastorder;

public class DishItem extends Item {
    private String price;
    private String description;
    private boolean fullName = false;

    DishItem(String title, String imgURL, int id, String price, String description) {
        super(title, imgURL, id);
        this.price = price;
        this.description = description;
    }

    //TODO Проверить с пробелами

    @Override
    public String getName() {
        return fullName == true ? super.getName() : super.getName().substring(0, Math.min(9, super.getName().length())) + "...";
    }

    public void setFullName(boolean fullName) {
        this.fullName = fullName;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean getVis() {
        return fullName;
    }
}
