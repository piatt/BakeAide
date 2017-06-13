package com.piatt.udacity.bakeaide.model;

import lombok.Getter;
import lombok.Setter;

public class Ingredient {
    @Getter @Setter private float quantity;
    @Getter @Setter private String measure;
    @Getter @Setter private String ingredient;
}