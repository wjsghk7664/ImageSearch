package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearch.data.remote.SearchRepository
import com.example.imagesearch.data.local.LocalRepository
import com.example.imagesearch.domain.LocalDataDeleteUsecase
import com.example.imagesearch.domain.LocalDataGetQueryUsecase
import com.example.imagesearch.domain.LocalDataGetUsecase
import com.example.imagesearch.domain.LocalDataSaveQueryUsecase
import com.example.imagesearch.domain.LocalDataSaveUsecase
import com.example.imagesearch.domain.RemoteDataSearchUsecase

class SearchViewModelFactory(private val searchRepository: SearchRepository, private val localRepository: LocalRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(
                RemoteDataSearchUsecase(searchRepository),
                LocalDataGetUsecase(localRepository),
                LocalDataDeleteUsecase(localRepository),
                LocalDataSaveUsecase(localRepository),
                LocalDataGetQueryUsecase(localRepository),
                LocalDataSaveQueryUsecase(localRepository)
            ) as T
        }
        throw IllegalArgumentException("viewModel factory error")
    }
}