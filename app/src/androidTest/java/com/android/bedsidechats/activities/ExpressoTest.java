package com.android.bedsidechats.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.bedsidechats.R;

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
public class ExpressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void expressoTest() {
        ViewInteraction button = onView(allOf(withId(R.id.english_button)));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(allOf(withId(R.id.spanish_button)));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(allOf(withId(R.id.login_button)));
        button3.check(matches(isDisplayed()));

        ViewInteraction appCompatButton = onView(allOf(withId(R.id.english_button), withText("English")));
        appCompatButton.perform(click());

        ViewInteraction button4 = onView(allOf(withId(R.id.default_button)));
        button4.check(matches(isDisplayed()));

        ViewInteraction button5 = onView(allOf(withId(R.id.nurse_button)));
        button5.check(matches(isDisplayed()));

        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.default_button), withText("Physician")));
        appCompatButton2.perform(click());

        ViewInteraction button6 = onView(allOf(withId(R.id.next_button)));
        button6.check(matches(isDisplayed()));
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
