package com.androidtest.minderatest.gallery.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageList {

    @SerializedName("photos")
    @Expose
    private Photo photos;
    @SerializedName("stat")
    @Expose
    private String stat;

    public Photo getPhotos() {
        return photos;
    }

    public void setPhotos(Photo photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}