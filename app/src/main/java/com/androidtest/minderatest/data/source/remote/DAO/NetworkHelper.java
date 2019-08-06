package com.androidtest.minderatest.data.source.remote.DAO;

import com.androidtest.minderatest.data.source.remote.IImageListDAO;
import com.androidtest.minderatest.exception.ClassNotInitializedException;
import com.androidtest.minderatest.exception.UnsupportedInitializationException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {

    private static final String BASE_URL = "https://api.flickr.com/services/rest";

    private static NetworkHelper instance;
    private static Retrofit retrofit;

    private NetworkHelper() {
        if (instance != null)
            throw new UnsupportedInitializationException();

        Gson gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static NetworkHelper instance() {
        if (instance == null) {
            synchronized (NetworkHelper.class) {
                if (instance == null) instance = new NetworkHelper();
            }
        }

        return instance;
    }

    public IImageListDAO getImageListDAO() {
        if (instance == null)
            throw new ClassNotInitializedException();

        return retrofit.create(IImageListDAO.class);
    }

}
