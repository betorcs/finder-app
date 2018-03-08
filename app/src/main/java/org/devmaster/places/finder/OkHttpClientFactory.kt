package org.devmaster.places.finder

import okhttp3.OkHttpClient

class OkHttpClientFactory {

    companion object {

        private val instance: OkHttpClient by lazy {
            OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val originalRequest = chain.request()

                        val httpUrl = originalRequest.url().newBuilder()
                                .addQueryParameter("key", BuildConfig.GOOGLE_API_KEY)
                                .build()

                        val req = originalRequest.newBuilder()
                                .url(httpUrl)
                                .build()

                        chain.proceed(req)
                    }
                    .build()
        }

        fun getOkHttpClient(): OkHttpClient {
            return instance
        }

    }

}