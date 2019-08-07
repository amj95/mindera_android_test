package com.androidtest.minderatest.data.source;

import androidx.annotation.NonNull;

import com.androidtest.minderatest.gallery.domain.model.Sizes;

/**
 * Main entry point for accessing size data.
 * For simplicity, only getPhoto() have callbacks.
 */
public interface SizesDataSource {

    interface LoadSizesCallback {

        void onSizesLoaded(Sizes sizes);

        void onDataNotAvailable();
    }

    void getSizes(@NonNull LoadSizesCallback callback, int id);

    void refreshImages();
}
