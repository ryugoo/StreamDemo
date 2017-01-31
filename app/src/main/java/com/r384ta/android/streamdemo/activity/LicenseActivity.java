package com.r384ta.android.streamdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.r384ta.android.streamdemo.R;
import com.r384ta.android.streamdemo.databinding.ActivityLicenseBinding;

public class LicenseActivity extends BaseActivity {
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, LicenseActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLicenseBinding binding =
            DataBindingUtil.setContentView(this, R.layout.activity_license);

        setSupportActionBar(binding.licenseToolbar);
        getOptionalActionBar().ifPresent(actionBar -> {
            actionBar.setTitle(R.string.license);
            actionBar.setDisplayHomeAsUpEnabled(true);
        });

        binding.licenseWebView.loadUrl("file:///android_asset/licenses.html");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
