package com.r384ta.android.streamdemo.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.annimon.stream.Optional;
import com.r384ta.android.streamdemo.App;
import com.r384ta.android.streamdemo.di.component.ActivityComponent;
import com.r384ta.android.streamdemo.di.component.AppComponent;
import com.r384ta.android.streamdemo.di.module.ActivityModule;


public abstract class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;

    public ActivityComponent getActivityComponent() {
        return Optional.ofNullable(mActivityComponent)
            .orElseGet(() -> {
                AppComponent appComponent = ((App) getApplication()).getAppComponent();
                mActivityComponent = appComponent.plus(new ActivityModule(this));
                return mActivityComponent;
            });
    }

    public Optional<ActionBar> getOptionalActionBar() {
        return Optional.ofNullable(getSupportActionBar());
    }
}
