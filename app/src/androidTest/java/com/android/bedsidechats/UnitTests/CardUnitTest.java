package com.android.bedsidechats.UnitTests;
import android.content.Intent;

import org.junit.Test;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.rule.ActivityTestRule;

import com.android.bedsidechats.R;
import com.android.bedsidechats.activities.CardsActivity;
import com.android.bedsidechats.fragments.CardsFragment;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CardUnitTest extends ActivityTestRule<CardsActivity> {
    private CardsActivity mCardsActivity;
    private CardsFragment mCardsFragment;

    public CardUnitTest() {
        super(CardsActivity.class);

        Intent intent = new Intent();
        intent.putExtra("Language", "English");
        intent.putExtra("Provider", "Physician");
        launchActivity(intent);
        mCardsActivity = getActivity();
        mCardsFragment = (CardsFragment) mCardsActivity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();
    }


    @Test
    public void testActivityExists() {
        assertNotNull(mCardsActivity);
    }

    @Test
    public void testFragmentExists() {
        assertNotNull(mCardsFragment);
    }

    @Test
    public void testCardsLanguageArgument() {
        assertTrue(mCardsFragment.getArguments().getString("Language").equals("English"));
    }

    @Test
    public void testCardsProviderArgument() {
        assertTrue(mCardsFragment.getArguments().getString("Provider").equals("Physician"));
    }

    @Test
    public void testCardsUsernameArgument() {
        assertTrue(mCardsFragment.getArguments().getString("Username").equals("test"));
    }

    @Test
    public void testCardsEmailArgument() {
        assertTrue(mCardsFragment.getArguments().getString("Email").equals("testing@test.com"));
    }

    @Test
    public void testCardsLanguageVariableEnglish() {
        assertTrue(mCardsFragment.getLanguage().equals("English"));
    }

    @Test
    public void testCardsProviderVariablePhysician() {
        assertTrue(mCardsFragment.getProvider().equals("Physician"));
    }

    @Test
    public void testCardsRecyclerViewNotNull() {
        assertNotNull(mCardsFragment.getRecyclerView());
    }

//    @Test
//    public void testCardDeckMapNotNull() {
//        assertNotNull(mCardsFragment.getCardDeckMap());
//    }

//    @Test
//    public void testCardDeckMapSize() {
//        assertTrue(mCardsFragment.getCardDeckSize() > 0);
//    }

    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mCardsActivity.finish();
        }
    }
}
