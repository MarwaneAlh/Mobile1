package com.example.miamapp;

public class IngredientData {

    String device,name,price,photo;

    public IngredientData(String device, String name, String price,String photo) {
        this.device = device;
        this.name = name;
        this.price = price;
        this.photo=photo;
    }

    public IngredientData() {

    }

    public String getDevice() {
        return device;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPhoto() {
        return photo;
    }
}
