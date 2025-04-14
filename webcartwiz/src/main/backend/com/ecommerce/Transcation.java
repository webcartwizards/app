package com.ecommerce;

public class Transcation {
    private Customer customer;
    private Product product;
    private double amount;
    private String paymentMethod;
    private String status;

    public Transcation(Customer c, Product prod, double d, String payMethod, String stat){
        this.customer = c;
        this.product = prod;
        this.amount = d;
        this.paymentMethod = payMethod;
        this.status = stat;
    }

    public String getCustomer(){
        return customer.firstName;
    }
    public String getProd(){
        return product.name;
    }
    public double getAmount(){
        return amount;
    }
    public String getStatus(){
        return "Status: " + status;
    }

    public String toString(){
        return "Transaction:" +
                "{ Customer:" + customer.firstName +
                " Product: " + product.name +
                " Amount: " + amount +
                " Status: " + status + " }";
        }
}
