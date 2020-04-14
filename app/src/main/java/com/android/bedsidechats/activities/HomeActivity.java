package com.android.bedsidechats.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.HomeFragment;

public class HomeActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Bundle bundle = new Bundle();
        String language = getIntent().getStringExtra("Language");
        String provider = getIntent().getStringExtra("Provider");
        String email = getIntent().getStringExtra("Email") != null ? getIntent().getStringExtra("Email") : "";
        String username = getIntent().getStringExtra("Username") != null ? getIntent().getStringExtra("Username") : "";
        bundle.putString("Language", language);
        bundle.putString("Provider", provider);
        bundle.putString("Email", email);
        bundle.putString("Username", username);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }
}
