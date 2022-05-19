package com.example.haystreethealthyhub;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void loginUIElementTest()
    {
        // App Logo
        onView(withId(R.id.App_logo)).check(matches(isDisplayed()));
        // Login email input
        onView(withId(R.id.LoginEmailInput)).check(matches(isDisplayed()));
        // Login password input
        onView(withId(R.id.LoginPasswordInput)).check(matches(isDisplayed()));
        // Doctor switch
        onView(withId(R.id.LoginDoctorSwitch)).check(matches(isDisplayed()));
        // Login button
        onView(withId(R.id.ButtonLogin)).check(matches(isDisplayed()));
        // Registration button
        onView(withId(R.id.ButtonRegistration)).check(matches(isDisplayed()));
        // Contact us button
        onView(withId(R.id.ButtonContactUs)).check(matches(isDisplayed()));
    }

    @Test
    public void patientLoginValidTest()
    {
        // Input email
        onView(withId(R.id.LoginEmailInput))
                .perform(typeText("jack@jack.com"));
        closeSoftKeyboard();

        // Input password
        onView(withId(R.id.LoginPasswordInput))
                .perform(typeText("jack"));
        closeSoftKeyboard();

        // Click button
        onView(withId(R.id.ButtonLogin))
                .perform(click());

        // Check if turn to home page
        onView(withId(R.id.PatientHomeCustomTitle)).check(matches(isDisplayed()));


    }

    @Test
    public void patientLoginInvalidTest()
    {
        // Input email
        onView(withId(R.id.LoginEmailInput))
                .perform(typeText("jack@jack.com"));
        closeSoftKeyboard();

        // Input password (Incorrect password)
        onView(withId(R.id.LoginPasswordInput))
                .perform(typeText("jackss"));
        closeSoftKeyboard();

        // Click button
        onView(withId(R.id.ButtonLogin))
                .perform(click());

        // Input wrong password can not turn to home page
        // Check if still on the login page
        onView(withId(R.id.ButtonLogin)).check(matches(isDisplayed()));


    }



    @After
    public void tearDown() throws Exception {
        activityScenarioRule = null;
    }


}