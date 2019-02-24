package com.example.fortituderecipe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fortituderecipe.Objects.Recipe;
import com.example.fortituderecipe.R;
import com.example.fortituderecipe.Activities.RecipePage;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private static final String TAG = "RecipeAdapter";

    private ArrayList<Recipe> recipes = null;
    private Context mContext;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTimetaken;
        ImageView ivFood;
        ImageView ivRightIcon;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTimetaken = itemView.findViewById(R.id.tv_timetaken);
            ivFood = itemView.findViewById(R.id.iv_food);
            ivRightIcon = itemView.findViewById(R.id.iv_right_icon);
            relativeLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_recipe, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.tvTitle.setText(recipes.get(position).getTitle());
        viewHolder.tvTimetaken.setText(recipes.get(position).getTimeTaken());

        Glide.with(mContext)//load and set the food image
                .asBitmap()
                .load(recipes.get(position).getImageUrl())
                .into(viewHolder.ivFood);

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load the clicked recipe page via activity
                Intent intent = new Intent(mContext, RecipePage.class);
                intent.putExtra("recipe", recipes.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public RecipeAdapter(ArrayList<Recipe> recipes, Context mContext) {
        this.recipes = recipes;
        this.mContext = mContext;
    }
}
