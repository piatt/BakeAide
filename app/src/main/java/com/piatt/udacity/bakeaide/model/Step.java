package com.piatt.udacity.bakeaide.model;

import org.parceler.Parcel;

import lombok.Getter;

@Parcel
public class Step {
    @Getter int id;
    @Getter String shortDescription;
    @Getter String description;
    @Getter String videoURL;
    @Getter String thumbnailURL;
}