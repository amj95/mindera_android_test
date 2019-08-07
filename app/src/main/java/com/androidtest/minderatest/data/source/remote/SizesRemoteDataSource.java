package com.androidtest.minderatest.data.source.remote;

import androidx.annotation.NonNull;

import com.androidtest.minderatest.data.source.ImageDataSource;
import com.androidtest.minderatest.data.source.SizeDataSource;
import com.androidtest.minderatest.data.source.remote.DAO.NetworkHelper;
import com.androidtest.minderatest.gallery.domain.model.ImageList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SizeRemoteDataSource implements SizeDataSource {

    private static SizeRemoteDataSource INSTANCE;

    public static SizeRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SizeRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private SizeRemoteDataSource() {}

    @Override
    public void getPhoto(@NonNull final LoadImagesCallback callback, int page) {
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
                            call.request();
                            callback.onDataNotAvailable();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSizes(@NonNull LoadSizesCallback callback, int id) {

    }

    @Override
    public void refreshImages() {

    }
}
