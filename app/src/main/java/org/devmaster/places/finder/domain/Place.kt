package org.devmaster.places.finder.domain


data class Place(
        var id: String,
        var name: String,
        var rating: Float,
        var icon: String,
        var photos: List<Photo>,
        var formattedAddress: String)