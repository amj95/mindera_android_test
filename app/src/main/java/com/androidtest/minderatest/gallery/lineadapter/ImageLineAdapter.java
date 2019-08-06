package com.androidtest.minderatest.gallery.lineadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.androidtest.minderatest.R;
import com.androidtest.minderatest.gallery.domain.model.Photo;
import com.androidtest.minderatest.gallery.lineholder.ImageLineHolder;

import java.util.ArrayList;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class ImageLineAdapter extends RecyclerView.Adapter<ImageLineHolder> {

    private List<Photo> entitiesArray;

    public ImageLineAdapter(ArrayList data) {
        entitiesArray = data;
    }

    public void replaceData(List<Photo> data) {
        setList(data);
        notifyDataSetChanged();
    }

    private void setList(List<Photo> data) {
        entitiesArray = checkNotNull(data);
    }

    @Override
    public ImageLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageLineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ImageLineHolder holder, final int position) {
        Photo entity = entitiesArray.get(position);

        //holder.tv_data.setText(entity.getName());
        holder.tv_title.setText(entity.getTitle());

    }

    @Override
    public int getItemCount() {
        return entitiesArray != null ? entitiesArray.size() : 0;
    }
}
