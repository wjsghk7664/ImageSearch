package com.example.imagesearch.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().
                addHeader("Authorization",
                    "KakaoAK %s".format("caadbfdfa2c83ea4840cc79b221104ef")
                ).build()

        return chain.proceed(newRequest)
    }
}