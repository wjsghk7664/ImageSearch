package com.example.imagesearch.data.remote

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchRepositoryModule {

    @Binds
    abstract fun bindSearchRepository(searchRepositoryRemoteImpl: SearchRepositoryRemoteImpl):SearchRepository
}