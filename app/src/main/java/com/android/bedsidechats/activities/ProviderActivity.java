package com.android.bedsidechats.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.ProviderFragment;

public class ProviderActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Bundle bundle = new Bundle();
        String language = getIntent().getStringExtra("Language");
        bundle.putString("Language", language);
        ProviderFragment providerFragment = new ProviderFragment();
        providerFragment.setArguments(bundle);
        return providerFragment; }
}
