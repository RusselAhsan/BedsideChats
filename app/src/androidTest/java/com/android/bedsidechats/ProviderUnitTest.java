package com.android.bedsidechats;
import android.content.Intent;

import org.junit.Test;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.rule.ActivityTestRule;
import com.android.bedsidechats.activities.ProviderActivity;
import com.android.bedsidechats.fragments.ProviderFragment;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProviderUnitTest extends ActivityTestRule<ProviderActivity> {
    private ProviderActivity mProviderActivity;
    private ProviderFragment mProviderFragment;

    public ProviderUnitTest() {
        super(ProviderActivity.class);

        Intent intent = new Intent();
        intent.putExtra("Language", "Español");
        launchActivity(intent);
        mProviderActivity = getActivity();
        mProviderFragment = (ProviderFragment) mProviderActivity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();
    }


    @Test
    public void testActivityExists() {
        assertNotNull(mProviderActivity);
    }

    @Test
    public void testFragmentExists() {
        assertNotNull(mProviderFragment);
    }

//    @Test
//    public void testProviderLanguageEnglish() {
//        assertTrue(mProviderFragment.getArguments().getString("Language").equals("English"));
//    }

    @Test
    public void testProviderLanguageArgumentEspañol() {
        assertTrue(mProviderFragment.getArguments().getString("Language").equals("Español"));
    }

    @Test
    public void testProviderLanguageVariableEspañol() {
        assertTrue(mProviderFragment.getLanguage().equals("Español"));
    }

//    @Test
//    public void testProviderRecyclerViewNotNull() {
//        assertNotNull(mProviderFragment.getRecyclerView());
//    }
//
//    @Test
//    public void testProviderArrayListNotNull() {
//        assertNotNull(mProviderFragment.getProviderList());
//    }

//    @Test
//    public void testProviderArrayListSize() {
//        assertTrue(mProviderFragment.getProviderListSize() > 0);
//    }

    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mProviderActivity.finish();
        }
    }
}