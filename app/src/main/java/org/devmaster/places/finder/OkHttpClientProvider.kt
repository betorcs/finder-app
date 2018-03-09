package org.devmaster.places.finder

import okhttp3.OkHttpClient

interface OkHttpClientProvider {

    fun getOkHttpClient(): OkHttpClient
    
}