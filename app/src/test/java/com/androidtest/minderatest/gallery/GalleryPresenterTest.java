package com.androidtest.minderatest.gallery;

import com.androidtest.minderatest.TestUseCaseScheduler;
import com.androidtest.minderatest.UseCaseHandler;
import com.androidtest.minderatest.data.source.ImageDataSource;
import com.androidtest.minderatest.data.source.ImageRepository;
import com.androidtest.minderatest.data.source.SizesRepository;
import com.androidtest.minderatest.gallery.domain.model.ImageList;
import com.androidtest.minderatest.gallery.domain.model.Photo;
import com.androidtest.minderatest.gallery.domain.model.Photos;
import com.androidtest.minderatest.gallery.domain.usecase.GetImageList;
import com.androidtest.minderatest.gallery.domain.usecase.GetSizes;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the implementation of {@link GalleryPresenter}
 */

public class GalleryPresenterTest {

    private static ImageList IMAGES;

    @Mock
    private ImageRepository mImageRepository;

    @Mock
    private SizesRepository mSizesRepository;

    @Mock
    private GalleryContract.View mGalleryView;

    /**
     * {@link ArgumentCaptor} is a powerful Mockito API to capture argument values and use them to
     * perform further actions or assertions on them.
     */
    @Captor
    private ArgumentCaptor<ImageDataSource.LoadImagesCallback> mLoadImagesCallbackCaptor;

    private GalleryPresenter mGalleryPresenter;

    @Before
    public void setupTasksPresenter() {
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mGalleryPresenter = givenGalleryPresenter();

        // The presenter won't update the view unless it's active.
        when(mGalleryView.isActive()).thenReturn(true);

        // We start the tasks to 3, with one active and two completed
        Photo photo = new Photo();
        photo.setId("31456463045");
        photo.setTitle("my title");

        Photos photos = new Photos();
        photos.setPhoto(new LinkedList<Photo>());
        photos.getPhoto().add(photo);

        ImageList imageList = new ImageList();
        imageList.setPhotos(photos);
       IMAGES = imageList;
    }

    private GalleryPresenter givenGalleryPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        GetImageList getTasks = new GetImageList(mImageRepository);
        GetSizes getSizes = new GetSizes(mSizesRepository);

        return new GalleryPresenter(useCaseHandler, mGalleryView, getTasks, getSizes);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        mGalleryPresenter= givenGalleryPresenter();

        // Then the presenter is set to the view
        verify(mGalleryView).setPresenter(mGalleryPresenter);
    }

    @Test
    public void loadAllImagesFromRepositoryAndLoadIntoView() {
        // Given an initialized TasksPresenter with initialized tasks
        // When loading of Tasks is requested
        mGalleryPresenter.loadImages(true);

        // Callback is captured and invoked with stubbed tasks
        verify(mImageRepository).getImages(mLoadImagesCallbackCaptor.capture(), eq(1));
        mLoadImagesCallbackCaptor.getValue().onImagesLoaded(IMAGES);

        // Then progress indicator is shown
        InOrder inOrder = inOrder(mGalleryView);
        inOrder.verify(mGalleryView).showLoadingIndicator();
        // Then verify if is fragment active
        inOrder.verify(mGalleryView).isActive();
        // Then progress indicator is hidden and all tasks are shown in UI
        inOrder.verify(mGalleryView).removePageLoadingIndicator();
    }


}