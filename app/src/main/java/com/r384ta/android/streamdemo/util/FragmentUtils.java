package com.r384ta.android.streamdemo.util;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.annimon.stream.Objects;
import com.annimon.stream.Optional;

public class FragmentUtils {
    private static final String TAG = FragmentUtils.class.getSimpleName();

    private FragmentManager mFragmentManager;

    public FragmentUtils(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void replaceFragment(@NonNull Fragment fragment,
                                @NonNull String tag,
                                @IdRes int targetId) {
        Objects.requireNonNull(fragment, "fragment == null");
        Objects.requireNonNull(tag, "tag == null");

        if (fragment.isAdded()) {
            return;
        }

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        getFragment(targetId).ifPresent(fragmentTransaction::detach);

        if (fragment.isDetached()) {
            fragmentTransaction.attach(fragment);
        } else {
            fragmentTransaction.add(targetId, fragment, tag);
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commitNow();
    }

    public void replaceFragment(@NonNull Fragment fragment,
                                @IdRes int targetId) {
        replaceFragment(fragment, fragment.getClass().getSimpleName(), targetId);
    }

    public <T extends Fragment> void replaceFragment(@NonNull Class<T> clazz,
                                                     @IdRes int targetId,
                                                     @NonNull String tag) {
        getFragment(clazz).ifPresent(fragment -> replaceFragment(fragment, tag, targetId));
    }

    public <T extends Fragment> void replaceFragment(@NonNull Class<T> clazz,
                                                     @IdRes int targetId) {
        replaceFragment(clazz, targetId, clazz.getSimpleName());
    }

    public <T extends Fragment> Optional<T> getFragment(@NonNull Class<T> clazz,
                                                        @NonNull String tag) {
        Objects.requireNonNull(clazz, "clazz == null");
        Objects.requireNonNull(tag, "tag == null");

        return getFragment(tag).select(clazz);
    }

    public <T extends Fragment> Optional<T> getFragment(@NonNull Class<T> clazz) {
        return getFragment(clazz, clazz.getSimpleName());
    }

    public Optional<Fragment> getFragment(@NonNull String tag) {
        return Optional.ofNullable(mFragmentManager.findFragmentByTag(tag));
    }

    public Optional<Fragment> getFragment(@IdRes int targetId) {
        return Optional.ofNullable(mFragmentManager.findFragmentById(targetId));
    }
}
