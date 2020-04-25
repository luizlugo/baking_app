package com.volcanolabs.bakingapp;

import android.content.Context;
import android.os.Build;

import androidx.annotation.ColorRes;

public class Utilities {
    @SuppressWarnings("deprecation")
    public static int getColorCompat(Context context, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(color);
        } else {
            return context.getResources().getColor(color);
        }
    }
}
