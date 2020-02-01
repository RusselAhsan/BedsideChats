package com.android.bedsidechats.activities;
import androidx.fragment.app.Fragment;
import com.android.bedsidechats.fragments.LanguageFragment;

public class MainActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() { return new LanguageFragment(); }
}
