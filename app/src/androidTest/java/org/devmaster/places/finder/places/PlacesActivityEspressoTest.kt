package org.devmaster.places.finder.places

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.squareup.rx2.idler.IdlingResourceScheduler
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.devmaster.places.finder.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class PlacesActivityEspressoTest {

    @Rule
    @JvmField
    var mActivityRule: ActivityTestRule<PlacesActivity> = ActivityTestRule<PlacesActivity>(PlacesActivity::class.java)

    private val mTestScheduler = TestScheduler()
    private lateinit var mIdlingResource: IdlingResourceScheduler

    @Before
    fun setUp() {
        RxJavaPlugins.setInitComputationSchedulerHandler(Rx2Idler.create("Computation Scheduler"))
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(Rx2Idler.create("Main Thread Scheduler"))
        RxJavaPlugins.setComputationSchedulerHandler({ mTestScheduler })
        RxAndroidPlugins.setMainThreadSchedulerHandler({ mTestScheduler })
        mIdlingResource = Rx2Idler.wrap(mTestScheduler, "Test Scheduler")
        IdlingRegistry.getInstance()
                .register(mIdlingResource)
    }

    @Test
    fun onSearchProgress_loadingMustBeVisible() {

        Thread.sleep(500)

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
    }

    @Test
    fun onEmptyResult_emptyViewMustBeVisible() {

        // Click in search icon
        onView(withId(R.id.app_bar_search))
                .perform(click())

        // Tap in search field and make a search
        onView(withHint(R.string.search_hint))
                .perform(typeText("sdkjflskdjflsdlkfkldsjf"))
                .perform(pressImeActionButton())

        // Check if loading is displayed
        onView(withId(R.id.progressBar))
                .check(matches(isDisplayed()))

        // Check if empty view is shown
        onView(withId(R.id.imageView))
                .check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance()
                .unregister(mIdlingResource)
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}