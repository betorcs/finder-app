package org.devmaster.places.finder.places

import org.devmaster.places.finder.domain.Place

interface PlacesContract {
    interface View {
        fun setProgressIndicator(visible: Boolean)
        fun showError(error: Throwable)
        fun showPlaces(places: Iterable<Place>)
    }
    interface Presenter {
        fun searchPlaces(query: String)
        fun onStop()
    }
}