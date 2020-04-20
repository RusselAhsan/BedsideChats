package com.android.bedsidechats.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.CardsFragment;

import java.util.TreeMap;

public class CardsActivity extends BaseFragmentActivity {
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
        CardsFragment cardFragment = new CardsFragment();
        cardFragment.setArguments(args);
        return cardFragment;
    }

}
