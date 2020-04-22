package com.volcanolabs.bakingapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.volcanolabs.bakingapp.databinding.ActivityRecipesBinding;

public class RecipesActivity extends AppCompatActivity {
    private ActivityRecipesBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
