package org.devmaster.places.finder

import okhttp3.Interceptor
import okhttp3.Response


class FakeDataInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }

}