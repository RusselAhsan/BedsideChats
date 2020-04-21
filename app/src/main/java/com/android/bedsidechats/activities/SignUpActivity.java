package com.android.bedsidechats.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.HomeFragment;
import com.android.bedsidechats.fragments.SignupFragment;

public class SignUpActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Bundle args = new Bundle();
//        String language = "English";
//        String provider = "Physician";
//        String email = "testing@test.com";
//        String username = "test";
        args.putString("Language", getIntent().getStringExtra("Language"));
        args.putString("Provider", getIntent().getStringExtra("Provider"));
        args.putString("Category", getIntent().getStringExtra("Category"));
        args.putString("Email", getIntent().getStringExtra("Email"));
        args.putString("Username", getIntent().getStringExtra("Username"));
        SignupFragment signupFragment = new SignupFragment();
        signupFragment.setArguments(args);
        return signupFragment; }
}
