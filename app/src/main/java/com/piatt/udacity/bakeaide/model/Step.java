package com.piatt.udacity.bakeaide.model;

import lombok.Getter;
import lombok.Setter;

public class Step {
    @Getter @Setter private int id;
    @Getter @Setter private String shortDescription;
    @Getter @Setter private String description;
    @Getter @Setter private String videoURL;
    @Getter @Setter private String thumbnailURL;
}