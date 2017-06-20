package com.piatt.udacity.bakeaide.model;

import org.parceler.Parcel;

import java.text.NumberFormat;

import lombok.Getter;
import lombok.Setter;

@Parcel
public class Ingredient {
    private final String SPACE = " ";
    private final String GENERIC_MEASURE = "UNIT";

    @Getter @Setter float quantity;
    @Getter @Setter String measure;
    @Getter @Setter String ingredient;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NumberFormat.getInstance().format(quantity)).append(SPACE);
        if (measure != null && !measure.equals(GENERIC_MEASURE)) {
            sb.append(measure).append(SPACE);
        }
        sb.append(ingredient);

        return sb.toString();
    }
}