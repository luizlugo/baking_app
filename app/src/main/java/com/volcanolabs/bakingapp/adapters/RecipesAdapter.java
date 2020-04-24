package com.volcanolabs.bakingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.volcanolabs.bakingapp.R;
import com.volcanolabs.bakingapp.entities.Recipe;
import com.volcanolabs.bakingapp.interfaces.RecipesListener;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
    private List<Recipe> recipes = new ArrayList<>();
    private RecipesListener recipesListener;

    public void setData(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public RecipesAdapter(RecipesListener recipesListener) {
        this.recipesListener = recipesListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        if (recipes.size() > 0) {
            return recipes.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivRecipe;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRecipe = itemView.findViewById(R.id.iv_recipe);
            tvName = itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        public void bind(Recipe recipe) {
            if (recipe.getImage() != null && !recipe.getImage().isEmpty()) {
                Picasso.get().load(recipe.getImage()).into(ivRecipe);
            } else {
                Picasso.get().load(R.drawable.food_placeholder).into(ivRecipe);
            }

            tvName.setText(recipe.getName());
        }

        @Override
        public void onClick(View view) {
            recipesListener.onRecipeClicked(recipes.get(getAdapterPosition()));
        }
    }
}
