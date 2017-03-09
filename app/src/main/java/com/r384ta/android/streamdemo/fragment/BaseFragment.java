package com.r384ta.android.streamdemo.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.annimon.stream.Optional;
import com.r384ta.android.streamdemo.activity.BaseActivity;
import com.r384ta.android.streamdemo.di.component.FragmentComponent;
import com.r384ta.android.streamdemo.di.module.FragmentModule;


public abstract class BaseFragment extends Fragment {
    private FragmentComponent mFragmentComponent;

    public FragmentComponent getFragmentComponent() {
        return Optional.ofNullable(mFragmentComponent)
            .orElseGet(() -> Optional.ofNullable(getActivity())
                .select(BaseActivity.class)
                .map(BaseActivity::getActivityComponent)
                .map(activityComponent -> activityComponent.plus(new FragmentModule(this)))
                .executeIfPresent(fragmentComponent -> mFragmentComponent = fragmentComponent)
                .orElseThrow(() ->
                    new IllegalStateException("This activity is not an instance of BaseActivity")));
    }

    public Optional<Activity> getOptionalActivity() {
        return Optional.ofNullable(getActivity());
    }
}
