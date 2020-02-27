package com.android.bedsidechats;
import org.junit.Test;
import androidx.test.rule.ActivityTestRule;
import com.android.bedsidechats.activities.LoginActivity;
import com.android.bedsidechats.fragments.LoginFragment;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class LoginUnitTest extends ActivityTestRule<LoginActivity> {
    private LoginActivity mLoginActivity;
    private LoginFragment mLoginFragment;

    public LoginUnitTest() {
        super(LoginActivity.class);

        launchActivity(getActivityIntent());
        mLoginActivity = getActivity();
        mLoginFragment = (LoginFragment) mLoginActivity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();
    }


    @Test
    public void testActivityExists() {
        assertNotNull(mLoginActivity);
        assertNotNull(mLoginFragment);
    }

    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mLoginActivity.finish();
        }
    }
}