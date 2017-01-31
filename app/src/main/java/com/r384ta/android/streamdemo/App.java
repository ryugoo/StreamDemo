package com.r384ta.android.streamdemo;

import android.app.Application;
import android.support.annotation.NonNull;

import com.r384ta.android.streamdemo.di.component.AppComponent;
import com.r384ta.android.streamdemo.di.component.DaggerAppComponent;
import com.r384ta.android.streamdemo.di.module.AppModule;


public class App extends Application {
    private AppComponent mAppComponent;

    @NonNull
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
            .appModule(new AppModule(this))
            .build();
    }
}
