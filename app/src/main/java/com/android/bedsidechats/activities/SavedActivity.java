package com.android.bedsidechats.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.HomeFragment;
import com.android.bedsidechats.fragments.SavedFragment;

public class SavedActivity extends BaseFragmentActivity {
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
        SavedFragment savedFragment = new SavedFragment();
        savedFragment.setArguments(args);
        return savedFragment;
    }
}
