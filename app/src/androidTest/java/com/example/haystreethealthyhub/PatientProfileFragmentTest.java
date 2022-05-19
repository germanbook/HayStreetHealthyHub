package com.example.haystreethealthyhub;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class PatientProfileFragmentTest {

    static Intent intent;
    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), PatientHomeMenuActivity.class);
        intent.putExtra("ID", 2);
        intent.putExtra("Name", "Bowen");
        intent.putExtra("UserType", "P");
    }

    @Rule
    public ActivityScenarioRule<PatientHomeMenuActivity> activityScenarioRule
            = new ActivityScenarioRule<>(intent);


    @Before
    public void init(){
        // Open drawer layout
        onView(withId(R.id.PatientHomeDrawerLayout)).perform(DrawerActions.open());
        // Click on Profile
        onView(withId(R.id.Nav_menu_profile))
                .perform(click());
    }

    @Test
    public void patientProfileElementTest() throws InterruptedException
    {

        onView(withId(R.id.Registration_FirstName)).check(matches(isDisplayed()));

        onView(withId(R.id.Registration_LastName)).check(matches(isDisplayed()));

        onView(withId(R.id.Registration_Email)).check(matches(isDisplayed()));

        onView(withId(R.id.Registration_Password)).check(matches(isDisplayed()));

        onView(withId(R.id.PersonalInfo_Gender)).check(matches(isDisplayed()));

        onView(withId(R.id.PersonalInfo_Dob)).check(matches(isDisplayed()));

        onView(withId(R.id.PersonalInfo_Height)).check(matches(isDisplayed()));

        // Scroll page
        onView(withId(R.id.Registration_Contents)).perform(ViewActions.swipeUp());

        onView(withId(R.id.PersonalInfo_Weight)).check(matches(isDisplayed()));

        onView(withId(R.id.PersonalInfo_GP)).check(matches(isDisplayed()));

        Thread.sleep(2000);

        onView(withId(R.id.profile_edit_icon)).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.option_edit)).check(matches(isDisplayed()));

        onView(withId(R.id.option_save)).check(matches(isDisplayed()));
    }

    @Test
    public void patientProfileEditElementTest() throws InterruptedException
    {


        onView(withId(R.id.profile_edit_icon)).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.option_save)).check(matches(isDisplayed()));

        onView(withId(R.id.option_edit)).check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);
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

        Thread.sleep(2000);

    }

    @Test
    public void patientProfileUpdateValidTest() throws InterruptedException {
        onView(withId(R.id.profile_edit_icon)).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.option_edit)).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.Registration_FirstName))
                .perform(typeText("a"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_LastName))
                .perform(typeText("a"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_Password))
                .perform(replaceText("password"));
        closeSoftKeyboard();


        onView(withId(R.id.Registration_ConfirmPassword))
                .perform(typeText("password"));
        closeSoftKeyboard();

        onView(withId(R.id.PersonalInfo_Gender))
                .perform(click());
        onData(hasToString(startsWith("F (female)")))
                .perform(click());

        onView(withId(R.id.PersonalInfo_Height))
                .perform(typeText("5"));
        closeSoftKeyboard();

        onView(withId(R.id.PersonalInfo_Weight))
                .perform(typeText("5"));
        closeSoftKeyboard();

        onView(withId(R.id.PersonalInfo_GP))
                .perform(click());
        onData(hasToString(startsWith("Tom")))
                .perform(click());

        Thread.sleep(1000);

        // Save
        onView(withId(R.id.profile_edit_icon)).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.option_save)).check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);
        // Check AlertDialog
        onView(withText("Ok")).check(matches(isDisplayed()));

    }

    @Test
    public void patientProfileUpdateInvalidTest() throws InterruptedException {
        onView(withId(R.id.profile_edit_icon)).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.option_edit)).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.Registration_FirstName))
                .perform(typeText("34"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_LastName))
                .perform(typeText("34"));
        closeSoftKeyboard();

        onView(withId(R.id.Registration_Password))
                .perform(replaceText("qwer"));
        closeSoftKeyboard();


        onView(withId(R.id.Registration_ConfirmPassword))
                .perform(typeText("1234"));
        closeSoftKeyboard();

        onView(withId(R.id.PersonalInfo_Gender))
                .perform(click());
        onData(hasToString(startsWith("F (female)")))
                .perform(click());

        onView(withId(R.id.PersonalInfo_Height))
                .perform(typeText(".5"));
        closeSoftKeyboard();

        onView(withId(R.id.PersonalInfo_Weight))
                .perform(typeText(".5"));
        closeSoftKeyboard();

        onView(withId(R.id.PersonalInfo_GP))
                .perform(click());
        onData(hasToString(startsWith("Tom")))
                .perform(click());

        Thread.sleep(1000);

        // Save
        onView(withId(R.id.profile_edit_icon)).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.option_save)).check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);
        // Check AlertDialog
        onView(withText("Back")).check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {
        activityScenarioRule = null;
    }
}