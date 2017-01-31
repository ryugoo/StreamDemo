package com.r384ta.android.streamdemo.di.component;

import com.r384ta.android.streamdemo.activity.MainActivity;
import com.r384ta.android.streamdemo.di.module.ActivityModule;
import com.r384ta.android.streamdemo.di.module.FragmentModule;
import com.r384ta.android.streamdemo.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity activity);

    FragmentComponent plus(FragmentModule fragmentModule);
}
