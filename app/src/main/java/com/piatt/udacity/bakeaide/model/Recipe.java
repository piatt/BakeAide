package com.piatt.udacity.bakeaide.model;

import org.parceler.Parcel;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Parcel
public class Recipe {
    @Getter @Setter int id;
    @Getter @Setter String name;
    @Getter @Setter int servings;
    @Getter @Setter String image;
    @Getter @Setter List<Ingredient> ingredients;
    @Getter @Setter List<Step> steps;
}