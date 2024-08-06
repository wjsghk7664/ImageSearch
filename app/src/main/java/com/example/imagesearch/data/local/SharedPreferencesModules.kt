package com.example.imagesearch.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class SharedPreferencesQuary

@Qualifier
annotation class SharePreferencesSearch


@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModules {

    //처음에만 사용하니 스코프 지정x
    @SharedPreferencesQuary
    @Provides
    fun provideSharePreferencesQuary(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences("query_pref",0)
    }

    //계속 사용하니 스코프 지정해서 단일 인스턴스만 사용
    @SharePreferencesSearch
    @Provides
    @Singleton
    fun provideSharePreferencesSearch(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences("search_pref",0)
    }

}