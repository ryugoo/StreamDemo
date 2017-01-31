package com.r384ta.android.streamdemo.di.component;

import com.r384ta.android.streamdemo.di.module.ActivityModule;
import com.r384ta.android.streamdemo.di.module.AppModule;
import com.r384ta.android.streamdemo.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    ViewModelComponent plus(ViewModelModule viewModelModule);

    ActivityComponent plus(ActivityModule activityModule);
}
