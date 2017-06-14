package com.piatt.udacity.bakeaide.model;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

@Parcel
public class Step {
    @Getter @Setter int id;
    @Getter @Setter String shortDescription;
    @Getter @Setter String description;
    @Getter @Setter String videoURL;
    @Getter @Setter String thumbnailURL;
}