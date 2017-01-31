package com.r384ta.android.streamdemo.di.module;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.r384ta.android.streamdemo.util.FragmentUtils;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Provides
    @Named(ProvideNames.PARENT_FRAGMENT_MANAGER)
    public FragmentManager provideSupportFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Provides
    @Named(ProvideNames.PARENT_FRAGMENT_UTILS)
    public FragmentUtils provideFragmentUtils(
        @Named(ProvideNames.PARENT_FRAGMENT_MANAGER) FragmentManager fragmentManager) {
        return new FragmentUtils(fragmentManager);
    }
}
