package com.androidtest.minderatest.gallery;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

import com.androidtest.minderatest.gallery.domain.model.ImageList;

import static androidx.core.util.Preconditions.checkNotNull;

public class GalleryFragment extends Fragment implements GalleryContract.View {

    private GalleryContract.Presenter mPresenter;

    public GalleryFragment() {
        // Requires empty public constructor
    }

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(GalleryContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void showImages(ImageList imageList) {

    }

    @Override
    public void showLoadingError() {

    }
}
