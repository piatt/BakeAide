package com.piatt.udacity.bakeaide.model;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

@Parcel
public class Ingredient {
    @Getter @Setter float quantity;
    @Getter @Setter String measure;
    @Getter @Setter String ingredient;
}