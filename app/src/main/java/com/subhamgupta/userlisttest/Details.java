package com.subhamgupta.userlisttest;

public class Details {
    private String title, revesions;

    public Details(String title, String revesions) {
        this.title = title;
        this.revesions = revesions;
    }
    public Details(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRevesions() {
        return revesions;
    }

    public void setRevesions(String revesions) {
        this.revesions = revesions;
    }
}
