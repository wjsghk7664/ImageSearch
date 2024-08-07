package com.example.imagesearch.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class SharedPreferencesQuary

@Qualifier
annotation class SharePreferencesSearch


@Module
@InstallIn(ViewModelComponent::class)
object SharedPreferencesModules {


    @SharedPreferencesQuary
    @Provides
    fun provideSharePreferencesQuary(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences("query_pref",0)
    }

    @SharePreferencesSearch
    @Provides
    fun provideSharePreferencesSearch(@ApplicationContext context: Context): SharedPreferences{
        return context.getSharedPreferences("search_pref",0)
    }

}