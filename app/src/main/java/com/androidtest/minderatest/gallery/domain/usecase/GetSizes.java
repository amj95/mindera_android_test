package com.androidtest.minderatest.gallery.domain.usecase;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.androidtest.minderatest.UseCase;
import com.androidtest.minderatest.data.source.SizesDataSource;
import com.androidtest.minderatest.data.source.SizesRepository;
import com.androidtest.minderatest.gallery.domain.model.Sizes;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Fetches the image sizes.
 */
public class GetSizes extends UseCase<GetSizes.RequestValues, GetSizes.ResponseValue> {

    private final SizesRepository mSizesRepository;

    @SuppressLint("RestrictedApi")
    public GetSizes(@NonNull SizesRepository sizesRepository) {
        mSizesRepository = checkNotNull(sizesRepository, "imageRepository cannot be null!");
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        mSizesRepository.getSizes(new SizesDataSource.LoadSizesCallback() {
            @Override
            public void onSizesLoaded(Sizes sizes) {
                ResponseValue responseValue = new ResponseValue(sizes);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        }, values.getId());
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String mId;

        public RequestValues(@NonNull String id) {
            mId = id;
        }

        public String getId() {
            return mId;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Sizes mSizes;

        @SuppressLint("RestrictedApi")
        public ResponseValue(@NonNull Sizes sizes) {
            mSizes = checkNotNull(sizes, "imageslist cannot be null!");
        }

        public Sizes getSizes() {
            return mSizes;
        }
    }
}
