package com.piatt.udacity.bakeaide.model;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

@Parcel
public class Step {
    @Getter int id;
    @Getter String shortDescription;
    @Getter String description;
    @Getter @Setter String videoURL;
    @Getter @Setter String thumbnailURL;

    public boolean hasVideoURL() {
        return videoURL != null && !videoURL.isEmpty();
    }

    public boolean hasThumbnailURL() {
        return thumbnailURL != null && !thumbnailURL.isEmpty();
    }
}