package com.piatt.udacity.bakeaide.model;

import org.parceler.Parcel;

import java.text.NumberFormat;

import lombok.Getter;

@Parcel
public class Ingredient {
    @Getter float quantity;
    @Getter String measure;
    @Getter String ingredient;

    public String toString() {
        final String SPACE = " ";
        final String GENERIC_MEASURE = "UNIT";

        StringBuilder sb = new StringBuilder();
        sb.append(NumberFormat.getInstance().format(quantity)).append(SPACE);
        if (measure != null && !measure.equals(GENERIC_MEASURE)) {
            sb.append(measure).append(SPACE);
        }
        sb.append(ingredient);

        return sb.toString();
    }
}