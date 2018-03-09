package org.devmaster.places.finder

import okhttp3.*


class OkHttpClientProviderImpl private constructor() : OkHttpClientProvider {

    companion object {
        val RESULT_EMTPY = "results_empty.json"
        val RESULT_OK = "results_ok.json"

        var result: String = RESULT_OK

        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                    .addInterceptor { chain ->

                        // Simulate network
                        Thread.sleep(500)

                        val stream = OkHttpClientProviderImpl::class.java.classLoader.getResourceAsStream("json/$result")
                        val body = ResponseBody.create(MediaType.parse("application/json"), stream.readBytes())
                        Response.Builder()
                                .protocol(Protocol.HTTP_1_1)
                                .code(200)
                                .message("")
                                .request(chain.request())
                                .body(body)
                                .build()
                    }
                    .build()
        }

        private var INSTANCE: OkHttpClientProviderImpl? = null
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