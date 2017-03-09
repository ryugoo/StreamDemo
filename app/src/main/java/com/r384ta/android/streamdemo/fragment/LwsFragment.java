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
import com.r384ta.android.streamdemo.databinding.FragmentLwsBinding;
import com.r384ta.android.streamdemo.fragment.handler.LwsHandler;
import com.r384ta.android.streamdemo.misc.AndroidDisposable;
import com.r384ta.android.streamdemo.viewmodel.LwsViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class LwsFragment extends BaseFragment implements LwsHandler {
    private static final String TAG = LwsFragment.class.getSimpleName();

    @Inject
    AndroidDisposable mDisposable;

    private LwsViewModel mViewModel;
    private FragmentLwsBinding mBinding;

    public LwsFragment() {
    }

    public static LwsFragment newInstance() {
        LwsFragment fragment = new LwsFragment();
        Bundle args = Optional.ofNullable(fragment.getArguments()).orElseGet(Bundle::new);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getFragmentComponent().inject(this);
        mViewModel = new LwsViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lws, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.bind(view);
        mBinding.setLwsHandler(this);

        mDisposable.add(mViewModel.collectionProcessing()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(error -> mBinding.lwsCollectionProcessing.setEnabled(true))
            .subscribe(result -> {
                mBinding.lwsCollectionProcessing.setEnabled(true);
                mBinding.lwsCollectionProcessingResult.setVisibility(View.VISIBLE);
                mBinding.lwsCollectionProcessingResult.setText(result);
            }, Throwable::printStackTrace));
    }

    @Override
    public void onDetach() {
        mDisposable.dispose();
        mViewModel.dispose();
        super.onDetach();
    }

    @Override
    public void onCollectionProcessingClick(View view) {
        mBinding.lwsCollectionProcessing.setEnabled(false);
        mViewModel.collectionProcessingRequest();
    }
}
