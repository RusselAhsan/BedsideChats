package com.android.bedsidechats.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.LoginFragment;

public class LoginActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Bundle args = new Bundle();
//        String language = "English";
//        String provider = "physician";
//        String category = "provider";
//        String email = "testing@test.com";
//        String username = "test";
        args.putString("Language", getIntent().getStringExtra("Language"));
        args.putString("Provider", getIntent().getStringExtra("Provider"));
        args.putString("Category", getIntent().getStringExtra("Category"));
        args.putString("Email", getIntent().getStringExtra("Email"));
        args.putString("Username", getIntent().getStringExtra("Username"));
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setArguments(args);
        return loginFragment; }
}
