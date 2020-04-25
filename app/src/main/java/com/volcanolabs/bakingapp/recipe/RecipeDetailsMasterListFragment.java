package com.volcanolabs.bakingapp.recipe;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.volcanolabs.bakingapp.R;
import com.volcanolabs.bakingapp.adapters.RecipeDetailsTabAdapter;
import com.volcanolabs.bakingapp.databinding.FragmentRecipeDetailsMasterListBinding;

public class RecipeDetailsMasterListFragment extends Fragment {
    FragmentRecipeDetailsMasterListBinding binding;
    private static final String IS_TABLET_KEY = "isTabletKey";
    private boolean isTablet;

    public static RecipeDetailsMasterListFragment newInstance(boolean isTablet) {
        Bundle params = new Bundle();
        params.putBoolean(IS_TABLET_KEY, isTablet);
        RecipeDetailsMasterListFragment fragment = new RecipeDetailsMasterListFragment();
        fragment.setArguments(params);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeDetailsMasterListBinding.inflate(inflater, container, false);
        ViewPager2 viewPager = binding.pager;
        TabLayout tabLayout = binding.tabs;

        if (getArguments() != null) {
            isTablet = getArguments().getBoolean(IS_TABLET_KEY);
        }

        viewPager.setAdapter(new RecipeDetailsTabAdapter(requireActivity(), isTablet));
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            Context context = getContext();
            if (context != null) {
                String tabName = (position == 0) ? context.getResources().getString(R.string.steps) : context.getResources().getString(R.string.ingredients);
                tab.setText(tabName);
            }
        }).attach();
        return binding.getRoot();
    }
}
