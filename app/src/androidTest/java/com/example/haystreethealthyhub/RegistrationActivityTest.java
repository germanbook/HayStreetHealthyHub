package com.example.haystreethealthyhub;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;

import android.widget.DatePicker;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegistrationActivityTest {

    @Rule
    public ActivityScenarioRule<RegistrationActivity> activityScenarioRule = new ActivityScenarioRule<>(RegistrationActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void registrationElementTest() {

        // Buttons
        onView(withId(R.id.buttons)).check(matches(isDisplayed()));

        onView(withId(R.id.Registration_FirstName)).check(matches(isDisplayed()));

        onView(withId(R.id.Registration_LastName)).check(matches(isDisplayed()));

        onView(withId(R.id.Registration_Email)).check(matches(isDisplayed()));

        onView(withId(R.id.Registration_Password)).check(matches(isDisplayed()));

        onView(withId(R.id.Registration_ConfirmPassword)).check(matches(isDisplayed()));

        onView(withId(R.id.PersonalInfo_Gender)).check(matches(isDisplayed()));

        onView(withId(R.id.PersonalInfo_Dob)).check(matches(isDisplayed()));

        onView(withId(R.id.PersonalInfo_Height)).check(matches(isDisplayed()));

        // Scroll page
        onView(withId(R.id.Registration_Contents)).perform(ViewActions.swipeUp());

        onView(withId(R.id.PersonalInfo_Weight)).check(matches(isDisplayed()));

        onView(withId(R.id.PersonalInfo_GP)).check(matches(isDisplayed()));
    }

    @Test
    public void registrationValidTest() throws InterruptedException {

        onView(withId(R.id.Registration_FirstName))
                .perform(typeText("FName"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_LastName))
                .perform(typeText("LName"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_Email))
                .perform(typeText("email@ffemail1.com"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_Password))
                .perform(typeText("qwer1234"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_ConfirmPassword))
                .perform(typeText("qwer1234"));
        closeSoftKeyboard();

        onView(withId(R.id.PersonalInfo_Gender))
                .perform(click());
        onData(hasToString(startsWith("F (female)")))
                .perform(click());

        onView(withId(R.id.PersonalInfo_Dob))
                .perform(click());
        // DatePicker setdate
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2000, 10, 10));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.PersonalInfo_Height))
                .perform(typeText("182"));
        closeSoftKeyboard();

        onView(withId(R.id.PersonalInfo_Weight))
                .perform(typeText("82"));
        closeSoftKeyboard();

        onView(withId(R.id.PersonalInfo_GP))
                .perform(click());
        onData(hasToString(startsWith("Tom")))
                .perform(click());

        // Click button
        onView(withId(R.id.Registration_ButtonOk))
                .perform(click());

        Thread.sleep(3000);
        // Check AlertDialog
        onView(withText("Ok")).check(matches(isDisplayed()));
    }

    @Test
    public void registrationInvalidTest() throws InterruptedException {

        onView(withId(R.id.Registration_FirstName))
                .perform(typeText("1234"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_LastName))
                .perform(typeText("^22"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_Email))
                .perform(typeText("email"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_Password))
                .perform(typeText("qwer1234"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_ConfirmPassword))
                .perform(typeText("qwer"));
        closeSoftKeyboard();


        // Click button
        onView(withId(R.id.Registration_ButtonOk))
                .perform(click());

        Thread.sleep(3000);

        // Check toast message
        // Check if still on the login page
        onView(withId(R.id.Registration_ButtonOk)).check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
        activityScenarioRule = null;
    }
}