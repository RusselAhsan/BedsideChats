package com.android.bedsidechats.UI;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.bedsidechats.R;
import com.android.bedsidechats.activities.LoginActivity;

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
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginViewTestTitle() {

        ViewInteraction textView = onView(
                allOf(withId(R.id.title_textView_login_port), withText("Bedside Chats"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Bedside Chats")));
    }

    @Test
    public void loginViewTestEmailTextView() {
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.email_textView_login_port), withText("Email:")));
        textView2.check(matches(withText("Email:")));
    }

    @Test
    public void loginViewTestEmailEditText() {
        ViewInteraction editText = onView(
                allOf(withId(R.id.email_editText_login_port)));
        editText.check(matches(withText("")));
    }

    @Test
    public void loginViewTestPasswordTextView() {
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.password_textView_login_port), withText("Password:")));
        textView3.check(matches(withText("Password:")));
    }

    @Test
    public void loginViewTestPasswordEditText() {
        ViewInteraction editText2 = onView(
                allOf(withId(R.id.password_editText_login_port)));
        editText2.check(matches(withText("")));
    }

    @Test
    public void loginViewTestLoginButton() {
        ViewInteraction button = onView(
                allOf(withId(R.id.login_button_login_port)));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void loginViewTestForgotPasswordButton() {
        ViewInteraction button2 = onView(
                allOf(withId(R.id.forgotPassword_button_login_port)));
        button2.check(matches(isDisplayed()));
    }

    @Test
    public void loginViewTestSignupButton() {
        ViewInteraction button3 = onView(
                allOf(withId(R.id.signup_button_login_port)));
        button3.check(matches(isDisplayed()));
    }

    @Test
    public void loginButtonLoginTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.login_button_login_port), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                5),
                        isDisplayed()));
        button.perform(click());
    }

    @Test
    public void loginButtonForgotPasswordTest() {
        ViewInteraction button2 = onView(
                allOf(withId(R.id.forgotPassword_button_login_port), withText("Forgot Password?"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                6),
                        isDisplayed()));
        button2.perform(click());
    }

    @Test
    public void loginButtonSignUpTest() {
        ViewInteraction button3 = onView(
                allOf(withId(R.id.signup_button_login_port), withText("New user? Sign up"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                7),
                        isDisplayed()));
        button3.perform(click());
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
