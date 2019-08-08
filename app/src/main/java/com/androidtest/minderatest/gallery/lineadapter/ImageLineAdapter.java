package com.androidtest.minderatest.gallery.lineadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.androidtest.minderatest.R;
import com.androidtest.minderatest.gallery.domain.model.Photo;
import com.androidtest.minderatest.gallery.domain.model.Picture;
import com.androidtest.minderatest.gallery.domain.model.Size;
import com.androidtest.minderatest.gallery.domain.model.Sizes;
import com.androidtest.minderatest.gallery.lineholder.ImageLineHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class ImageLineAdapter extends RecyclerView.Adapter<ImageLineHolder> {

    private List<Picture> entitiesArray;

    public ImageLineAdapter(ArrayList data) {
        entitiesArray = data;
    }

    public void replaceData(List<Picture> data) {
        setList(data);
        notifyDataSetChanged();
    }

    private void setList(List<Picture> data) {
        entitiesArray = checkNotNull(data);
    }

    @Override
    public ImageLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageLineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ImageLineHolder holder, final int position) {
        Photo entity = entitiesArray.get(position).getPhoto();
        Sizes sizes = entitiesArray.get(position).getSizes();

        if (sizes != null) {
            Size sizeEntity = entitiesArray.get(position).getLargeSquareLabel();
            Picasso.get()
                    .load(sizeEntity.getSource())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.iv_image);
        }


        //holder.tv_data.setText(entity.getName());
        holder.tv_title.setText(entity.getTitle());


    }

    @Override
    public int getItemCount() {
        return entitiesArray != null ? entitiesArray.size() : 0;
    }
}
