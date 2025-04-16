package com.ecommerce;

public class Product {
    public String name;
    private double price;
    private String size;
    private String type;
    int quantity;
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return price;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setSize(String size){
        this.size = size;
    }
    public String getSize(){
        return size;
    }
    public String toString(){
        return "name:" + name + "\n"+ "price:" + price + "\n" + "quantity: " + quantity;

    }
}
