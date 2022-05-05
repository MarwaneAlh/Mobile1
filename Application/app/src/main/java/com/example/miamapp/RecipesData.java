package com.example.miamapp;

import java.util.ArrayList;
import java.util.List;

public class RecipesData {

    String title,healthScore,time,image,instruction;
    List<IngredientData> ingredientsrecipes=new ArrayList<>();

    public RecipesData(String title, String healthScore, String time, String image, String instruction, List<IngredientData> ingredientsrecipes) {
        this.title = title;
        this.healthScore = healthScore;
        this.time = time;
        this.image = image;
        this.instruction = instruction;
        this.ingredientsrecipes = ingredientsrecipes;
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

    public List<IngredientData> getIngredientsrecipes() {
        return ingredientsrecipes;
    }
}
