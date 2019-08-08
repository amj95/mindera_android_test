package com.androidtest.minderatest.data.source.remote;

import androidx.annotation.NonNull;

import com.androidtest.minderatest.data.source.SizesDataSource;
import com.androidtest.minderatest.data.source.remote.DAO.NetworkHelper;
import com.androidtest.minderatest.gallery.domain.model.SizeItem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SizesRemoteDataSource implements SizesDataSource {

    private static SizesRemoteDataSource INSTANCE;

    public static SizesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SizesRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private SizesRemoteDataSource() {}

    @Override
    public void getSizes(@NonNull final LoadSizesCallback callback, String id) {
        try {
            NetworkHelper.instance()
                    .getSizesDAO()
                    .getSizes(id)
                    .enqueue(new Callback<SizeItem>() {
                        @Override
                        public void onResponse(Call<SizeItem> call, Response<SizeItem> response) {
                            SizeItem sizeItem = response.body();
                            callback.onSizesLoaded(sizeItem.getSizes());
                        }

                        @Override
                        public void onFailure(Call<SizeItem> call, Throwable t) {
                            call.request();
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
