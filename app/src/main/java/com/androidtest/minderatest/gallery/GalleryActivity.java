package com.androidtest.minderatest.gallery;

import android.os.Bundle;

import com.androidtest.minderatest.R;
import com.androidtest.minderatest.UseCaseHandler;
import com.androidtest.minderatest.data.source.ImageRepository;
import com.androidtest.minderatest.data.source.SizesRepository;
import com.androidtest.minderatest.data.source.remote.ImageListRemoteDataSource;
import com.androidtest.minderatest.data.source.remote.SizesRemoteDataSource;
import com.androidtest.minderatest.databinding.GalleryActBinding;
import com.androidtest.minderatest.gallery.domain.usecase.GetImageList;
import com.androidtest.minderatest.gallery.domain.usecase.GetSizes;
import com.androidtest.minderatest.util.ActivityUtils;

import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity {

    GalleryActBinding binding;

    private GalleryPresenter mGalleryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_act);

        GalleryFragment galleryFragment =
                (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (galleryFragment == null) {
            // Create the fragment
            galleryFragment = GalleryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), galleryFragment, R.id.contentFrame);
        }

        mGalleryPresenter = new GalleryPresenter(UseCaseHandler.getInstance(),
                galleryFragment,
                new GetImageList(ImageRepository.getInstance(ImageListRemoteDataSource.getInstance())),
                new GetSizes(SizesRepository.getInstance(SizesRemoteDataSource.getInstance()))
                );
    }
}
