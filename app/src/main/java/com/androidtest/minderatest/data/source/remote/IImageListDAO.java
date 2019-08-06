package com.androidtest.minderatest.data.source.remote;

import com.androidtest.minderatest.gallery.domain.model.ImageList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IImageListDAO {

    @GET("/services/rest/?method=flickr.photos.search&api_key=f9cc014fa76b098f9e82f1c288379ea1&tags=kitten&format=json&nojsoncallback=1")
    Call<ImageList> getImageList(@Query("page") int page);

}


