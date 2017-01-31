package com.r384ta.android.streamdemo.fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r384ta.android.streamdemo.R;
import com.r384ta.android.streamdemo.databinding.FragmentRxJavaBinding;
import com.r384ta.android.streamdemo.fragment.handler.RxJavaHandler;
import com.r384ta.android.streamdemo.misc.AndroidDisposable;
import com.r384ta.android.streamdemo.viewmodel.RxJavaViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RxJavaFragment extends BaseFragment implements RxJavaHandler {
    private static final String TAG = RxJavaFragment.class.getSimpleName();

    @Inject
    AndroidDisposable mDisposable;

    private RxJavaViewModel mViewModel;
    private FragmentRxJavaBinding mBinding;

    public RxJavaFragment() {
    }

    public static RxJavaFragment newInstance() {
        RxJavaFragment fragment = new RxJavaFragment();
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getFragmentComponent().inject(this);
        mViewModel = new RxJavaViewModel(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rx_java, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.bind(view);
        mBinding.setRxJavaHandler(this);

        mDisposable.add(mViewModel.httpBinText()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(result -> {
                mBinding.rxJavaWithRetrofitResult.setVisibility(View.VISIBLE);
                mBinding.rxJavaWithRetrofitResult.setText(result);
            }, Throwable::printStackTrace));
    }

    @Override
    public void onDetach() {
        mDisposable.dispose();
        mViewModel.dispose();
        super.onDetach();
    }

    @Override
    public void onRetrofitClick(View view) {
        mViewModel.httpBinTextRequest();
    }
}
