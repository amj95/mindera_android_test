package com.androidtest.minderatest.data.source;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.androidtest.minderatest.gallery.domain.model.ImageList;

import java.util.LinkedHashMap;
import java.util.Map;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Concrete implementation to load images from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data.
 */
public class ImageRepository implements ImageDataSource {

    private static ImageRepository INSTANCE = null;

    private final ImageDataSource mImagesRemoteDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<Integer, ImageList> mCachedImageList;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    @SuppressLint("RestrictedApi")
    private ImageRepository(@NonNull ImageDataSource imagesRemoteDataSource) {
        mImagesRemoteDataSource = checkNotNull(imagesRemoteDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param imagesRemoteDataSource the backend data source
     * @return the {@link ImageRepository} instance
     */
    public static ImageRepository getInstance(@NonNull ImageDataSource imagesRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ImageRepository(imagesRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(ImageDataSource)}  to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets Images from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link com.androidtest.minderatest.data.source.ImageDataSource.LoadImagesCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void getImages(@NonNull LoadImagesCallback callback, int page) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty and requested page is in cache
        if (mCachedImageList != null && mCachedImageList.get(page) != null) {
            callback.onImagesLoaded(mCachedImageList.get(page));
            return;
        }

        getImagesFromRemoteDataSource(callback, page);
    }

    @Override
    public void refreshImages() {

    }

    private void getImagesFromRemoteDataSource(@NonNull final LoadImagesCallback callback, final int page) {
        mImagesRemoteDataSource.getImages(new LoadImagesCallback() {
            @Override
            public void onImagesLoaded(ImageList imageList) {
                refreshCache(imageList, page);
                callback.onImagesLoaded(mCachedImageList.get(page));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        }, page);
    }

    private void refreshCache(ImageList imageList, int page) {
        if (mCachedImageList == null) {
            mCachedImageList = new LinkedHashMap<>();
        }
        mCachedImageList.put(page, imageList);
    }

}
