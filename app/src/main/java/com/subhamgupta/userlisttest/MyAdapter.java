package com.subhamgupta.userlisttest;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Details> contents;
    private OnItemClick onItemClick;
    public MyAdapter( List<Details> contents, OnItemClick onItemClick){
        this.contents = contents;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(contents.get(position).getTitle());
        holder.body.setText(contents.get(position).getRevesions());

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView body;

        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.tvtitle);
            body = itemView.findViewById(R.id.tvbody);

            relativeLayout = itemView.findViewById(R.id.linearid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClickItem(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClick.onLongClickItem(getAdapterPosition());
                    return false;
                }
            });


        }
    }

    //writing file to the device storage



}