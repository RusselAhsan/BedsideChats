package com.android.bedsidechats.UnitTests;
import android.content.Intent;
import android.os.SystemClock;

import org.junit.Test;
import androidx.test.rule.ActivityTestRule;

import com.android.bedsidechats.R;
import com.android.bedsidechats.activities.LoginActivity;
import com.android.bedsidechats.fragments.LoginFragment;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LoginUnitTest extends ActivityTestRule<LoginActivity> {
    private LoginActivity mLoginActivity;
    private LoginFragment mLoginFragment;

    public LoginUnitTest() {
        super(LoginActivity.class);

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
        mLoginActivity = getActivity();
        mLoginFragment = (LoginFragment) mLoginActivity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
    }


    @Test
    public void testActivityExists() {
        assertNotNull(mLoginActivity);
    }

    @Test
    public void testFragmentExists(){
        assertNotNull(mLoginFragment);
    }

    @Test
    public void testLoginLanguageArgument() {
        assertTrue(mLoginFragment.getArguments().getString("Language").equals("English"));
    }

    @Test
    public void testLoginProviderArgument() {
        assertTrue(mLoginFragment.getArguments().getString("Provider").equals("physician"));
    }



    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mLoginActivity.finish();
        }
    }
}