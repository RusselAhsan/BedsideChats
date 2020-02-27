package com.android.bedsidechats.activities;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.LanguageFragment;
import com.android.bedsidechats.fragments.LoginFragment;

public class LoginActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() { return new LoginFragment(); }
}
