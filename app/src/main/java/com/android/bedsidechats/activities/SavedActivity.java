package com.android.bedsidechats.activities;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.HomeFragment;
import com.android.bedsidechats.fragments.SavedFragment;

public class SavedActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() { return new SavedFragment();
    }
}
