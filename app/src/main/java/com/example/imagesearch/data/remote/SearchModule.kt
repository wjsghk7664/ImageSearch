package com.example.imagesearch.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule{
    private const val BASE_URL = "https://dapi.kakao.com"

    @Provides
    fun provideGson(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(authorizationInterceptor: AuthorizationInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authorizationInterceptor)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory):Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    fun provideSearchImage(retrofit: Retrofit):SearchImage{
        return retrofit.create(SearchImage::class.java)
    }

    @Provides
    fun provideSearchVideo(retrofit: Retrofit):SearchVideo{
        return retrofit.create(SearchVideo::class.java)
    }

}