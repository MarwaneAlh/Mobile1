package com.example.miamapp;

public class IngredientData {

    String device,name,price;

    public IngredientData(String device, String name, String price) {
        this.device = device;
        this.name = name;
        this.price = price;
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


}
