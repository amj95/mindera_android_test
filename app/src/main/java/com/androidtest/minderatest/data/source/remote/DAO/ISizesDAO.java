package com.androidtest.minderatest.data.source.remote.DAO;

import com.androidtest.minderatest.gallery.domain.model.SizeItem;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ISizesDAO {
    @GET("/services/rest/?method=flickr.photos.getSizes&api_key=f9cc014fa76b098f9e82f1c288379ea1&format=json&nojsoncallback=1")
    Call<SizeItem> getSizes(@Query("photo_id") String id);
}
