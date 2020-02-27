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
public class SignUpTest {

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityTestRule = new ActivityTestRule<>(SignUpActivity.class, true, true);

    @Test
    public void signUpViewTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.title_textView_signup_port), withText("Bedside Chats")));
        textView.check(matches(withText("Bedside Chats")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.email_textView_signup_port), withText("Email:")));
        textView2.check(matches(withText("Email:")));

        ViewInteraction editText = onView(
                allOf(withId(R.id.email_editText_signup_port)));
        editText.check(matches(withText("")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.username_editText_signup_port)));
        editText2.check(matches(withText("")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.username_textView_signup_port), withText("Username:")));
        textView3.check(matches(withText("Username:")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.password_textView_signup_port), withText("Password:")));
        textView4.check(matches(withText("Password:")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.password_editText_signup_port)));
        editText3.check(matches(withText("")));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.signup_button_signup_port)));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.login_button_signup_port)));
        button3.check(matches(isDisplayed()));
    }

    @Test
    public void signUpButtonSignUpTest() {
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.signup_button_signup_port), withText("Sign Up")));
        appCompatButton2.perform(click());
    }

    @Test
    public void signUpButtonLoginTest() {
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.login_button_signup_port), withText("Login")));
        appCompatButton3.perform(click());
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
