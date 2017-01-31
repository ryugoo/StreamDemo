package com.r384ta.android.streamdemo.viewmodel;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.annimon.stream.Optional;
import com.r384ta.android.streamdemo.App;
import com.r384ta.android.streamdemo.di.component.ViewModelComponent;
import com.r384ta.android.streamdemo.di.module.ViewModelModule;

public abstract class BaseViewModel {
    private Activity mActivity;

    private ViewModelComponent mViewModelComponent;

    public BaseViewModel(Activity activity) {
        mActivity = activity;
    }

    public BaseViewModel(Fragment fragment) {
        mActivity = fragment.getActivity();
    }

    public ViewModelComponent getViewModelComponent() {
        if (mViewModelComponent != null) {
            return mViewModelComponent;
        }

        mViewModelComponent = Optional.ofNullable(mActivity)
            .map(Activity::getApplication)
            .select(App.class)
            .map(App::getAppComponent)
            .map(appComponent -> appComponent.plus(new ViewModelModule()))
            .orElseThrow(() -> new IllegalStateException("This view model require a BaseActivity"));

        return mViewModelComponent;
    }

    public void dispose() {
        mActivity = null;
    }
}
