package com.r384ta.android.streamdemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.r384ta.android.streamdemo.R;
import com.r384ta.android.streamdemo.databinding.ActivityMainBinding;
import com.r384ta.android.streamdemo.di.module.ProvideNames;
import com.r384ta.android.streamdemo.fragment.AboutFragment;
import com.r384ta.android.streamdemo.fragment.LwsFragment;
import com.r384ta.android.streamdemo.fragment.RxJavaFragment;
import com.r384ta.android.streamdemo.util.FragmentUtils;

import javax.inject.Inject;
import javax.inject.Named;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    @Named(ProvideNames.PARENT_FRAGMENT_UTILS)
    FragmentUtils mFragmentUtils;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initializeToolbar();
        initializeFragment();
        initializeBottomNavigation();
    }

    private void initializeToolbar() {
        setSupportActionBar(mBinding.mainToolbar);
        getOptionalActionBar().ifPresent(actionBar -> actionBar.setTitle(R.string.app_name));
    }

    private void initializeFragment() {
        RxJavaFragment rxJavaFragment = mFragmentUtils.getFragment(RxJavaFragment.class)
            .orElseGet(RxJavaFragment::newInstance);
        mFragmentUtils.replaceFragment(rxJavaFragment, R.id.main_frame_layout);
    }

    private void initializeBottomNavigation() {
        mBinding.mainBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.bni_rxjava:
                    fragment = mFragmentUtils.getFragment(RxJavaFragment.class)
                        .orElseGet(RxJavaFragment::newInstance);
                    break;
                case R.id.bni_lws:
                    fragment = mFragmentUtils.getFragment(LwsFragment.class)
                        .orElseGet(LwsFragment::newInstance);
                    break;
                case R.id.bni_about:
                    fragment = mFragmentUtils.getFragment(AboutFragment.class)
                        .orElseGet(AboutFragment::newInstance);
                    break;
                default:
                    throw new IllegalStateException("Invalid item id");
            }
            mFragmentUtils.replaceFragment(fragment, R.id.main_frame_layout);
            return true;
        });
    }
}
