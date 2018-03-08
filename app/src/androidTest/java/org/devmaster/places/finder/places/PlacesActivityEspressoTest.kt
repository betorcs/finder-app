package org.devmaster.places.finder.places

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.devmaster.places.finder.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class PlacesActivityEspressoTest {

    @Rule @JvmField
    var mActivityRule: ActivityTestRule<PlacesActivity> = ActivityTestRule<PlacesActivity>(PlacesActivity::class.java)

    @Test
    fun proceedSearch() {
        onView(withId(R.id.app_bar_search))
                .perform(click())
                .perform(typeText("Pizza in Bauru"))
                .perform(pressImeActionButton())
    }
}