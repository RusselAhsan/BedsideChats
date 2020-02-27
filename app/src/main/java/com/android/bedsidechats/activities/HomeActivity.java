package com.android.bedsidechats.activities;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.HomeFragment;

public class HomeActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() { return new HomeFragment();}
}
