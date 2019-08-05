package com.androidtest.minderatest.gallery;

import com.androidtest.minderatest.BasePresenter;
import com.androidtest.minderatest.BaseView;
import com.androidtest.minderatest.gallery.domain.model.ImageList;

public interface GalleryContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean showLoadingUI);

        void showImages(ImageList imageList);

        void showLoadingError();

        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void loadImages(boolean forceUpdate);

        void loadNextPage();
    }
}

