package com.piatt.udacity.bakeaide.model;

import android.net.Uri;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;

@Parcel
public class Step {
    @Getter int id;
    @Getter String shortDescription;
    @Getter String description;
    @Setter String videoURL;
    @Getter @Setter String thumbnailURL;

    public Uri getVideoURI() {
        return Uri.parse(videoURL);
    }

    public boolean hasDescription() {
        return !description.equals(shortDescription);
    }

    public boolean hasVideoURL() {
        return videoURL != null && !videoURL.isEmpty();
    }

    public boolean hasThumbnailURL() {
        return thumbnailURL != null && !thumbnailURL.isEmpty();
    }
}