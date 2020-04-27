package com.volcanolabs.bakingapp.network;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.volcanolabs.bakingapp.Constants.BASE_URL;

public class NetClientInstance {
    private static Retrofit retrofit;

    private static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static RestApi getRestApi(Context context) {
        return getRetrofitInstance(context).create(RestApi.class);
    }
}
