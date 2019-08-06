package com.androidtest.minderatest.data.source.remote;

import androidx.annotation.NonNull;

import com.androidtest.minderatest.data.source.ImageDataSource;
import com.androidtest.minderatest.data.source.remote.DAO.NetworkHelper;
import com.androidtest.minderatest.gallery.domain.model.ImageList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageListRemoteDataSource implements ImageDataSource {

    private static ImageListRemoteDataSource INSTANCE;

    public static ImageListRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageListRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private ImageListRemoteDataSource() {}

    @Override
    public void getImages(@NonNull final LoadImagesCallback callback, int page) {
        try {
            NetworkHelper.instance()
                    .getImageListDAO()
                    .getImageList(page)
                    .enqueue(new Callback<ImageList>() {
                @Override
                public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                    ImageList data = response.body();
                    callback.onImagesLoaded(data);
                }

                @Override
                public void onFailure(Call<ImageList> call, Throwable t) {
                    callback.onDataNotAvailable();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshImages() {

    }
}
