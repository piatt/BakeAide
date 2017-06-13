package com.piatt.udacity.bakeaide.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Recipe {
    @Getter @Setter private int id;
    @Getter @Setter private String name;
    @Getter @Setter private int servings;
    @Getter @Setter private String image;
    @Getter @Setter private List<Ingredient> ingredients;
    @Getter @Setter private List<Step> steps;
}