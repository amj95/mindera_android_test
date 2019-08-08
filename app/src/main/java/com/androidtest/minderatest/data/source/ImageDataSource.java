package com.androidtest.minderatest.data.source;

import androidx.annotation.NonNull;

import com.androidtest.minderatest.gallery.domain.model.ImageList;

/**
 * Main entry point for accessing Image data.
 * For simplicity, only getImages() and getImage() have callbacks.
 */
public interface ImageDataSource {

    interface LoadImagesCallback {

        void onImagesLoaded(ImageList imageList);

        void onDataNotAvailable();
    }

    void getImages(@NonNull LoadImagesCallback callback, int page);

    void refreshImages();
}
