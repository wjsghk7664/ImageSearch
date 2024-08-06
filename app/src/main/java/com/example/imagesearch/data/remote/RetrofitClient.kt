package com.example.imagesearch.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://dapi.kakao.com"

    private val okHttpClient by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder().addInterceptor(AuthoriztionInterceptor())
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val searchImages: SearchImage by lazy {
        retrofit.create(SearchImage::class.java)
    }

    val searchVideo: SearchVideo by lazy {
        retrofit.create(SearchVideo::class.java)
    }

}