package com.example.imagesearch.data.search

import okhttp3.Interceptor
import okhttp3.Response

class AuthoriztionInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().
                addHeader("Authorization",
                    "KakaoAK %s".format("caadbfdfa2c83ea4840cc79b221104ef")
                ).build()

        return chain.proceed(newRequest)
    }
}