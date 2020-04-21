package com.android.bedsidechats;

import android.content.Intent;
import android.os.SystemClock;

import androidx.test.rule.ActivityTestRule;

import com.android.bedsidechats.activities.HomeActivity;
import com.android.bedsidechats.fragments.HomeFragment;

import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HomeUnitTest extends ActivityTestRule<HomeActivity> {
    private HomeActivity mHomeActivity;
    private HomeFragment mHomeFragment;

    public HomeUnitTest() {
        super(HomeActivity.class);

        Intent intent = new Intent();
        intent.putExtra("Language", "English");
        intent.putExtra("Provider", "physician");
        intent.putExtra("Category", "provider");
        intent.putExtra("Email", "testing@test.com");
        intent.putExtra("Username", "test");
        launchActivity(intent);

        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();
        SystemClock.sleep(500);
        mHomeActivity = getActivity();
        if(mHomeActivity != null) {
            mHomeFragment = (HomeFragment) mHomeActivity.getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);
        }

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
    public void testHomeLanguageArgumentEnglish() {
        assertTrue(mHomeFragment.getArguments().getString("Language").equals("English"));
    }

    @Test
    public void testHomeProviderArgumentPhysician() {
        assertTrue(mHomeFragment.getArguments().getString("Provider").equals("physician"));
    }

    @Test
    public void testHomeUsernameArgument() {
        assertTrue(mHomeFragment.getArguments().getString("Username").equals("test"));
    }

    @Test
    public void testHomeEmailArgument() {
        assertTrue(mHomeFragment.getArguments().getString("Email").equals("testing@test.com"));
    }

    @Test
    public void testHomeCategoryArgumentProvider() {
        assertTrue(mHomeFragment.getArguments().getString("Category").equals("provider"));
    }


    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mHomeActivity.finish();
        }
    }
}