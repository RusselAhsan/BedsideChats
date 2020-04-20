package com.android.bedsidechats.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.HomeFragment;

public class HomeActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Bundle args = new Bundle();
        String language = "English";
        String provider = "Physician";
        String email = "testing@test.com";
        String username = "test";
        args.putString("Language", language);
        args.putString("Provider", provider);
        args.putString("Email", email);
        args.putString("Username", username);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        return homeFragment;
    }
}
