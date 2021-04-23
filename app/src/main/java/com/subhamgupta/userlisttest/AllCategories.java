package com.subhamgupta.userlisttest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class AllCategories extends RecyclerView.Adapter<AllCategories.AllCategoryViewHolder> {
    List<String> contents;

    public AllCategories(List<String> contents) {
        this.contents = contents;
    }

    @NonNull
    @Override
    public AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_layout, parent, false);
        return new AllCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryViewHolder holder, int position) {
        holder.category.setText(contents.get(position));
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public static class AllCategoryViewHolder extends RecyclerView.ViewHolder{
        TextView category;
        public AllCategoryViewHolder(@NonNull View viewitem){
            super(viewitem);
            category = viewitem.findViewById(R.id.category);
        }
    }
}
