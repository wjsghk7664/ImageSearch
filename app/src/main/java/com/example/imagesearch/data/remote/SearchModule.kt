package com.example.imagesearch.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule{
    private const val BASE_URL = "https://dapi.kakao.com"

    @Provides
    @Singleton
    fun provideGson(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authorizationInterceptor: AuthorizationInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authorizationInterceptor)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory):Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    @Singleton
    fun provideSearchImage(retrofit: Retrofit):SearchImage{
        return retrofit.create(SearchImage::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchVideo(retrofit: Retrofit):SearchVideo{
        return retrofit.create(SearchVideo::class.java)
    }

}