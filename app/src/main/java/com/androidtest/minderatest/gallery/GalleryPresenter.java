package com.androidtest.minderatest.gallery;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.androidtest.minderatest.UseCase;
import com.androidtest.minderatest.UseCaseHandler;
import com.androidtest.minderatest.gallery.domain.model.ImageList;
import com.androidtest.minderatest.gallery.domain.model.Photo;
import com.androidtest.minderatest.gallery.domain.model.Picture;
import com.androidtest.minderatest.gallery.domain.model.Sizes;
import com.androidtest.minderatest.gallery.domain.usecase.GetImageList;
import com.androidtest.minderatest.gallery.domain.usecase.GetSizes;

import java.util.LinkedList;
import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;

public class GalleryPresenter implements GalleryContract.Presenter {

    private final GalleryContract.View mGalleryView;

    private final GetImageList mGetImageList;

    private final GetSizes mGetSizes;

    private final UseCaseHandler mUseCaseHandler;

    private int page = 1;

    private boolean mFirstLoad = true;

    private boolean mBlockNewRequest = false;

    private List<Picture> dataList = new LinkedList<>();

    @SuppressLint("RestrictedApi")
    public GalleryPresenter(@NonNull UseCaseHandler useCaseHandler,
                            @NonNull GalleryContract.View GalleryView,
                            @NonNull GetImageList getImageList,
                            @NonNull GetSizes getSizes) {
        mUseCaseHandler = checkNotNull(useCaseHandler, "usecaseHandler cannot be null");
        mGalleryView = checkNotNull(GalleryView, "galleryView cannot be null!");
        mGetImageList = checkNotNull(getImageList, "getImageList cannot be null!");
        mGetSizes = checkNotNull(getSizes, "getSizes cannot be null!");

        mGalleryView.setPresenter(this);
    }

    @Override
    public void start() {
        loadImages(false);
    }

    @Override
    public void loadImages(boolean forceUpdate) {
        mBlockNewRequest = true;
        // Simplification for sample: a network reload will be forced on first load.
        loadImages(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    @Override
    public void loadSize(final List<Picture> pictureList) {
        int i = 0;
        for (final Picture picture : pictureList) {
            if (picture.getSizes() == null) {
                GetSizes.RequestValues requestValue = new GetSizes.RequestValues(picture.getPhoto().getId());
                final int pos = i;
                mUseCaseHandler.execute(mGetSizes, requestValue,
                        new UseCase.UseCaseCallback<GetSizes.ResponseValue>() {
                            @Override
                            public void onSuccess(GetSizes.ResponseValue response) {
                                Sizes sizes = response.getSizes();
                                pictureList.get(pos).setSizes(sizes);
                                mGalleryView.showImages(dataList.subList(0, pos));
                                // The view may not be able to handle UI updates anymore
                                if (!mGalleryView.isActive()) {
                                    return;
                                }

                                verifySizes();
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
                break;
            }
            i++;
        }
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadImages(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mGalleryView.showLoadingIndicator();
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
                        mGalleryView.removePageLoadingIndicator();
                        processImages(list);
                    }

                    @Override
                    public void onError() {
                        // The view may not be able to handle UI updates anymore
                        if (!mGalleryView.isActive()) {
                            return;
                        }
                        mGalleryView.removeLoadingIndicator();
                        mGalleryView.showLoadingError();
                    }
                });
    }


    @Override
    public void loadNextPage() {
        if (!mBlockNewRequest) {
            mBlockNewRequest = true;
            mGalleryView.showPageLoadingIndicator();
            page++;
            loadImages(true, false);
        }
    }

    private void processImages(ImageList imageList) {
        for (Photo photo : imageList.getPhotos().getPhoto()) {
            Picture picture = new Picture();
            picture.setPhoto(photo);
            dataList.add(picture);
        }

        verifySizes();
    }

    private void verifySizes() {
        //iterates till all sizes been loaded
        if (dataList.get(dataList.size() - 1).getSizes() == null) {
            loadSize(dataList);
        } else {
            mGalleryView.showImages(dataList);
            mBlockNewRequest = false;
        }
    }


}
