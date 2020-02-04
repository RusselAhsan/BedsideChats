package com.android.bedsidechats;

import androidx.test.rule.ActivityTestRule;

import com.android.bedsidechats.activities.MainActivity;
import com.android.bedsidechats.fragments.LanguageFragment;

import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest extends ActivityTestRule<MainActivity> {
    private MainActivity mMainActivity;
    private LanguageFragment mLanguageFragment;

    public MainActivityTest() {
        super(MainActivity.class);

        launchActivity(getActivityIntent());
        mMainActivity = getActivity();
        mLanguageFragment = (LanguageFragment) mMainActivity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();
    }


    @Test
    public void testActivityExists() {
        assertNotNull(mMainActivity);
    }

    @Test
    public void testFragmentExists() {
        assertNotNull(mLanguageFragment);
    }

    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mMainActivity.finish();
        }
    }
}