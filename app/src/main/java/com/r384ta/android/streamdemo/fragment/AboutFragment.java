package com.r384ta.android.streamdemo.fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;
import com.r384ta.android.streamdemo.R;
import com.r384ta.android.streamdemo.activity.LicenseActivity;
import com.r384ta.android.streamdemo.databinding.FragmentAboutBinding;
import com.r384ta.android.streamdemo.di.module.ProvideNames;
import com.r384ta.android.streamdemo.fragment.handler.AboutHandler;
import com.r384ta.android.streamdemo.util.FragmentUtils;

import javax.inject.Inject;
import javax.inject.Named;

public class AboutFragment extends BaseFragment implements AboutHandler {
    private static final String TAG = AboutFragment.class.getSimpleName();

    @Inject
    @Named(ProvideNames.CHILD_FRAGMENT_UTILS)
    FragmentUtils mFragmentUtils;

    @Inject
    @Named(ProvideNames.APPLICATION_CONTEXT)
    Context mContext;

    public AboutFragment() {
    }

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle args = Optional.ofNullable(fragment.getArguments()).orElseGet(Bundle::new);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getFragmentComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FragmentAboutBinding binding = DataBindingUtil.bind(view);
        binding.setAboutHandler(this);
    }

    @Override
    public void onShowLicenses(View view) {
        startActivity(LicenseActivity.createIntent(mContext));
    }
}
