package com.android.bedsidechats.UnitTests;
import android.content.Intent;
import android.os.SystemClock;

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
        intent.putExtra("Provider", "physician");
        intent.putExtra("Category", "provider");
        intent.putExtra("Email", "testing@test.com");
        intent.putExtra("Username", "test");
        launchActivity(intent);

        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();
        SystemClock.sleep(100);
        mCardsActivity = getActivity();
        mCardsFragment = (CardsFragment) mCardsActivity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
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
    public void testProviderProviderArgumentPhysician() {
        assertTrue(mCardsFragment.getArguments().getString("Provider").equals("physician"));
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
        assertTrue(mCardsFragment.getProvider().equals("physician"));
    }

    @Test
    public void testCardsRecyclerViewNotNull() {
        assertNotNull(mCardsFragment.getRecyclerView());
    }

    @Test
    public void testCardDeckMapNotNull() {
        mCardsFragment.getCardDeck("English", "provider", "physician");
        assertNotNull(mCardsFragment.getCardDeckMap());
    }

    @Test
    public void testCardDeckMapSizeEnglishProviderPhysician() {
        mCardsFragment.getCardDeck("English", "provider", "physician");
        assertTrue(mCardsFragment.getCardDeckSize() > 0);
    }

    protected void afterActivityFinished() {
        super.afterActivityFinished();
        if (!getActivity().isFinishing()) {
            mCardsActivity.finish();
        }
    }
}
