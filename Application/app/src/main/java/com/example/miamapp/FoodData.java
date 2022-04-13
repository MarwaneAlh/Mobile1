package com.example.miamapp;

public class FoodData {

    String name,device,price,resto,category,photo;

    public FoodData(String name, String device, String price, String resto, String category,String photo) {
        this.name = name;
        this.device = device;
        this.price = price;
        this.resto = resto;
        this.category = category;
        this.photo=photo;
    }

    public FoodData() {
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

    public String getResto() {
        return resto;
    }

    public String getCategory() {
        return category;
    }

    public String getPhoto() {
        return photo;
    }
}
