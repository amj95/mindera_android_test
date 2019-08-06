package com.androidtest.minderatest.gallery.domain.usecase;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.androidtest.minderatest.UseCase;
import com.androidtest.minderatest.data.source.ImageDataSource;
import com.androidtest.minderatest.data.source.ImageRepository;
import com.androidtest.minderatest.gallery.domain.model.ImageList;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Fetches the list of images.
 */
public class GetImageList extends UseCase<GetImageList.RequestValues, GetImageList.ResponseValue> {

    private final ImageRepository mImageRepository;

    @SuppressLint("RestrictedApi")
    public GetImageList(@NonNull ImageRepository imageRepository) {
        mImageRepository = checkNotNull(imageRepository, "imageRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        if (values.isForceUpdate()) {
            mImageRepository.refreshImages();
        }

        mImageRepository.getImages(new ImageDataSource.LoadImagesCallback() {
            @Override
            public void onImagesLoaded(ImageList imageList) {
                ResponseValue responseValue = new ResponseValue(imageList);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        }, values.getPage());
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final boolean mForceUpdate;
        private final int mPage;

        public RequestValues(boolean forceUpdate, @NonNull int page) {
            mForceUpdate = forceUpdate;
            mPage = page;
        }

        public boolean isForceUpdate() {
            return mForceUpdate;
        }

        public int getPage() {
            return mPage;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final ImageList mImageList;

        @SuppressLint("RestrictedApi")
        public ResponseValue(@NonNull ImageList imageList) {
            mImageList = checkNotNull(imageList, "imageslist cannot be null!");
        }

        public ImageList getImageList() {
            return mImageList;
        }
    }
}
