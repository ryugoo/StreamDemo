package com.r384ta.android.streamdemo.di.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.r384ta.android.streamdemo.util.FragmentUtils;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @Named(ProvideNames.CHILD_FRAGMENT_MANAGER)
    public FragmentManager provideChildFragmentManager() {
        return mFragment.getChildFragmentManager();
    }

    @Provides
    @Named(ProvideNames.CHILD_FRAGMENT_UTILS)
    public FragmentUtils provideChildFragmentUtils(
        @Named(ProvideNames.CHILD_FRAGMENT_MANAGER) FragmentManager fragmentManager) {
        return new FragmentUtils(fragmentManager);
    }
}
