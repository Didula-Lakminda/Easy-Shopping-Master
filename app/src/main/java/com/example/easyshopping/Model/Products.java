package com.example.easyshopping.Model;

public class Products {
    private String product_name ,date,description,image,price,product_id,time,categoryName;

    public Products()
    {

    }

    public Products(String product_name, String date, String description, String image, String price, String product_id, String time,String categoryName) {
        this.product_name = product_name;
        this.date = date;
        this.description = description;
        this.image = image;
        this.price = price;
        this.product_id = product_id;
        this.time = time;
        this.categoryName = categoryName;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
