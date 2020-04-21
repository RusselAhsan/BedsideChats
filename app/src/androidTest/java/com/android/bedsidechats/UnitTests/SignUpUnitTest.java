package com.android.bedsidechats.UnitTests;

import android.content.Intent;

import androidx.test.rule.ActivityTestRule;

import com.android.bedsidechats.R;
import com.android.bedsidechats.activities.LoginActivity;
import com.android.bedsidechats.activities.SignUpActivity;
import com.android.bedsidechats.fragments.LoginFragment;
import com.android.bedsidechats.fragments.SignupFragment;

import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SignUpUnitTest extends ActivityTestRule<SignUpActivity> {
    private SignUpActivity mSignUpActivity;
    private SignupFragment mSignUpFragment;

    public SignUpUnitTest() {
        super(SignUpActivity.class);

        Intent intent = new Intent();
        intent.putExtra("Language", "English");
        intent.putExtra("Provider", "Physician");
        launchActivity(intent);
        mSignUpActivity = getActivity();
        mSignUpFragment = (SignupFragment) mSignUpActivity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();
    }


    @Test
    public void testActivityExists() {
        assertNotNull(mSignUpActivity);
    }

    @Test
    public void testFragmentExists(){
        assertNotNull(mSignUpFragment);
    }

    @Test
    public void testSignUpLanguageArgument() {
        assertTrue(mSignUpFragment.getArguments().getString("Language").equals("English"));
    }

    @Test
    public void testSignUpProviderArgument() {
        assertTrue(mSignUpFragment.getArguments().getString("Provider").equals("Physician"));
    }



    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mSignUpActivity.finish();
        }
    }
}