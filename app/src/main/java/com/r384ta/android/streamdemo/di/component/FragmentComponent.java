package com.r384ta.android.streamdemo.di.component;

import com.r384ta.android.streamdemo.di.module.FragmentModule;
import com.r384ta.android.streamdemo.di.scope.FragmentScope;
import com.r384ta.android.streamdemo.fragment.AboutFragment;
import com.r384ta.android.streamdemo.fragment.LwsFragment;
import com.r384ta.android.streamdemo.fragment.RxJavaFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    void inject(RxJavaFragment fragment);

    void inject(LwsFragment fragment);

    void inject(AboutFragment fragment);
}
