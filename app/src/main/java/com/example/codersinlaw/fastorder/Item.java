package com.example.codersinlaw.fastorder;

public class Item {
    private String title;
    private String imgURL;

    private boolean expanded;
    private int id;

    Item(String title, String imgURL, int id) {
        this.title = title;
        this.imgURL = imgURL;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return title;
    }
    public String getURL() {
        return imgURL;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
}
