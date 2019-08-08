package com.androidtest.minderatest.gallery;

import com.androidtest.minderatest.BasePresenter;
import com.androidtest.minderatest.BaseView;
import com.androidtest.minderatest.gallery.domain.model.ImageList;
import com.androidtest.minderatest.gallery.domain.model.Picture;

import java.util.List;

public interface GalleryContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator();

        void showImages(List<Picture> pictureList);

        void showLoadingError();

        void showPageLoadingIndicator();

        void showPageLoadingError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void loadImages(boolean forceUpdate);

        void loadSize(List<Picture> pictureList);

        void loadNextPage();
    }
}

