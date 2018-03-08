package org.devmaster.places.finder.domain


data class PlaceResult(
        var status: String,
        var results: Iterable<Place>,
        var nextPageToken: String?)