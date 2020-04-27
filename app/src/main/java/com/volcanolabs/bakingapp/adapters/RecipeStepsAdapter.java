package com.volcanolabs.bakingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.volcanolabs.bakingapp.R;
import com.volcanolabs.bakingapp.Utilities;
import com.volcanolabs.bakingapp.entities.Step;
import com.volcanolabs.bakingapp.interfaces.RecipeStepsListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.ViewHolder> {
    private List<Step> steps = new ArrayList<>();
    private RecipeStepsListener listener;
    private Context context;

    public RecipeStepsAdapter(Context context, RecipeStepsListener listener) {
        this.context = context;
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
        private FrameLayout vgStepNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStep = itemView.findViewById(R.id.tv_step_number);
            tvName = itemView.findViewById(R.id.tv_name);
            vgStepNumber = itemView.findViewById(R.id.vg_step_number);
            itemView.setOnClickListener(this);
        }

        public void bind(Step step) {
            tvName.setText(step.getShortDescription());
            tvStep.setText(String.valueOf(getAdapterPosition() + 1));
            int primaryColor = Utilities.getColorCompat(context, R.color.colorPrimary);
            int dividerColor = Utilities.getColorCompat(context, R.color.dividerColor);

            if (step.isSelected()) {
                vgStepNumber.setBackgroundColor(primaryColor);
            } else {
                vgStepNumber.setBackgroundColor(dividerColor);
            }
        }

        @Override
        public void onClick(View view) {
            listener.onStepClicked(steps.get(getAdapterPosition()));
        }
    }
}
