package com.example.imagesearch.domain

import com.example.imagesearch.data.local.LocalRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LocalDataSaveQueryUsecase @Inject constructor(private val localRepository: LocalRepository) {
    operator fun invoke(query:String){
        localRepository.saveQuery(query)
    }
}