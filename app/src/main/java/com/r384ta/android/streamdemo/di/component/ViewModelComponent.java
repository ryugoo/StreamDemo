package com.r384ta.android.streamdemo.di.component;

import com.r384ta.android.streamdemo.di.module.ViewModelModule;
import com.r384ta.android.streamdemo.viewmodel.LwsViewModel;
import com.r384ta.android.streamdemo.viewmodel.RxJavaViewModel;

import dagger.Subcomponent;

@Subcomponent(modules = {ViewModelModule.class})
public interface ViewModelComponent {
    void inject(RxJavaViewModel viewModel);

    void inject(LwsViewModel viewModel);
}
