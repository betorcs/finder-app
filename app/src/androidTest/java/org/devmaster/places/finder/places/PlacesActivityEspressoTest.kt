package org.devmaster.places.finder.places

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.devmaster.places.finder.OkHttpClientProviderImpl
import org.devmaster.places.finder.R
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class PlacesActivityEspressoTest {

    @Rule
    @JvmField
    var mActivityRule: ActivityTestRule<PlacesActivity>
            = ActivityTestRule<PlacesActivity>(PlacesActivity::class.java)


    @Test
    fun onSearchProgress_loadingMustBeVisible() {


        // Use Fake flavor to run tests
        OkHttpClientProviderImpl.result = OkHttpClientProviderImpl.RESULT_OK

        // Click in search icon
        onView(withId(R.id.app_bar_search))
                .perform(click())

        // Tap in search field and make a search
        onView(withHint(R.string.search_hint))
                .perform(typeText("Pizza in Bauru"))
                .perform(pressImeActionButton())

        // Check if loading is displayed
        onView(withId(R.id.progressBar))
                .check(matches(isDisplayed()))

        Thread.sleep(1200)

        // Empty view can not be displayed
        onView(withId(R.id.emptyPlaceholder))
                .check(matches(not(isDisplayed())))

        // Neither error view
        onView(withId(R.id.errorPlaceholder))
                .check(matches(not(isDisplayed())))

    }

    @Test
    fun onSearchFinishedWithNoResults_shouldShowEmptyView() {

        // Use Fake flavor to run tests
        OkHttpClientProviderImpl.result = OkHttpClientProviderImpl.RESULT_EMTPY

        // Click in search icon
        onView(withId(R.id.app_bar_search))
                .perform(click())

        // Tap in search field and make a search
        onView(withHint(R.string.search_hint))
                .perform(typeText("pizza"))
                .perform(pressImeActionButton())

        // Check if loading is displayed
        onView(withId(R.id.progressBar))
                .check(matches(isDisplayed()))

        Thread.sleep(1200)

        // Check if empty view is shown
        onView(withId(R.id.emptyPlaceholder))
                .check(matches(isDisplayed()))
    }

}