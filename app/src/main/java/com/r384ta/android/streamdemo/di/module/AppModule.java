package com.r384ta.android.streamdemo.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r384ta.android.streamdemo.misc.AndroidDisposable;
import com.r384ta.android.streamdemo.network.HttpBinService;

import java.net.CookieManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    @Named(ProvideNames.APPLICATION_CONTEXT)
    public Context provideApplicationContext() {
        return mContext;
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
            .cookieJar(new JavaNetCookieJar(new CookieManager()))
            .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory
                .createWithScheduler(Schedulers.computation()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://httpbin.org/")
            .build();
    }

    @Provides
    @Singleton
    public HttpBinService provideHttpBinService(Retrofit retrofit) {
        return retrofit.create(HttpBinService.class);
    }

    @Provides
    public AndroidDisposable provideAndroidDisposable() {
        return new AndroidDisposable();
    }
}
