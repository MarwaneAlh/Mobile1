package com.example.miamapp;

public class RecipesData {

    String title,healthScore,time,image,instruction;

    public RecipesData(String title, String healthScore, String time, String image, String instruction) {
        this.title = title;
        this.healthScore = healthScore;
        this.time = time;
        this.image = image;
        this.instruction = instruction;
    }

    public RecipesData() {
    }

    public String getTitle() {
        return title;
    }

    public String getHealthScore() {
        return healthScore;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

    public String getInstruction() {
        return instruction;
    }
}
