package com.example.easyshopping.Categories;

public class Category {

    private String Cname, Cdes, image, date, time, c_id;

    public Category()
    {

    }

    public Category(String Cname, String Cdes, String image, String date, String time, String c_id) {
        this.Cname = Cname;
        this.Cdes = Cdes;
        this.c_id = c_id;
        this.image = image;
        this.date = date;
        this.time = time;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String Cname) {
        this.Cname = Cname;
    }

    public String getCdes() {
        return Cdes;
    }

    public void setCdes(String Cdes) {
        this.Cdes = Cdes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }
}

