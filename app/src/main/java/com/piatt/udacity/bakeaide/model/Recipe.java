package com.piatt.udacity.bakeaide.model;

import org.parceler.Parcel;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Parcel
public class Recipe {
    @Getter int id;
    @Getter String name;
    @Getter int servings;
    @Getter @Setter String image;
    @Getter List<Ingredient> ingredients;
    @Getter List<Step> steps;

    public boolean hasServings() {
        return servings > 0;
    }

    public boolean hasImage() {
        return image != null && !image.isEmpty();
    }

    public boolean hasIngredients() {
        return ingredients != null && !ingredients.isEmpty();
    }

    public boolean hasSteps() {
        return steps != null && !steps.isEmpty();
    }
}