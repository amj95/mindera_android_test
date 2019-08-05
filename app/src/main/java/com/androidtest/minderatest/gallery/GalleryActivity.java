package com.androidtest.minderatest.gallery;

import android.os.Bundle;

import com.androidtest.minderatest.R;
import com.androidtest.minderatest.databinding.GalleryActBinding;
import com.androidtest.minderatest.databinding.MainActBinding;
import com.androidtest.minderatest.util.ActivityUtils;

import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity {
    GalleryActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_act);

        GalleryFragment galleryFragment =
                (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (galleryFragment == null) {
            // Create the fragment
            galleryFragment = GalleryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), galleryFragment, R.id.contentFrame);
        }

        // Create the presenter
        mTasksPresenter = new TasksPresenter(
                Injection.provideUseCaseHandler(),
                tasksFragment,
                Injection.provideGetTasks(getApplicationContext()),
                Injection.provideCompleteTasks(getApplicationContext()),
                Injection.provideActivateTask(getApplicationContext()),
                Injection.provideClearCompleteTasks(getApplicationContext())
        );
    }
}
