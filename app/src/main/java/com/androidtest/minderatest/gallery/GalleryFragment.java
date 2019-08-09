package com.androidtest.minderatest.gallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.androidtest.minderatest.R;
import com.androidtest.minderatest.databinding.GalleryFragBinding;
import com.androidtest.minderatest.gallery.domain.model.Photo;
import com.androidtest.minderatest.gallery.domain.model.Picture;
import com.androidtest.minderatest.gallery.lineadapter.ImageLineAdapter;

import java.util.ArrayList;
import java.util.List;

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
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvImages);

        binding.rvImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    mPresenter.loadNextPage();
                }
            }
        });

        return view;
    }

    @Override
    public void showLoadingIndicator() {
        binding.tvErrorLoading.setVisibility(View.GONE);
        binding.rvImages.setVisibility(View.GONE);
        binding.pbLoading.setVisibility(View.VISIBLE);
        binding.pbLoadingNextPage.setVisibility(View.GONE);
    }

    @Override
    public void removeLoadingIndicator() {
        binding.tvErrorLoading.setVisibility(View.GONE);
        binding.rvImages.setVisibility(View.VISIBLE);
        binding.pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showImages(List<Picture> pictureList) {
        mImageAdapter.replaceData(pictureList);
        removeLoadingIndicator();
    }

    @Override
    public void showLoadingError() {
        binding.tvErrorLoading.setVisibility(View.VISIBLE);
        binding.rvImages.setVisibility(View.GONE);
        binding.pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showPageLoadingIndicator() {
        binding.pbLoadingNextPage.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams params = binding.pbLoadingNextPage.getLayoutParams();
        params.height = 140;
        binding.pbLoadingNextPage.setLayoutParams(params);
    }

    @Override
    public void removePageLoadingIndicator() {
        binding.pbLoadingNextPage.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = binding.pbLoadingNextPage.getLayoutParams();
        params.height = 5;
        binding.pbLoadingNextPage.setLayoutParams(params);
    }

    @Override
    public void showPageLoadingError() {
        binding.tvErrorLoading.setVisibility(View.VISIBLE);
        binding.rvImages.setVisibility(View.GONE);
        binding.pbLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
