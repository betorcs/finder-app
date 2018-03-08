package org.devmaster.places.finder

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

class GooglePlacesServiceFactory {

    companion object {

        private const val BASE_URL: String = "https://maps.googleapis.com/maps/api/place/"

        private val instance: GooglePlacesService by lazy {


            val objectMapper = jacksonObjectMapper()
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE

            val retrofit = Retrofit.Builder()
                    .client(OkHttpClientFactory.getOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .baseUrl(BASE_URL)
                    .build()

            retrofit.create(GooglePlacesService::class.java)
        }

        fun getGooglePlacesService(): GooglePlacesService {
            return instance
        }
    }


}