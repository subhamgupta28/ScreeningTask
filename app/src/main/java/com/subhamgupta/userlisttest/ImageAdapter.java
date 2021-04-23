package com.subhamgupta.userlisttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
    List<String> urls;
    List<String> description;

    public ImageAdapter(List<String> urls, List<String> description) {
        this.urls = urls;
        this.description = description;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false);
        return new ImageHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        holder.tvtitle.setText(description.get(position));
        Glide.with(holder.imageView.getContext())
                .load(urls.get(position))
                .thumbnail(0.05f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tvtitle;
        public ImageHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.image);
            tvtitle = view.findViewById(R.id.title);
        }
    }
}
