package com.android.bedsidechats.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.ProviderFragment;

public class ProviderActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Bundle args = new Bundle();
//        String language = "English";
//        String email = "testing@test.com";
//        String username = "test";
        args.putString("Language", getIntent().getStringExtra("Language"));
        args.putString("Email", getIntent().getStringExtra("Email"));
        args.putString("Username", getIntent().getStringExtra("Username"));
        ProviderFragment providerFragment = new ProviderFragment();
        providerFragment.setArguments(args);
        return providerFragment; }
}
