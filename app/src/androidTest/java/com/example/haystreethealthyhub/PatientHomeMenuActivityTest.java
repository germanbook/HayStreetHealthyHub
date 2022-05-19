package com.example.haystreethealthyhub;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class PatientHomeMenuActivityTest {

    static Intent intent;
    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), PatientHomeMenuActivity.class);
        intent.putExtra("ID", 1);
        intent.putExtra("Name", "jack");
        intent.putExtra("UserType", "P");
    }

    @Rule
    public ActivityScenarioRule<PatientHomeMenuActivity> activityScenarioRule
            = new ActivityScenarioRule<>(intent);


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void patientHomeMenuElementTest() throws InterruptedException {

        // Open drawer layout
        onView(withId(R.id.PatientHomeDrawerLayout)).perform(DrawerActions.open());

        onView(withId(R.id.Main_Menu_Logo)).check(matches(isDisplayed()));

        onView(withId(R.id.Main_Menu_Username)).check(matches(isDisplayed()));

        onView(withId(R.id.Nav_menu_home)).check(matches(isDisplayed()));

        onView(withId(R.id.Nav_menu_profile)).check(matches(isDisplayed()));

        onView(withId(R.id.Nav_menu_appointment)).check(matches(isDisplayed()));

        onView(withId(R.id.Nav_menu_logout)).check(matches(isDisplayed()));

    }

    @Test
    public void patientHomeMenuItemProfileTest()
    {
        // Open drawer layout
        onView(withId(R.id.PatientHomeDrawerLayout)).perform(DrawerActions.open());

        // Click on Profile
        onView(withId(R.id.Nav_menu_profile))
                .perform(click());
        // Check if turned to profile page
        onView(withId(R.id.PatientHomeCustomTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void patientHomeMenuItemAppointmentTest()
    {
        // Open drawer layout
        onView(withId(R.id.PatientHomeDrawerLayout)).perform(DrawerActions.open());

        // Click on Profile
        onView(withId(R.id.Nav_menu_appointment))
                .perform(click());
        // Check if turned to appointment page
        onView(withId(R.id.appointment_listView)).check(matches(isDisplayed()));
    }

    @Test
    public void patientHomeMenuItemLogoutTest()
    {
        // Open drawer layout
        onView(withId(R.id.PatientHomeDrawerLayout)).perform(DrawerActions.open());

        // Click on Profile
        onView(withId(R.id.Nav_menu_logout))
                .perform(click());
        // Check AlertDialog
        onView(withText("Ok")).check(matches(isDisplayed()));
        onView(withText("Ok")).perform(click());

        // Check if back to main page
        onView(withId(R.id.LoginEmailInput)).check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
        activityScenarioRule = null;
    }
}