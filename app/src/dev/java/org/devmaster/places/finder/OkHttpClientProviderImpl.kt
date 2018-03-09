package org.devmaster.places.finder

import okhttp3.OkHttpClient


class OkHttpClientProviderImpl private constructor(): OkHttpClientProvider {

    companion object {

        private var INSTANCE: OkHttpClientProviderImpl? = null

        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val originalRequest = chain.request()

                        val httpUrl = originalRequest.url().newBuilder()
                                .addQueryParameter("key", BuildConfig.GOOGLE_API_KEY)
                                .build()

                        val request = originalRequest.newBuilder()
                                .url(httpUrl)
                                .build()

                        chain.proceed(request)
                    }
                    .build()
        }

        fun getInstance(): OkHttpClientProviderImpl {
            if (INSTANCE == null) {
                INSTANCE = OkHttpClientProviderImpl()
            }
            return INSTANCE!!
        }
    }

    override fun getOkHttpClient(): OkHttpClient {
        return okHttpClient
    }


}