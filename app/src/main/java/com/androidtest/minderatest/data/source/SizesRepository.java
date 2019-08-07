package com.androidtest.minderatest.data.source;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import com.androidtest.minderatest.gallery.domain.model.Sizes;

import java.util.LinkedHashMap;
import java.util.Map;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Concrete implementation to load images sizes from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data.
 */
public class SizesRepository implements SizesDataSource {

    private static SizesRepository INSTANCE = null;

    private final SizesDataSource mSizesRemoteDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Sizes> mCachedSizesList;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    @SuppressLint("RestrictedApi")
    private SizesRepository(@NonNull SizesDataSource sizesRemoteDataSource) {
        mSizesRemoteDataSource = checkNotNull(sizesRemoteDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param sizesRemoteDataSource the backend data source
     * @return the {@link ImageRepository} instance
     */
    public static SizesRepository getInstance(@NonNull SizesDataSource sizesRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new SizesRepository(sizesRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(SizesDataSource)}  to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets tasks from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link com.androidtest.minderatest.data.source.SizesDataSource.LoadSizesCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void getSizes(@NonNull LoadSizesCallback callback, String id) {
        checkNotNull(callback);

        // Respond immediately with cache if available and requested size is in cache
        if (mCachedSizesList != null && mCachedSizesList.get(id) != null) {
            callback.onSizesLoaded(mCachedSizesList.get(id));
            return;
        }

        getSizesFromRemoteDataSource(callback, id);
    }

    @Override
    public void refreshImages() {

    }

    private void getSizesFromRemoteDataSource(@NonNull final LoadSizesCallback callback, final String id) {
        mSizesRemoteDataSource.getSizes(new LoadSizesCallback() {
            @Override
            public void onSizesLoaded(Sizes sizes) {
                refreshCache(id, sizes);
                callback.onSizesLoaded(mCachedSizesList.get(id));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        }, id);
    }

    private void refreshCache(String id, Sizes sizes) {
        if (mCachedSizesList == null) {
            mCachedSizesList = new LinkedHashMap<>();
        }
        mCachedSizesList.put(id, sizes);
    }

}

