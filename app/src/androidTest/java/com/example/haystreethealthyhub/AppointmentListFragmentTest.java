package com.example.haystreethealthyhub;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.startsWith;

import android.content.Intent;
import android.widget.DatePicker;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AppointmentListFragmentTest {

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
        // Open drawer layout
        onView(withId(R.id.PatientHomeDrawerLayout)).perform(DrawerActions.open());
        // Click on Profile
        onView(withId(R.id.Nav_menu_appointment))
                .perform(click());
    }

    @Test
    public void patientAppointmentListElementTest()
    {
        // Check item at position 0
        onView(withId(R.id.appointment_listView)).check(matches(isDisplayed()));

        onView(withId(R.id.profile_edit_icon)).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.option_cancel)).check(matches(isDisplayed()));
    }

    @Test
    public void patientAppointmentDetailsElementTest()
    {
        // Click item at position 0
        onView(withId(R.id.appointment_listView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check item at position 0
        onView(withId(R.id.appointment_details_docName)).check(matches(isDisplayed()));
        onView(withId(R.id.appointment_details_address)).check(matches(isDisplayed()));
        onView(withId(R.id.appointment_details_phone)).check(matches(isDisplayed()));
        onView(withId(R.id.appointment_details_note)).check(matches(isDisplayed()));
        onView(withId(R.id.appointment_details_btnback)).check(matches(isDisplayed()));


    }

    @Test
    public void patientAppointmentBookingElementTest()
    {
        // Click item at position 0
        onView(withId(R.id.appointment_list_booking))
                .perform(click());

        // Check item at position 0
        onView(withId(R.id.Booking_GP)).check(matches(isDisplayed()));
        onView(withId(R.id.Booking_VisitReason)).check(matches(isDisplayed()));
        onView(withId(R.id.Booking_Date)).check(matches(isDisplayed()));
        onView(withId(R.id.Booking_Time)).check(matches(isDisplayed()));
        onView(withId(R.id.Booking_Booking)).check(matches(isDisplayed()));


    }

    @Test
    public void patientAppointmentBookingValidTest() throws InterruptedException {

        onView(withId(R.id.appointment_list_booking))
                .perform(click());

        onView(withId(R.id.Booking_GP))
                .perform(click());
        onData(hasToString(startsWith("Tom")))
                .perform(click());

        onView(withId(R.id.Booking_VisitReason))
                .perform(replaceText("Test reason."));
        closeSoftKeyboard();

        onView(withId(R.id.Booking_Date))
                .perform(click());
        // DatePicker setdate
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2000, 10, 10));
        onView(withId(android.R.id.button1)).perform(click());


        onView(withId(R.id.Booking_Time))
                .perform(click());

        Thread.sleep(5000);
        onView(withText("OK")).perform(click());

        onView(withId(R.id.Booking_Booking))
                .perform(click());

        onView(withText("OK")).perform(click());

    }

    @Test
    public void patientAppointmentBookingInvalidTest() throws InterruptedException {

        onView(withId(R.id.appointment_list_booking))
                .perform(click());

        onView(withId(R.id.Booking_GP))
                .perform(click());
        onData(hasToString(startsWith("Tom")))
                .perform(click());

        onView(withId(R.id.Booking_Booking))
                .perform(click());

        onView(withText("Back")).perform(click());

    }

    @Test
    public void patientAppointmentInvalidCancelTest()
    {
        onView(withId(R.id.profile_edit_icon)).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.option_cancel)).check(matches(isDisplayed()))
                .perform(click());

        onView(withText("Back")).perform(click());

    }

    @After
    public void tearDown() throws Exception {
        activityScenarioRule = null;
    }

}