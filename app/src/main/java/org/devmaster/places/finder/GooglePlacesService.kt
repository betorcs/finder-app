package org.devmaster.places.finder

import io.reactivex.Observable
import org.devmaster.places.finder.domain.PlaceResult
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesService {

    @GET("textsearch/json")
    fun search(@Query("query") query: String,
               @Query("pageToken") pageToken: String? = null): Observable<PlaceResult>

}