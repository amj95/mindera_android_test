package com.androidtest.minderatest;

import com.androidtest.minderatest.data.source.ImageDataSource;
import com.androidtest.minderatest.data.source.ImageRepository;
import com.androidtest.minderatest.gallery.GalleryContract;
import com.androidtest.minderatest.gallery.GalleryPresenter;
import com.androidtest.minderatest.gallery.domain.model.ImageList;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GalleryPresenterTest {

    private static List<ImageList> IMAGES;

    @Mock
    private ImageRepository mImageRepository;

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
        mGalleryPresenter = givenTasksPresenter();

        // The presenter won't update the view unless it's active.
        when(mTasksView.isActive()).thenReturn(true);

        // We start the tasks to 3, with one active and two completed
        TASKS = Lists.newArrayList(new Task("Title1", "Description1"),
                new Task("Title2", "Description2", true), new Task("Title3", "Description3", true));
    }

    private GalleryPresenter givenTasksPresenter() {
        UseCaseHandler useCaseHandler = new UseCaseHandler(new TestUseCaseScheduler());
        GetTasks getTasks = new GetTasks(mTasksRepository, new FilterFactory());
        CompleteTask completeTask = new CompleteTask(mTasksRepository);
        ActivateTask activateTask = new ActivateTask(mTasksRepository);
        ClearCompleteTasks clearCompleteTasks = new ClearCompleteTasks(mTasksRepository);

        return new TasksPresenter(useCaseHandler, mTasksView, getTasks, completeTask, activateTask,
                clearCompleteTasks);
    }

}