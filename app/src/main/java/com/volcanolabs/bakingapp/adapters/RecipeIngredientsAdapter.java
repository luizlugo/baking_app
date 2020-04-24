package com.volcanolabs.bakingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.volcanolabs.bakingapp.R;
import com.volcanolabs.bakingapp.entities.Ingredient;

import java.util.List;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.ViewHolder> {
    private List<Ingredient> ingredients;

    public void setData(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ingredient_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (ingredients != null && ingredients.size() > 0) {
            return ingredients.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ingredients.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
        }

        public void bind(Ingredient ingredient) {
            tvName.setText(ingredient.getIngredient());
            String quantity = "%s %s";
            tvQuantity.setText(String.format(quantity, ingredient.getQuantity(), ingredient.getMeasure()));
        }
    }
}
