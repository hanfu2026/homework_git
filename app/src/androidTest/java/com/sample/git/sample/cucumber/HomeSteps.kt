package com.sample.git.sample.cucumber//package com.sample.git.sample.cucumber
//
//import android.content.Intent
//import androidx.test.espresso.Espresso
//import androidx.test.espresso.action.ViewActions
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.filters.SmallTest
//import com.sample.git.sample.MainActivity
//import io.cucumber.core.internal.com.fasterxml.jackson.databind.util.ViewMatcher
//import io.cucumber.java.After
//import io.cucumber.java.Before
//import io.cucumber.java.en.Given
//import io.cucumber.java.en.Then
//import io.cucumber.java.en.When
//import junit.framework.TestCase.assertNotNull
//import org.junit.Rule
//import org.junit.runner.RunWith
//
///*
//Feature: Home
//
//Scenario Outline:
//    Give: I have a MainActivity
//    When:
//    Then: I should <see>
//
// */
//
//@SmallTest
//@RunWith(AndroidJUnit4::class)
//class HomeSteps {
//    @Rule
//    lateinit var activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
//    @Rule
//    lateinit var activity: MainActivity
//
//    @Before()
//    fun setup() {
//        activityTestRule.launchActivity(Intent())
//        activity = activityTestRule.activity
//    }
//
//    @After()
//    fun finish() {
//        activityTestRule.finishActivity()
//    }
//
//    @Given("Home launched")
//    fun onHomeScreenLaunched() {
//        assertNotNull(activity)
//    }
//
//    @When("I clicked ")
//    fun onSearchClicked() {
//        Espresso.onView(ViewMatcher.with).perform(ViewActions.click())
//    }
//}