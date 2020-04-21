package com.android.bedsidechats.UnitTests;

import androidx.test.rule.ActivityTestRule;

import com.android.bedsidechats.R;
import com.android.bedsidechats.activities.HomeActivity;
import com.android.bedsidechats.activities.MainActivity;
import com.android.bedsidechats.fragments.HomeFragment;
import com.android.bedsidechats.fragments.LanguageFragment;

import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HomeUnitTest extends ActivityTestRule<HomeActivity> {
    private HomeActivity mHomeActivity;
    private HomeFragment mHomeFragment;

    public HomeUnitTest() {
        super(HomeActivity.class);

        launchActivity(getActivityIntent());
        mHomeActivity = getActivity();
        mHomeFragment = (HomeFragment) mHomeActivity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();
    }


    @Test
    public void testActivityExists() {
        assertNotNull(mHomeActivity);
    }

    @Test
    public void testFragmentExists() {
        assertNotNull(mHomeFragment);
    }

    @Test
    public void testHomeUsernameArgument() {
        assertTrue(mHomeFragment.getArguments().getString("Username").equals("test"));
    }

    @Test
    public void testHomeEmailArgument() {
        assertTrue(mHomeFragment.getArguments().getString("Email").equals("testing@test.com"));
    }


    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mHomeActivity.finish();
        }
    }
}