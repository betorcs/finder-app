package org.devmaster.places.finder.domain


data class Photo(
        val photoReference: String,
        val height: Int,
        val width: Int) {

    fun getUrl(height: Int = 400): String {
        return "https://maps.googleapis.com/maps/api/place/photo?maxheight=$height&photoreference=$photoReference"
    }

}