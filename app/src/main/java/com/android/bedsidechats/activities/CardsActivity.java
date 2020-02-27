package com.android.bedsidechats.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.CardsFragment;

public class CardsActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Bundle bundle = new Bundle();
        String language = getIntent().getStringExtra("Language");
        String provider = getIntent().getStringExtra("Provider");
        bundle.putString("Language", language);
        bundle.putString("Provider", provider);
        CardsFragment cardsFragment = new CardsFragment();
        cardsFragment.setArguments(bundle);
        return cardsFragment;
    }
}
