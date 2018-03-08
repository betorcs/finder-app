package org.devmaster.places.finder.places

import org.devmaster.places.finder.domain.Place

interface PlacesContract {
    interface View {
        fun setProgressIndicator(visible: Boolean)
        fun showPlaces(places: Iterable<Place>)
        fun showErrorPlaceholder()
        fun showPlacesEmptyPlaceholder()
    }
    interface Presenter {
        fun searchPlaces(query: String)
        fun onStop()
    }
}