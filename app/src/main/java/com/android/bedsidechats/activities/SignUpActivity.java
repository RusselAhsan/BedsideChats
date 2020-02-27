package com.android.bedsidechats.activities;

import androidx.fragment.app.Fragment;

import com.android.bedsidechats.fragments.LoginFragment;
import com.android.bedsidechats.fragments.SignupFragment;

public class SignUpActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() { return new SignupFragment(); }
}
