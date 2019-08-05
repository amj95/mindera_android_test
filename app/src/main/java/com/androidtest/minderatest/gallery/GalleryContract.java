package com.androidtest.minderatest.gallery;

import com.androidtest.minderatest.BasePresenter;
import com.androidtest.minderatest.BaseView;

public interface GalleryContract {

    interface View extends BaseView<Presenter> {

        void showLoadingIndicator();

        void showImages(List<Images> images);
    }

    interface Presenter extends BasePresenter {

    }
}

