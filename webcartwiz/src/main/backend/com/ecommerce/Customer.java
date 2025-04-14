package com.ecommerce;

public class Customer{
    private int id;
    public String firstName;
    public String lastName;
    public String address;
    private String email;
    private String username;
    private String password;
    public Customer() {
        this.firstName = "User";
        this.password = "password";
    }
    public Customer(String fname, String lname, String email, String address, String username, String password, int id){
        firstName = fname;
        lastName = lname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.id = id;

    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public int getID(){
        return id;
    }
    public String getAddress(){
        return address;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public void addToCart(Cart cart, CartItem item, int quantity){
        cart.addToCart(item.prod,quantity);
    }
    public Product removeFromCart(Cart cart, CartItem item, int quantity){
        cart.removeFromCart(item,quantity);
        return item.prod;
    }
}
