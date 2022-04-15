package com.example.miamapp;

public class CartData {

    String name,device,price,quantity;

    public CartData(String name, String device, String price,String quantity) {
        this.name = name;
        this.device = device;
        this.price = price;
        this.quantity=quantity;
    }

    public CartData() {
    }

    public String getName() {
        return name;
    }

    public String getDevice() {
        return device;
    }

    public String getPrice() {
        return price;
    }

   public String getQuantity(){ return quantity;}
}




