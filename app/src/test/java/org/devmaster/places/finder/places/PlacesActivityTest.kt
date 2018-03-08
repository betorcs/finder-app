package org.devmaster.places.finder.places

import android.view.View
import kotlinx.android.synthetic.main.activity_places.*
import org.devmaster.places.finder.BuildConfig
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class PlacesActivityTest {

    @Test
    fun onCreate_shouldInflateLayout() {
        val activity: PlacesActivity = Robolectric.setupActivity(PlacesActivity::class.java)

        assertEquals(View.VISIBLE, activity.progressBar.visibility)
    }

}