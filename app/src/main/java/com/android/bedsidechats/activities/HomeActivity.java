package com.android.bedsidechats.activities;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.HomeFragment;
import com.android.bedsidechats.fragments.ProviderFragment;

public class HomeActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() { return new HomeFragment();}
}
