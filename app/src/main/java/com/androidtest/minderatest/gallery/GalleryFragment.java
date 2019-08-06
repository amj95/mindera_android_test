package com.androidtest.minderatest.gallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtest.minderatest.R;
import com.androidtest.minderatest.databinding.GalleryFragBinding;
import com.androidtest.minderatest.gallery.domain.model.ImageList;
import com.androidtest.minderatest.gallery.domain.model.Photo;
import com.androidtest.minderatest.gallery.domain.model.Photos;
import com.androidtest.minderatest.gallery.lineadapter.ImageLineAdapter;

import java.util.ArrayList;

import static androidx.core.util.Preconditions.checkNotNull;

public class GalleryFragment extends Fragment implements GalleryContract.View {

    private GalleryContract.Presenter mPresenter;

    private ImageLineAdapter mImageAdapter;

    private GalleryFragBinding binding;

    public GalleryFragment() {
        // Requires empty public constructor
    }

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(GalleryContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageAdapter = new ImageLineAdapter(new ArrayList<Photo>(0));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.gallery_frag, container, false
        );
        View view = binding.getRoot();

        binding.rvImages.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvImages.setAdapter(mImageAdapter);

        binding.rvImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(getContext(), "Last", Toast.LENGTH_LONG).show();

                }
            }
        });

        return view;
    }

    @Override
    public void setLoadingIndicator() {

    }

    @Override
    public void showImages(ImageList imageList) {
        mImageAdapter.replaceData(imageList.getPhotos().getPhoto());
    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
