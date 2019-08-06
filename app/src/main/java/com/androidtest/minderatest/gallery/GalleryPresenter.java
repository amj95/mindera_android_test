package com.androidtest.minderatest.gallery;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.androidtest.minderatest.UseCase;
import com.androidtest.minderatest.UseCaseHandler;
import com.androidtest.minderatest.gallery.domain.model.ImageList;
import com.androidtest.minderatest.gallery.domain.usecase.GetImageList;

import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;

public class GalleryPresenter implements GalleryContract.Presenter{

    private final GalleryContract.View mGalleryView;

    private final GetImageList mGetImageList;

    private final UseCaseHandler mUseCaseHandler;

    private int page = 1;

    private boolean mFirstLoad = true;

    @SuppressLint("RestrictedApi")
    public GalleryPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @NonNull GalleryContract.View GalleryView,
                            @NonNull GetImageList getImageList){
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mGalleryView = checkNotNull(GalleryView, "galleryView cannot be null!");
        mGetImageList = checkNotNull(getImageList, "getImageList cannot be null!");

        mGalleryView.setPresenter(this);
    }

    @Override
    public void start() {
        loadImages(false);
    }

    @Override
    public void loadImages(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadTasks(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mGalleryView.setLoadingIndicator();
        }

        GetImageList.RequestValues requestValue = new GetImageList.RequestValues(forceUpdate, page);

        mUseCaseHandler.execute(mGetImageList, requestValue,
                new UseCase.UseCaseCallback<GetImageList.ResponseValue>() {
                    @Override
                    public void onSuccess(GetImageList.ResponseValue response) {
                        ImageList list = response.getImageList();
                        // The view may not be able to handle UI updates anymore
                        if (!mGalleryView.isActive()) {
                            return;
                        }
                        if (showLoadingUI) {
                            mGalleryView.setLoadingIndicator();
                        }

                        processImages(list);
                    }

                    @Override
                    public void onError() {
                        // The view may not be able to handle UI updates anymore
                        if (!mGalleryView.isActive()) {
                            return;
                        }
                        mGalleryView.showLoadingError();
                    }
                });
    }

    @Override
    public void loadNextPage() {

    }

    private void processImages(ImageList imageList){

    }


}
