package com.androidtest.minderatest.gallery.lineholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidtest.minderatest.R;

public class ImageLineHolder extends RecyclerView.ViewHolder {

    public TextView tv_title;

    public ImageView iv_image;

    public ImageLineHolder(View itemView) {
        super(itemView);

        tv_title = (TextView) itemView.findViewById(R.id.tv_title);

        iv_image = (ImageView) itemView.findViewById(R.id.iv_image);

    }
}

