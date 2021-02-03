package com.example.easyshopping.Model;

public class Cart {

    private String productName , product_id ,price , quantity , discount ;

    public Cart() {
    }

    public Cart(String productName, String product_id, String price, String quantity, String discount) {
        this.productName = productName;
        this.product_id = product_id;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
