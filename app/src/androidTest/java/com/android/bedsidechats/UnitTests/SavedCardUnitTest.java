package com.android.bedsidechats.UnitTests;
import android.content.Intent;
import android.os.SystemClock;

import org.junit.Test;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.rule.ActivityTestRule;

import com.android.bedsidechats.R;
import com.android.bedsidechats.activities.SavedActivity;
import com.android.bedsidechats.fragments.SavedFragment;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SavedCardUnitTest extends ActivityTestRule<SavedActivity> {
    private SavedActivity mSavedActivity;
    private SavedFragment mSavedFragment;

    public SavedCardUnitTest() {
        super(SavedActivity.class);

        Intent intent = new Intent();
        intent.putExtra("Language", "English");
        intent.putExtra("Provider", "physician");
        intent.putExtra("Category", "provider");
        intent.putExtra("Email", "testing@test.com");
        intent.putExtra("Username", "test");
        launchActivity(intent);

        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();
        SystemClock.sleep(100);
        mSavedActivity = getActivity();
        if(mSavedActivity != null) {
            mSavedFragment = (SavedFragment) mSavedActivity.getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);
        }

    }


    @Test
    public void testActivityExists() {
        assertNotNull(mSavedActivity);
    }

    @Test
    public void testFragmentExists() {
        assertNotNull(mSavedFragment);
    }

    @Test
    public void testSavedLanguageArgument() {
        assertTrue(mSavedFragment.getArguments().getString("Language").equals("English"));
    }

    @Test
    public void testProviderProviderArgumentPhysician() {
        assertTrue(mSavedFragment.getArguments().getString("Provider").equals("physician"));
    }

    @Test
    public void testSavedUsernameArgument() {
        assertTrue(mSavedFragment.getArguments().getString("Username").equals("test"));
    }

    @Test
    public void testSaveEmailArgument() {
        assertTrue(mSavedFragment.getArguments().getString("Email").equals("testing@test.com"));
    }



    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mSavedActivity.finish();
        }
    }
}
