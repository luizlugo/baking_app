package com.volcanolabs.bakingapp.adapters;

import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.volcanolabs.bakingapp.R;
import com.volcanolabs.bakingapp.entities.Step;
import com.volcanolabs.bakingapp.interfaces.RecipeStepsListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {
    private List<Step> steps = new ArrayList<>();
    private RecipeStepsListener listener;

    public RecipeStepsAdapter(RecipeStepsListener listener) {
        this.listener = listener;
    }

    public void setData(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeStepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_step_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvStep;
        private TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStep = itemView.findViewById(R.id.tv_step_number);
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this::onClick);
        }

        public void bind(Step step) {
            tvName.setText(step.getShortDescription());
            tvStep.setText(String.valueOf(getAdapterPosition() + 1));
        }

        @Override
        public void onClick(View view) {
            listener.onStepClicked(steps.get(getAdapterPosition()));
        }
    }
}
