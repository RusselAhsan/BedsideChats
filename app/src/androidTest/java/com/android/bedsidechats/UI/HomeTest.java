package com.android.bedsidechats.UI;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.bedsidechats.R;
import com.android.bedsidechats.activities.HomeActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule(HomeActivity.class, true, true){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, HomeActivity.class);
            result.putExtra("Language", "English");
            result.putExtra("Provider", "physician");
            result.putExtra("Category", "provider");
            result.putExtra("Email", "testing@test.com");
            result.putExtra("Username", "test");
            return result;
        }
    };

    @Test
    public void HomeTestTitle() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.title_textView_home_port), withText("Bedside Chats")));
        textView.check(matches(isDisplayed()));
    }

    @Test
    public void HomeTestSelectTextView() {
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.select_textView_home_port), withText("Select an option")));
        textView2.check(matches(isDisplayed()));
    }

    @Test
    public void HomeTestCardsButton() {
        ViewInteraction button = onView(
                allOf(withId(R.id.cards_button_home_port)));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void HomeTestSavedButton() {
        ViewInteraction button2 = onView(
                allOf(withId(R.id.saved_button_home_port)));
        button2.check(matches(isDisplayed()));
    }

    @Test
    public void HomeTestProvidersButton() {
        ViewInteraction button3 = onView(
                allOf(withId(R.id.provider_button_home_port)));
        button3.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
